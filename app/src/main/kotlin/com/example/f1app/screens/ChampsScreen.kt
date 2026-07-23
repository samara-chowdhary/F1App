package com.example.f1app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1app.ChampsViewModel
import com.example.f1app.components.ConstructorsChampionshipDropDownBox
import com.example.f1app.components.DriverStandingRow
import com.example.f1app.components.DriversChampionshipDropDownBox
import com.example.f1app.ui.theme.F1Font
import androidx.compose.material3.CenterAlignedTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChampsScreen(
    viewModel: ChampsViewModel,
    onCardClick: (String) -> Unit
) {
    val driverStandings by viewModel.standings.collectAsState()
    val constructorStandings by viewModel.constructorStandings.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Current Championship Standings",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = F1Font,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF111111) // Matches dark background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            DriversChampionshipDropDownBox(
                title = "Drivers Standings",
                drivers = driverStandings.map { driver ->
                    DriverStandingRow(
                        position = driver.positionCurrent,
                        firstName = driver.firstName,
                        lastName = driver.lastName,
                        team = driver.teamName ?: "Unknown",
                        value = "${driver.pointsCurrent} pts"
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ConstructorsChampionshipDropDownBox(
                title = "Constructors Standings",
                constructors = constructorStandings.map { constructor ->
                    DriverStandingRow(
                        position = constructor.positionCurrent ?: 0,
                        firstName = constructor.teamName,
                        lastName = "",
                        team = constructor.teamName,
                        value = "${constructor.pointsCurrent ?: 0} pts"
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}