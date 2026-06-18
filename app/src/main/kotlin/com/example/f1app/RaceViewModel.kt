package com.example.f1app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app.components.DriverStandingRow
import com.example.f1app.databaseEntities.Driver
import com.example.f1app.machineLearning.PredictionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DriverPredictionState(
    val drivers: List<DriverStandingRow> = emptyList(),
    val isLoading: Boolean = true
)

class RaceViewModel(
    private val database: F1Database,
    private val trackLocation: String
) : ViewModel() {

    private val _state = MutableStateFlow(DriverPredictionState())
    val state: StateFlow<DriverPredictionState> = _state

    init {
        loadPredictions()
    }

    private fun loadPredictions() {
        viewModelScope.launch {
            val predictionRepo = PredictionRepository(database.driverDao())
            val drivers = database.driverDao().getCurrentDrivers()

            val predictions = mutableListOf<DriverStandingRow>()

            drivers.forEach { driver ->
                if (driver.firstName != null && driver.lastName != null) {
                    val team = database.driverDao().getLatestTeamForDriver(driver.driverNumber) ?: "Unknown"
                    val prediction = predictionRepo.predictNextPosition(
                        firstName = driver.firstName,
                        lastName = driver.lastName,
                        trackLocation = trackLocation
                    )
                    if (prediction != null) {
                        predictions.add(
                            DriverStandingRow(
                                position = 0,
                                firstName = driver.firstName,
                                lastName = driver.lastName,
                                team = team,
                                value = "P${Math.round(prediction)}"
                            )
                        )
                    }
                }
            }

            // sort by predicted position and assign ranks
            val sorted = predictions
                .sortedBy { it.value.removePrefix("P").toIntOrNull() ?: 99 }
                .mapIndexed { index, row -> row.copy(position = index + 1) }

            _state.value = DriverPredictionState(drivers = sorted, isLoading = false)
        }
    }
}

