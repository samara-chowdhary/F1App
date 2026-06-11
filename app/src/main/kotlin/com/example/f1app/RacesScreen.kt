package com.example.f1app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.f1app.R // Make sure this matches your package name
import com.example.f1app.components.RaceData
import com.example.f1app.components.RacePredictionCard

@Composable
fun RacesScreen(onCardClick: (String) -> Unit) {
    val raceList = listOf(
        RaceData("06 - 08", "MAR", "Australia", R.drawable.flag_australia, "australia"),
        RaceData("22 - 24", "MAY", "Monaco", R.drawable.flag_monaco, "monaco"),
        RaceData("05 - 07", "JUN", "Canada", R.drawable.flag_canada, "canada"),
        RaceData("03 - 05", "JUL", "Great Britain", R.drawable.flag_uk, "uk")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = spacedBy(16.dp)
    ) {
        // A little spacer at the very top of the page
        Spacer(modifier = Modifier.height(24.dp))

        // 3. Loop through your list to generate the cards dynamically
        raceList.forEach { race ->
            RacePredictionCard(
                dateRange = race.dateRange,
                month = race.month,
                countryName = race.countryName,
                flagDrawableId = race.flagDrawableId,
                onButtonClick = {
                    onCardClick(race.screenKey)
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}