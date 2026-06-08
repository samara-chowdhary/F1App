package com.example.f1app.databaseEntities

import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "sessionResults", primaryKeys = ["sessionKey", "driverNumber"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionResult (
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