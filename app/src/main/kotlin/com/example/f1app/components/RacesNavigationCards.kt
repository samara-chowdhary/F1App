package com.example.f1app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class RaceData(
    val dateRange: String,
    val month: String,
    val countryName: String,
    val flagDrawableId: Int,
    val screenKey: String // Tells the app which page to open when clicked
)

@Composable
fun RacePredictionCard(
    dateRange: String,
    month: String,
    countryName: String,
    flagDrawableId: Int,
    route: String,                 // <-- New: The target destination layout name
    onButtonClick: (String) -> Unit // <-- Updated: Now passes the string route name when clicked
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = dateRange, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = month, color = Color.LightGray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }

                    Image(
                        painter = painterResource(id = flagDrawableId),
                        contentDescription = "$countryName Flag",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .weight(2f, fill = false)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = countryName, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onButtonClick(route) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE10600)),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text(text = "View Predictions", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}