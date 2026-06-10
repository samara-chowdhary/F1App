package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "STARTING_GRID", primaryKeys = ["session_key", "driver_number"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class StartingGrid(
    @ColumnInfo(name = "session_key") val sessionKey: Int = 0,
    @ColumnInfo(name = "driver_number") val driverNumber: Int = 0,
    @ColumnInfo(name = "lap_duration") val lapDuration: Double = 0.0,
    val position: Int = 0
)
