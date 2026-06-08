package com.example.f1app.databaseEntities

import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "driverParticipation", primaryKeys = ["sessionKey", "driverNumber"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class DriverParticipation (
    val sessionKey: Int = 0,
    val driverNumber: Int = 0,
    val teamName: String? = null
)

