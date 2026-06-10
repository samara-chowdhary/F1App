package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "meetings", primaryKeys = ["meeting_key", "circuit_key"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class Meeting(
    @ColumnInfo(name = "meeting_key") val meetingKey: Int = 0,
    @ColumnInfo(name = "circuit_key") val circuitKey: Int = 0,
    @ColumnInfo(name = "date_end") val dateEnd: String? = null,
    @ColumnInfo(name = "date_start") val dateStart: String? = null,
    @ColumnInfo(name = "meeting_name") val meetingName: String? = null,
    @ColumnInfo(name = "meeting_official_name") val meetingOfficialName: String? = null,
    val year: Int = 0
)