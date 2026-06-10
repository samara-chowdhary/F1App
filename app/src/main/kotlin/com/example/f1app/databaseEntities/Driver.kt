package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "drivers")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Driver(
    @PrimaryKey
    @ColumnInfo(name = "driver_number") val driverNumber: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String? = null,
    @ColumnInfo(name = "last_name") val lastName: String? = null
)