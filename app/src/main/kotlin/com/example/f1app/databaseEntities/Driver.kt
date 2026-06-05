package com.example.f1app.databaseEntities

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@Entity(tableName = "drivers")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Driver(
    @PrimaryKey
    val driverNumber: Int = 0,

    val firstName: String? = null,

    val lastName: String? = null

)