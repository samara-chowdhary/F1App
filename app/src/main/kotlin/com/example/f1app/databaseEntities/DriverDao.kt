package com.example.f1app.databaseEntities

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

@Dao
interface DriverDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drivers: List<Driver>)

    @Query("SELECT * FROM drivers")
    suspend fun getAllDrivers(): List<Driver>

    @Query("""
        SELECT sr.position 
        FROM SESSION_RESULT sr
        INNER JOIN DRIVERS d ON sr.driver_number = d.driver_number
        INNER JOIN SESSIONS s ON sr.session_key = s.session_key
        INNER JOIN MEETINGS m ON s.meeting_key = m.meeting_key
        INNER JOIN CIRCUITS c ON m.circuit_key = c.circuit_key
        WHERE d.first_name = :firstName 
          AND d.last_name = :lastName 
          AND c.location LIKE :location
          AND s.session_type = 'Race'
        ORDER BY m.year ASC
    """)
    suspend fun getHistoricalPositions(firstName: String, lastName: String, location: String): List<Int>
}
