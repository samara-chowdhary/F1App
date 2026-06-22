package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "WEATHER",
    primaryKeys = ["session_key", "date"]
)
data class Weather(
    @ColumnInfo(name = "session_key") val sessionKey: Int,
    @ColumnInfo(name = "date") val date: String,


    @ColumnInfo(name = "air_temperature") val airTemperature: Double?,
    @ColumnInfo(name = "humidity") val humidity: Int?,
    @ColumnInfo(name = "pressure") val pressure: Double?,
    @ColumnInfo(name = "rainfall") val rainfall: Int?,
    @ColumnInfo(name = "track_temperature") val trackTemperature: Double?,
    @ColumnInfo(name = "wind_direction") val windDirection: Int?,
    @ColumnInfo(name = "wind_speed") val windSpeed: Int?
)