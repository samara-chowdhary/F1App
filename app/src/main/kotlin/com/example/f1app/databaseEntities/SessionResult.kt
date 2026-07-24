package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "SESSION_RESULT", primaryKeys = ["session_key", "driver_number"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionResult(
    @ColumnInfo(name = "session_key") val sessionKey: Int = 0,
    @ColumnInfo(name = "driver_number") val driverNumber: Int = 0,
    @ColumnInfo(name = "meeting_key") val meetingKey: Int = 0,
    val dnf: Boolean = false,
    val dns: Boolean = false,
    val dsq: Boolean = false,
    val duration: Double = 0.0,
    @ColumnInfo(name = "gap_to_leader") val gapToLeader: Double = 0.0,
    @ColumnInfo(name = "number_of_laps") val numberOfLaps: Int = 0,
    val position: Int = 0
)