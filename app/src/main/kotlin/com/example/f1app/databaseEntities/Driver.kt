package com.example.f1app.databaseEntities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "drivers")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Driver(
    @PrimaryKey
    val driverNumber: Int = 0,

    val firstName: String? = null,

    val lastName: String? = null
)