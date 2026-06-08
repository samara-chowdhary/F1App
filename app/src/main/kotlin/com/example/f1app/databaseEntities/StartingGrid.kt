package com.example.f1app.databaseEntities

import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "startingGrid", primaryKeys = ["sessionKey", "driverNumber"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class StartingGrid(
    val sessionKey: Int = 0,
    val driverNumber: Int = 0,
    val lapDuration: Double = 0.0,
    val position: Int = 0
)
