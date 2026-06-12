package com.example.f1app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.f1app.R
import com.example.f1app.components.RaceData
import com.example.f1app.components.RaceNavigationCard

@Composable
fun RacesScreen(onCardClick: (String) -> Unit) {
    val raceList = listOf(
        RaceData("06 - 08", "MAR", "Australia", R.drawable.flag_australia, "australia"),
        RaceData("21 - 23", "MAR", "China", R.drawable.flag_china, "china"),
        RaceData("04 - 06", "APR", "Japan", R.drawable.flag_japan, "japan"),
        RaceData("11 - 13", "APR", "Bahrain", R.drawable.flag_bahrain, "bahrain"),
        RaceData("18 - 20", "APR", "Saudi Arabia", R.drawable.flag_saudi, "saudi"),
        RaceData("02 - 04", "MAY", "Miami", R.drawable.flag_usa, "miami"),
        RaceData("16 - 18", "MAY", "Emilia Romagna", R.drawable.flag_italy, "emilia_romagna"),
        RaceData("23 - 25", "MAY", "Monaco", R.drawable.flag_monaco, "monaco"),
        RaceData("30 MAY - 01", "JUN", "Spain", R.drawable.flag_spain, "spain"),
        RaceData("13 - 15", "JUN", "Canada", R.drawable.flag_canada, "canada"),
        RaceData("27 - 29", "JUN", "Austria", R.drawable.flag_austria, "austria"),
        RaceData("04 - 06", "JUL", "Great Britain", R.drawable.flag_uk, "britain"),
        RaceData("25 - 27", "JUL", "Belgium", R.drawable.flag_belgium, "belgium"),
        RaceData("01 - 03", "AUG", "Hungary", R.drawable.flag_hungary, "hungary"),
        RaceData("29 - 31", "AUG", "Netherlands", R.drawable.flag_netherlands, "netherlands"),
        RaceData("05 - 07", "SEP", "Italy", R.drawable.flag_italy, "italy"),
        RaceData("19 - 21", "SEP", "Azerbaijan", R.drawable.flag_azerbaijan, "azerbaijan"),
        RaceData("03 - 05", "OCT", "Singapore", R.drawable.flag_singapore, "singapore"),
        RaceData("17 - 19", "OCT", "USA", R.drawable.flag_usa, "usa"),
        RaceData("24 - 26", "OCT", "Mexico", R.drawable.flag_mexico, "mexico"),
        RaceData("07 - 09", "NOV", "Brazil", R.drawable.flag_brazil, "brazil"),
        RaceData("20 - 22", "NOV", "Las Vegas", R.drawable.flag_usa, "las_vegas"),
        RaceData("28 - 30", "NOV", "Qatar", R.drawable.flag_qatar, "qatar"),
        RaceData("05 - 07", "DEC", "Abu Dhabi", R.drawable.flag_uae, "abu_dhabi")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        raceList.forEach { race ->
            RaceNavigationCard(
                dateRange = race.dateRange,
                month = race.month,
                countryName = race.countryName,
                flagDrawableId = race.flagDrawableId,
                route = race.screenKey,
                onButtonClick = { route -> onCardClick(route) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}