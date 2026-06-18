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

fun getFlagForMeeting(meetingName: String?): Int {
    return when {
        meetingName?.contains("Australia", ignoreCase = true) == true -> R.drawable.flag_australia
        meetingName?.contains("China", ignoreCase = true) == true -> R.drawable.flag_china
        meetingName?.contains("Japan", ignoreCase = true) == true -> R.drawable.flag_japan
        meetingName?.contains("Bahrain", ignoreCase = true) == true -> R.drawable.flag_bahrain
        meetingName?.contains("Saudi", ignoreCase = true) == true -> R.drawable.flag_saudi
        meetingName?.contains("Miami", ignoreCase = true) == true -> R.drawable.flag_usa
        meetingName?.contains("Emilia", ignoreCase = true) == true -> R.drawable.flag_italy
        meetingName?.contains("Monaco", ignoreCase = true) == true -> R.drawable.flag_monaco
        meetingName?.contains("Spain", ignoreCase = true) == true -> R.drawable.flag_spain
        meetingName?.contains("Canada", ignoreCase = true) == true -> R.drawable.flag_canada
        meetingName?.contains("Austria", ignoreCase = true) == true -> R.drawable.flag_austria
        meetingName?.contains("Britain", ignoreCase = true) == true -> R.drawable.flag_uk
        meetingName?.contains("Belgium", ignoreCase = true) == true -> R.drawable.flag_belgium
        meetingName?.contains("Hungary", ignoreCase = true) == true -> R.drawable.flag_hungary
        meetingName?.contains("Netherlands", ignoreCase = true) == true -> R.drawable.flag_netherlands
        meetingName?.contains("Italy", ignoreCase = true) == true -> R.drawable.flag_italy
        meetingName?.contains("Azerbaijan", ignoreCase = true) == true -> R.drawable.flag_azerbaijan
        meetingName?.contains("Singapore", ignoreCase = true) == true -> R.drawable.flag_singapore
        meetingName?.contains("United States", ignoreCase = true) == true -> R.drawable.flag_usa
        meetingName?.contains("Mexico", ignoreCase = true) == true -> R.drawable.flag_mexico
        meetingName?.contains("Brazil", ignoreCase = true) == true -> R.drawable.flag_brazil
        meetingName?.contains("Las Vegas", ignoreCase = true) == true -> R.drawable.flag_usa
        meetingName?.contains("Qatar", ignoreCase = true) == true -> R.drawable.flag_qatar
        meetingName?.contains("Abu Dhabi", ignoreCase = true) == true -> R.drawable.flag_uae
        else -> R.drawable.flag_australia
    }
}

@Composable
fun HomeScreen(
    nextRace: Meeting?,
    onRacesClick: () -> Unit,
    onDriversClick: () -> Unit,
    onNextRaceClick: (String) -> Unit
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

        SplitImageCard(
            title = "Drivers",
            imageDrawableId = R.drawable.img_drivers,
            backgroundColor = Color.Black,
            buttonColor = Color.Red,
            onCardClick = onDriversClick
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}