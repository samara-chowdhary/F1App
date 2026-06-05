package com.example.f1app.databaseEntities

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

//session data set
@Entity(tableName = "startingGrid", primaryKeys = ["session_key", "driver_number"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class StartingGrid(
    @PrimaryKey
    val sessionKey: Int = 0,
    val driverNumber: Int = 0,
    val lapDuration: Double = 0.0,
    val position: Int = 0
)
