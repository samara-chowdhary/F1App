package com.example.f1app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1app.ui.theme.F1Font
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1app.F1Database
import com.example.f1app.RaceViewModel
import com.example.f1app.RaceViewModelFactory
import com.example.f1app.components.StandingsDropDownBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AustriaScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val viewModel: RaceViewModel = viewModel(
        factory = RaceViewModelFactory(
            database = F1Database.getInstance(context),
            trackLocation = "Melbourne"  // change this for each track
        )
    )
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Austrian GP Predictions",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = F1Font,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Dashboard",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF111111)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Red Bull Ring",
                color = Color(0xFFE10600),
                fontSize = 24.sp,
                fontFamily = F1Font,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color(0xFFE10600),
                    modifier = Modifier.padding(32.dp)
                )
            } else {
                StandingsDropDownBox(
                    title = "Predicted Positions",
                    drivers = state.drivers
                )
            }

        }
    }
}


