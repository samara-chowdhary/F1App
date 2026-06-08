package com.example.f1app.databaseEntities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DriverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drivers: List<Driver>)

    @Query("SELECT * FROM drivers")
    fun getAllDrivers(): List<Driver>

    @Query("""
        SELECT sr.position AS position
        FROM sessionResults AS sr
        INNER JOIN drivers AS d ON sr.driverNumber = d.driverNumber
        INNER JOIN sessions AS s ON sr.sessionKey = s.sessionKey
        INNER JOIN MEETINGS AS m ON s.meetingKey = m.meetingKey
        INNER JOIN CIRCUITS AS c ON m.circuitKey = c.circuitKey
        WHERE d.firstName = :firstName
          AND d.lastName = :lastName
          AND c.location LIKE :location
          AND s.sessionType = 'Race'
        ORDER BY m.year ASC
    """)
    fun getHistoricalPositions(firstName: String, lastName: String, location: String): List<DriverPosition>
}

data class DriverPosition(
    val position: Int
)