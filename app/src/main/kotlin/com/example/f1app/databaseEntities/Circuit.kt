package com.example.f1app.databaseEntities

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.Typeface
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.sql.Time

@Entity(tableName = "circuits")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Circuit(
    @PrimaryKey
    val circuitKey: Int = 0,

    val circuitShortName: String? = null,

    val circuitType: String? = null,

    val countryCode: Int = 0,

    val gmtOffset: Time,

    val location: String? = null
)
