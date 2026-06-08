package com.example.f1app.databaseEntities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "circuits")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Circuit(
    @PrimaryKey
    val circuitKey: Int = 0,

    val circuitShortName: String? = null,

    val circuitType: String? = null,

    val countryCode: Int = 0,

    val gmtOffset: String? = null,

    val location: String? = null
)