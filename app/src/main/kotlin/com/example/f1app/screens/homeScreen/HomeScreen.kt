package com.example.f1app.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.f1app.R
import com.example.f1app.components.SplitImageCard
import com.example.f1app.components.RaceNavigationCard
import com.example.f1app.databaseEntities.Meeting
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import com.example.f1app.utils.getFlagForMeeting

@Composable
fun HomeScreen(
    nextRace: Meeting?,
    onRacesClick: () -> Unit,
    onNextRaceClick: (String) -> Unit,
    onResultsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        if (nextRace != null) {
            RaceNavigationCard(
                dateRange = nextRace?.dateStart?.substring(8, 10)?.let {
                    "${it} - ${nextRace.dateEnd?.substring(8, 10) ?: ""}"
                } ?: "TBD",
                month = nextRace?.dateStart?.substring(5, 7)?.let {
                    when(it) {
                        "01" -> "JAN"
                        "02" -> "FEB"
                        "03" -> "MAR"
                        "04" -> "APR"
                        "05" -> "MAY"
                        "06" -> "JUN"
                        "07" -> "JUL"
                        "08" -> "AUG"
                        "09" -> "SEP"
                        "10" -> "OCT"
                        "11" -> "NOV"
                        "12" -> "DEC"
                        else -> ""
                    }
                } ?: "",
                countryName = nextRace?.meetingName ?: "Upcoming Race",
                flagDrawableId = getFlagForMeeting(nextRace?.meetingName),
                route = "race/${nextRace.meetingName?.replace(" ", "_")}/${nextRace.meetingName?.replace(" ", "_")}/${nextRace.meetingName?.replace(" ", "_")}",
                onButtonClick = {
                    val cleanName = nextRace.meetingName?.replace(" ", "_") ?: "unknown"
                    onNextRaceClick(cleanName)
                },
                backgroundColor = Color(0xFFE10600),
                buttonColor = Color.Black
            )
        } else {
            // loading placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color(0xFF111111)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFE10600))
            }
        }

        SplitImageCard(
            title = "Races",
            imageDrawableId = R.drawable.img_cars,
            backgroundColor = Color.Black,
            buttonColor = Color.Red,
            onCardClick = onRacesClick
        )

        //Spacer(modifier = Modifier.height(4.dp))

        SplitImageCard(
            title = "Results",
            imageDrawableId = R.drawable.img_drivers,
            backgroundColor = Color.Black,
            buttonColor = Color.Red,
            onCardClick = onResultsClick
        )
    }
}