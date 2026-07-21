package com.example.f1app.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.f1app.ChampsViewModel
import com.example.f1app.components.DNFDropDownBox
import com.example.f1app.components.DriverStandingRow
import com.example.f1app.components.DriversChampionshipDropDownBox
import com.example.f1app.ui.theme.F1Font

@Composable
fun ChampsScreen(
    viewModel: ChampsViewModel,
    onCardClick: (String) -> Unit
) {
    val standings by viewModel.standings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DriversChampionshipDropDownBox(
            title = "Drivers Championship Standings",
            drivers = standings.map { driver ->
                DriverStandingRow(
                    position = driver.positionCurrent,
                    firstName = driver.firstName,
                    lastName = driver.lastName,
                    team = driver.teamName ?: "Unknown",
                    value = "${driver.pointsCurrent} pts"
                )
            }
        )
    }
}