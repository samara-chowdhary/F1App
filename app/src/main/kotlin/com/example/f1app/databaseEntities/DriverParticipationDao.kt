package com.example.f1app.databaseEntities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DriverParticipationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(participations: List<DriverParticipation>): List<Long>

    @Query("""
        SELECT team_name FROM DRIVER_PARTICIPATION
        WHERE driver_number = :driverNumber
        AND team_name IS NOT NULL
        ORDER BY session_key DESC
        LIMIT 1
    """)
    suspend fun getLatestTeamForDriver(driverNumber: Int): String?
}

