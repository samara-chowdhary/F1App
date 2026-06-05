package com.example.f1app.databaseEntities

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//session data set
@Entity(tableName = "driverParticipation", primaryKeys = ["session_key", "driver_number"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class DriverParticipation (
    @PrimaryKey
    val sessionKey: Int = 0,
    val driverNumber: Int = 0,
    val teamName: String? = null
)

