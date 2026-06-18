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
        RaceData("14 - 16", "MAR", "Australia", R.drawable.flag_australia, "australia", "Albert Park Circuit", "Melbourne"),
        RaceData("21 - 23", "MAR", "China", R.drawable.flag_china, "china", "Shanghai International Circuit", "Shanghai"),
        RaceData("04 - 06", "APR", "Japan", R.drawable.flag_japan, "japan", "Suzuka Circuit", "Suzuka"),
        RaceData("11 - 13", "APR", "Bahrain", R.drawable.flag_bahrain, "bahrain", "Bahrain International Circuit", "Sakhir"),
        RaceData("18 - 20", "APR", "Saudi Arabia", R.drawable.flag_saudi, "saudi", "Jeddah Corniche Circuit", "Jeddah"),
        RaceData("02 - 04", "MAY", "Miami", R.drawable.flag_usa, "miami", "Miami International Autodrome", "Miami"),
        RaceData("16 - 18", "MAY", "Emilia Romagna", R.drawable.flag_italy, "emilia_romagna", "Autodromo Enzo e Dino Ferrari", "Imola"),
        RaceData("23 - 25", "MAY", "Monaco", R.drawable.flag_monaco, "monaco", "Circuit de Monaco", "Monaco"),
        RaceData("30 MAY - 01", "JUN", "Spain", R.drawable.flag_spain, "spain", "Circuit de Barcelona-Catalunya", "Barcelona"),
        RaceData("13 - 15", "JUN", "Canada", R.drawable.flag_canada, "canada", "Circuit Gilles Villeneuve", "Montreal"),
        RaceData("27 - 29", "JUN", "Austria", R.drawable.flag_austria, "austria", "Red Bull Ring", "Spielberg"),
        RaceData("04 - 06", "JUL", "Great Britain", R.drawable.flag_uk, "britain", "Silverstone Circuit", "Silverstone"),
        RaceData("25 - 27", "JUL", "Belgium", R.drawable.flag_belgium, "belgium", "Circuit de Spa-Francorchamps", "Spa"),
        RaceData("01 - 03", "AUG", "Hungary", R.drawable.flag_hungary, "hungary", "Hungaroring", "Budapest"),
        RaceData("29 - 31", "AUG", "Netherlands", R.drawable.flag_netherlands, "netherlands", "Circuit Zandvoort", "Zandvoort"),
        RaceData("05 - 07", "SEP", "Italy", R.drawable.flag_italy, "italy", "Autodromo Nazionale Monza", "Monza"),
        RaceData("19 - 21", "SEP", "Azerbaijan", R.drawable.flag_azerbaijan, "azerbaijan", "Baku City Circuit", "Baku"),
        RaceData("03 - 05", "OCT", "Singapore", R.drawable.flag_singapore, "singapore", "Marina Bay Street Circuit", "Singapore"),
        RaceData("17 - 19", "OCT", "USA", R.drawable.flag_usa, "usa", "Circuit of The Americas", "Austin"),
        RaceData("24 - 26", "OCT", "Mexico", R.drawable.flag_mexico, "mexico", "Autodromo Hermanos Rodriguez", "Mexico City"),
        RaceData("07 - 09", "NOV", "Brazil", R.drawable.flag_brazil, "brazil", "Autodromo Jose Carlos Pace", "Sao Paulo"),
        RaceData("20 - 22", "NOV", "Las Vegas", R.drawable.flag_usa, "las_vegas", "Las Vegas Street Circuit", "Las Vegas"),
        RaceData("28 - 30", "NOV", "Qatar", R.drawable.flag_qatar, "qatar", "Lusail International Circuit", "Lusail"),
        RaceData("05 - 07", "DEC", "Abu Dhabi", R.drawable.flag_uae, "abu_dhabi", "Yas Marina Circuit", "Abu Dhabi")
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
                route = "race/${race.countryName}/${race.circuitName}/${race.trackLocation}",
                onButtonClick = { route -> onCardClick(route) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}