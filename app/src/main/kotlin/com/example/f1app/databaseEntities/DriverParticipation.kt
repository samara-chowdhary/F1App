package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "DRIVER_PARTICIPATION", primaryKeys = ["driver_number", "session_key"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class DriverParticipation(
    @ColumnInfo(name = "driver_number") val driverNumber: Int = 0,
    @ColumnInfo(name = "session_key") val sessionKey: Int = 0,
    @ColumnInfo(name = "team_name") val teamName: String? = null
)

