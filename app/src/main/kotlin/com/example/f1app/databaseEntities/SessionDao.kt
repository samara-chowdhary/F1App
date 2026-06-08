package com.example.f1app.databaseEntities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sessions: List<Session>)

    @Query("SELECT * FROM sessions")
    fun getAllSessions(): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE sessionType = :type")
    fun getSessionsByType(type: String): Flow<List<Session>>
}