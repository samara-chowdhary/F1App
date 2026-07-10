package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sessions: List<Session>): List<Long>

    @Query("SELECT * FROM sessions")
    fun getAllSessions(): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE session_type = :type")
    fun getSessionsByType(type: String): Flow<List<Session>>

    @Query("""
    SELECT m.* FROM MEETINGS m
    INNER JOIN sessions s ON m.meeting_key = s.meeting_key
    WHERE s.date_start > :currentDate
    AND s.session_type = 'Race'
    ORDER BY s.date_start ASC
    LIMIT 1
""")
    suspend fun getNextRaceMeeting(currentDate: String): Meeting?

    @Query("""
    SELECT DISTINCT m.meeting_name, m.date_start, m.date_end, m.year, c.location, c.circuit_short_name
    FROM MEETINGS m
    INNER JOIN CIRCUITS c ON m.circuit_key = c.circuit_key
    INNER JOIN sessions s ON m.meeting_key = s.meeting_key
    WHERE s.session_type = 'Race'
    AND m.year = :year
    AND m.meeting_name NOT LIKE '%Testing%'
    ORDER BY m.date_start ASC
""")
    suspend fun getRacesForYear(year: Int): List<RaceCalendarRow>

    data class RaceCalendarRow(
        @ColumnInfo(name = "meeting_name") val meetingName: String,
        @ColumnInfo(name = "date_start") val dateStart: String?,
        @ColumnInfo(name = "date_end") val dateEnd: String?,
        val year: Int,
        val location: String?,
        @ColumnInfo(name = "circuit_short_name") val circuitShortName: String?
    )

}