package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity(tableName = "TEAMS_CHAMPIONSHIP", primaryKeys = ["team_name", "session_key"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class TeamsChampionship(
    @ColumnInfo(name = "team_name") val teamName: String = "",
    @ColumnInfo(name = "session_key") val sessionKey: Int = 0,
    @ColumnInfo(name = "points_current") val pointsCurrent: Int?,
    @ColumnInfo(name = "points_start") val pointsStart: Int?,
    @ColumnInfo(name = "position_current") val positionCurrent: Int?,
    @ColumnInfo(name = "position_start") val positionStart: Int?
)

