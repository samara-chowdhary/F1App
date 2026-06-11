package com.example.f1app.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

@Composable
fun DropDownBox(title: String, infoText: String, modifier: Modifier = Modifier){
    OutlinedTextField(
        value = infoText,
        onValueChange = {},
        readOnly = true,
        label = { Text(title) },
        trailingIcon = {
            Icon(imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null)
        }
    )
}
