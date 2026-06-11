package com.example.f1app

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.f1app.components.DropDownBox
import com.example.f1app.components.LightsOutTopBar

@Composable
fun MainScreen() {
    Scaffold(
        topBar = { LightsOutTopBar() }
    ) { innerPadding ->
        // Your main content (like the race cards in your screenshot) goes here
        Column(modifier = Modifier.padding(innerPadding)) {
            // Content...
        }
    }
}
