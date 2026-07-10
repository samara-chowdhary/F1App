package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "DRIVERS_CHAMPIONSHIP", primaryKeys = ["driver_number", "session_key"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class DriversChampionship(
    @ColumnInfo(name = "driver_number") val driverNumber: Int = 0,
    @ColumnInfo(name = "session_key") val sessionKey: Int = 0,
    @ColumnInfo(name = "points_current") val pointsCurrent: Int = 0,
    @ColumnInfo(name = "points_start") val pointsStart: Int = 0,
    @ColumnInfo(name = "position_current") val positionCurrent: Int = 0,
    @ColumnInfo(name = "position_start") val positionStart: Int = 0
)