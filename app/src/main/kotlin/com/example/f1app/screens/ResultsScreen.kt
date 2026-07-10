package com.example.f1app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1app.F1Database
import com.example.f1app.ResultsViewModel
import com.example.f1app.ResultsViewModelFactory
import com.example.f1app.components.RaceNavigationCard
import com.example.f1app.utils.getCountryName
import com.example.f1app.utils.getFlagForMeeting

@Composable
fun ResultsScreen(onCardClick: (String) -> Unit) {
    val context = LocalContext.current
    val viewModel: ResultsViewModel = viewModel(
        factory = ResultsViewModelFactory(F1Database.getInstance(context))
    )
    val races by viewModel.races.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        if (races.isEmpty()) {
            CircularProgressIndicator(
                color = Color(0xFFE10600),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            races.forEach { race ->
                val dateRange = race.dateStart?.substring(8, 10)?.let {
                    "${it} - ${race.dateEnd?.substring(8, 10) ?: ""}"
                } ?: "TBD"

                val month = race.dateStart?.substring(5, 7)?.let {
                    when(it) {
                        "01" -> "JAN"; "02" -> "FEB"; "03" -> "MAR"; "04" -> "APR"
                        "05" -> "MAY"; "06" -> "JUN"; "07" -> "JUL"; "08" -> "AUG"
                        "09" -> "SEP"; "10" -> "OCT"; "11" -> "NOV"; "12" -> "DEC"
                        else -> ""
                    }
                } ?: ""

                RaceNavigationCard(
                    dateRange = dateRange,
                    month = month,
                    countryName = getCountryName(race.meetingName),
                    flagDrawableId = getFlagForMeeting(race.meetingName),
                    route = "result/${race.meetingName.replace(" ", "_")}/${race.location?.replace(" ", "_")}/2026",
                    onButtonClick = { route -> onCardClick(route) },
                    buttonText = "View Results"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}