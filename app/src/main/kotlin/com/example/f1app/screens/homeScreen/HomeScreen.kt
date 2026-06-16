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

@Composable
fun HomeScreen(
    nextRace: Meeting?,
    onRacesClick: () -> Unit,
    onDriversClick: () -> Unit,
    onNextRaceClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        RaceNavigationCard(
            dateRange = "06 - 08",
            month = "MAR",
            countryName = "Australia",
            flagDrawableId = R.drawable.flag_australia,
            route = "australia",
            onButtonClick = { onNextRaceClick() },
            backgroundColor = Color.Red,
            buttonColor = Color.Black
        )

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