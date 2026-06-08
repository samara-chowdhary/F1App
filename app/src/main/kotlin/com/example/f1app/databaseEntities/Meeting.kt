package com.example.f1app.databaseEntities

import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.sql.Date
import java.time.Year

@Entity(tableName = "meetings", primaryKeys = ["meetingKey", "circuitKey"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class Meeting(
    val meetingKey: Int = 0,
    val circuitKey: Int = 0,
    val dateEnd: Date,
    val dateStart: Date,
    val meetingName: String? = null,
    val meetingOfficialName: String? = null,
    val year: Year
)