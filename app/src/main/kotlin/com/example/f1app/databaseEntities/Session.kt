package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "sessions")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Session(
    @PrimaryKey
    @ColumnInfo(name = "session_key") val sessionKey: Int = 0,
    @ColumnInfo(name = "meeting_key") val meetingKey: Int = 0,
    @ColumnInfo(name = "session_name") val sessionName: String? = null,
    @ColumnInfo(name = "session_type") val sessionType: String? = null,
    @ColumnInfo(name = "date_start") val dateStart: String? = null,
)