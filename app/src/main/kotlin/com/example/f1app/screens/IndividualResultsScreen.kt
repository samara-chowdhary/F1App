package com.example.f1app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1app.F1Database
import com.example.f1app.RaceResultsViewModel
import com.example.f1app.RaceResultsViewModelFactory
import com.example.f1app.ui.theme.F1Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualResultsScreen(
    raceName: String,
    location: String,
    year: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: RaceResultsViewModel = viewModel(
        key = "$location$year",
        factory = RaceResultsViewModelFactory(
            database = F1Database.getInstance(context),
            location = location,
            year = year
        )
    )
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "$raceName Results",
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
                            contentDescription = "Back",
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                CircularProgressIndicator(color = Color(0xFFE10600))
            } else if (!state.hasData) {
                Text(
                    text = "No data yet",
                    color = Color.LightGray,
                    fontSize = 18.sp,
                    fontFamily = F1Font
                )
            } else {
                state.results.forEach { result ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (result.dnf) "DNF" else "P${result.position}",
                                color = if (result.dnf) Color.Red else Color(0xFFE10600),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontFamily = F1Font,
                                modifier = Modifier.width(48.dp)
                            )
                            Column {
                                Text(
                                    text = "${result.firstName} ${result.lastName}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = F1Font
                                )
                                Text(
                                    text = result.teamName ?: "Unknown",
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                    fontFamily = F1Font
                                )
                            }
                        }
                    }
                    HorizontalDivider(color = Color(0xFF222222), thickness = 1.dp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

