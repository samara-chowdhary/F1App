package com.example.f1app.databaseEntities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DriverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drivers: List<Driver>): List<Long>

    @Query("SELECT * FROM drivers")
    suspend fun getAllDrivers(): List<Driver>

    @Query("""
        SELECT sr.position AS position
        FROM session_result AS sr
        INNER JOIN drivers AS d ON sr.driver_number = d.driver_number
        INNER JOIN sessions AS s ON sr.session_key = s.session_key
        INNER JOIN meetings AS m ON s.meeting_key = m.meeting_key
        INNER JOIN circuits AS c ON m.circuit_key = c.circuit_key
        WHERE d.first_name = :firstName
          AND d.last_name = :lastName
          AND c.location LIKE :location
          AND s.session_type = 'Race'
        ORDER BY m.year ASC
    """)
    suspend fun getHistoricalPositions(firstName: String, lastName: String, location: String): List<DriverPosition>

    @Query("""
        SELECT sr.position AS position
        FROM SESSION_RESULT AS sr
        INNER JOIN drivers AS d ON sr.driver_number = d.driver_number
        INNER JOIN sessions AS s ON sr.session_key = s.session_key
        INNER JOIN MEETINGS AS m ON s.meeting_key = m.meeting_key
        WHERE d.first_name = :firstName
        AND d.last_name = :lastName
        AND s.session_type = 'Race'
        ORDER BY m.date_start DESC
        LIMIT 5
        """)
    suspend fun getRecentPositions(firstName: String, lastName: String): List<DriverPosition>
}

data class DriverPosition(
    val position: Int
)