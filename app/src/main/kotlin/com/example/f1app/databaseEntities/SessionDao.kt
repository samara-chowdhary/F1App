package com.example.f1app.databaseEntities

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


}