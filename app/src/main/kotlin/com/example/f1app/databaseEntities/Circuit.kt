package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "circuits")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Circuit(
    @PrimaryKey
    @ColumnInfo(name = "circuit_key") val circuitKey: Int = 0,
    @ColumnInfo(name = "circuit_short_name") val circuitShortName: String? = null,
    @ColumnInfo(name = "circuit_type") val circuitType: String? = null,
    @ColumnInfo(name = "country_code") val countryCode: Int = 0,
    @ColumnInfo(name = "gmt_offset") val gmtOffset: String? = null,
    val location: String? = null
)