package com.example.f1app.databaseEntities

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

//session data set
@Entity(tableName = "sessionResults", primaryKeys = ["session_key", "driver_number"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionResult (
    @PrimaryKey
    val sessionKey: Int = 0,
    val driverNumber: Int = 0,
    val meetingKey: Int = 0,
    val dnf: Boolean,
    val dns: Boolean,
    val dsq: Boolean,
    val duration: Double = 0.0,
    val gapToLeader: Double = 0.0,
    val numberOfLaps: Int = 0,
    val position: Int = 0
)