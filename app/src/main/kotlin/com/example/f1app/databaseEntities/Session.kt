package com.example.f1app.databaseEntities

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

//session data set
@Entity(tableName = "sessions")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Session (
    @PrimaryKey
    val sessionKey: Int = 0,

    val meetingKey: Int = 0,

    val sessionName: String? = null,

    val sessionType: String? = null,

    val dateStart: String? = null,
)