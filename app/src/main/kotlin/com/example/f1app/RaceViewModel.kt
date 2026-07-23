package com.example.f1app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app.components.DriverStandingRow
import com.example.f1app.machineLearning.DNFPredictionRepository
import com.example.f1app.machineLearning.PredictionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DriverPredictionState(
    val drivers: List<DriverStandingRow> = emptyList(),
    val dnfRisks: List<DriverStandingRow> = emptyList(),
    val wetPositionImpact: List<DriverStandingRow> = emptyList(),
    val wetDnfImpact: List<DriverStandingRow> = emptyList(),
    val driversChampionshipImpact: List<DriverStandingRow> = emptyList(),
    val constructorsChampionshipImpact: List<DriverStandingRow> = emptyList(),
    val isLoading: Boolean = true
)

fun getPointsForPosition(position: Int): Int {
    return when (position) {
        1 -> 25; 2 -> 18; 3 -> 15; 4 -> 12; 5 -> 10
        6 -> 8; 7 -> 6; 8 -> 4; 9 -> 2; 10 -> 1
        else -> 0
    }
}

class RaceViewModel(
    private val database: F1Database,
    private val trackLocation: String
) : ViewModel() {

    private val _state = MutableStateFlow(DriverPredictionState())
    val state: StateFlow<DriverPredictionState> = _state

    private val _isWetRace = MutableStateFlow(false)
    val isWetRace: StateFlow<Boolean> = _isWetRace

    init {
        loadPredictions(false)
    }

    fun toggleWetRace(isWet: Boolean) {
        _isWetRace.value = isWet
        loadPredictions(isWet)
    }

    private fun loadPredictions(isWetRace: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val predictionRepo = PredictionRepository(database.driverDao())
            val dnfRepo = DNFPredictionRepository(database.driverDao())
            val drivers = database.driverDao().getCurrentDrivers()

            val predictions = mutableListOf<DriverStandingRow>()
            val dnfRisks = mutableListOf<DriverStandingRow>()

            drivers.forEach { driver ->
                if (driver.firstName != null && driver.lastName != null) {
                    val team = database.driverParticipationDao().getLatestTeamForDriver(driver.driverNumber) ?: "Unknown"

                    val prediction = predictionRepo.predictNextPosition(
                        firstName = driver.firstName,
                        lastName = driver.lastName,
                        trackLocation = trackLocation,
                        isWetRace = isWetRace
                    )

                    if (prediction != null) {
                        val bracket = predictionRepo.getPredictionBracket(prediction)
                        val value = "P${Math.round(prediction)} · $bracket"
                        Log.d("PRED_DEBUG", "${driver.firstName} ${driver.lastName}: $value")
                        predictions.add(
                            DriverStandingRow(
                                position = 0,
                                firstName = driver.firstName,
                                lastName = driver.lastName,
                                team = team,
                                value = "P${Math.round(prediction)} · $bracket"
                            )
                        )
                    }


                    val dnfRisk = dnfRepo.predictDnfRisk(driver.firstName, driver.lastName, isWetRace)
                    dnfRisks.add(
                        DriverStandingRow(0, driver.firstName, driver.lastName, team, dnfRisk)
                    )
                }
            }

            val sortedPredictions = predictions
                .sortedBy {
                    it.value.substringAfter("P")
                        .substringBefore(" ")
                        .toIntOrNull() ?: 99
                }
                .mapIndexed { index, row -> row.copy(position = index + 1) }

            val sortedDnfRisks = dnfRisks
                .sortedByDescending {
                    when (it.value) { "High" -> 3; "Medium" -> 2; "Low" -> 1; else -> 0 }
                }
                .mapIndexed { index, row -> row.copy(position = index + 1) }

            // fetch current standings
            val currentStandings = database.driverDao().getCurrentDriversChampionship()
            val currentConstructors = database.driverDao().getCurrentConstructorsChampionship()

            // calculate points impact per driver
            val driversImpact = mutableListOf<DriverStandingRow>()
            val constructorsImpact = mutableMapOf<String, Int>() // team -> points gained


            sortedPredictions.forEach { prediction ->
                val predictedPos = prediction.value.substringAfter("P")
                    .substringBefore(" ")
                    .toIntOrNull() ?: 0
                val pointsGained = getPointsForPosition(predictedPos)
                val current = currentStandings.find {
                    it.firstName == prediction.firstName && it.lastName == prediction.lastName
                }
                val currentPoints = current?.pointsCurrent ?: 0
                val newPoints = currentPoints + pointsGained

                driversImpact.add(
                    DriverStandingRow(
                        position = current?.positionCurrent ?: 0,
                        firstName = prediction.firstName,
                        lastName = prediction.lastName,
                        team = prediction.team,
                        value = if (pointsGained > 0) "+$pointsGained pts → ${newPoints} pts"
                        else "No points → ${newPoints} pts"
                    )
                )

                // accumulate constructor points
                val team = prediction.team
                constructorsImpact[team] = (constructorsImpact[team] ?: 0) + pointsGained
            }

            // build constructors impact list
            val constructorsList = constructorsImpact.map { (team, pointsGained) ->
                val current = currentConstructors.find { it.teamName == team }
                val currentPoints = current?.pointsCurrent ?: 0
                DriverStandingRow(
                    position = current?.positionCurrent ?: 0,
                    firstName = team,
                    lastName = "",
                    team = team,
                    value = if (pointsGained > 0) "+$pointsGained pts → ${currentPoints + pointsGained} pts"
                    else "No points → ${currentPoints} pts"
                )
            }.sortedBy { it.position }

            _state.value = DriverPredictionState(
                drivers = sortedPredictions,
                dnfRisks = sortedDnfRisks,
                driversChampionshipImpact = driversImpact.sortedBy { it.position },
                constructorsChampionshipImpact = constructorsList,
                isLoading = false
            )
        }
    }
}