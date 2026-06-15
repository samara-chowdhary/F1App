package com.example.f1app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.f1app.components.LightsOutTopBar

@Composable
fun MainScreen() {
    Scaffold(
        topBar = { LightsOutTopBar() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Content...
        }
    }
}
