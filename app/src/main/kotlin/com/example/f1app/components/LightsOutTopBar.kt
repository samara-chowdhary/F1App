package com.example.f1app.components
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LightsOutTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "LightsOut",
                color = Color(0xFFE53935), // Your brand red
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF121212) // Dark background
        )
    )
}
