package com.example.f1app.databaseEntities

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao{
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(sessions: List<Session>)

    @Query("SELECT * FROM sessions")
    fun getAllSessions(): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE sessionType = :type")
    fun getSessionsByType(type: String): Flow<List<Session>>
}