package com.example.f1app.databaseEntities

import androidx.room.ColumnInfo
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
      AND s.session_key = (
          SELECT MAX(s2.session_key)
          FROM sessions s2
          WHERE s2.meeting_key = m.meeting_key
          AND s2.session_type = 'Race'
      )
    ORDER BY m.year ASC
""")
    suspend fun getHistoricalPositions(firstName: String, lastName: String, location: String): List<DriverPosition>

    @Query("""
    SELECT sr.position AS position
    FROM session_result AS sr
    INNER JOIN drivers AS d ON sr.driver_number = d.driver_number
    INNER JOIN sessions AS s ON sr.session_key = s.session_key
    INNER JOIN MEETINGS AS m ON s.meeting_key = m.meeting_key
    WHERE d.first_name = :firstName
    AND d.last_name = :lastName
    AND s.session_type = 'Race'
    AND m.year = (SELECT MAX(year) FROM MEETINGS)
    AND s.session_key = (
        SELECT MAX(s2.session_key) 
        FROM sessions s2 
        WHERE s2.meeting_key = m.meeting_key 
        AND s2.session_type = 'Race'
    )
    ORDER BY m.date_start DESC
    LIMIT 5
""")
    suspend fun getRecentPositions(firstName: String, lastName: String): List<DriverPosition>


    @Query("""
    SELECT dp.team_name FROM DRIVER_PARTICIPATION dp
    WHERE dp.driver_number = :driverNumber
    AND dp.team_name IS NOT NULL
    ORDER BY dp.session_key DESC
    LIMIT 1
""")
    suspend fun getLatestTeamForDriver(driverNumber: Int): String?


    @Query("""
    SELECT DISTINCT d.* FROM drivers d
    INNER JOIN DRIVER_PARTICIPATION dp ON d.driver_number = dp.driver_number
    INNER JOIN sessions s ON dp.session_key = s.session_key
    INNER JOIN MEETINGS m ON s.meeting_key = m.meeting_key
    WHERE s.session_type = 'Race'
    AND m.year = 2026
""")
    suspend fun getCurrentDrivers(): List<Driver>

    data class DnfResult(

        val dnf: Boolean

    )

    @Query("""
    SELECT sr.dnf AS dnf
    FROM SESSION_RESULT sr
    INNER JOIN drivers d ON sr.driver_number = d.driver_number
    INNER JOIN sessions s ON sr.session_key = s.session_key
    WHERE d.first_name = :firstName
      AND d.last_name = :lastName
      AND s.session_type = 'Race'
      AND s.session_key = (
          SELECT MAX(s2.session_key)
          FROM sessions s2
          WHERE s2.meeting_key = (
              SELECT s3.meeting_key FROM sessions s3 WHERE s3.session_key = s.session_key
          )
          AND s2.session_type = 'Race'
      )
    ORDER BY s.date_start DESC
    LIMIT 10
""")
    suspend fun getRecentDNFs(firstName: String, lastName: String): List<DnfResult>
    @Query("""
    SELECT sr.position AS position
    FROM SESSION_RESULT sr
    INNER JOIN drivers d ON sr.driver_number = d.driver_number
    INNER JOIN sessions s ON sr.session_key = s.session_key
    INNER JOIN WEATHER w ON s.session_key = w.session_key
    WHERE d.first_name = :firstName
      AND d.last_name = :lastName
      AND s.session_type = 'Race'
      AND w.rainfall > 0
      AND s.session_key = (
          SELECT MAX(s2.session_key)
          FROM sessions s2
          INNER JOIN MEETINGS m2 ON s2.meeting_key = m2.meeting_key
          WHERE s2.meeting_key = (
              SELECT s3.meeting_key FROM sessions s3 WHERE s3.session_key = s.session_key
          )
          AND s2.session_type = 'Race'
      )
""")
    suspend fun getWetRacePositions(firstName: String, lastName: String): List<DriverPosition>

    @Query("""
    SELECT sr.position AS position
    FROM SESSION_RESULT sr
    INNER JOIN drivers d ON sr.driver_number = d.driver_number
    INNER JOIN sessions s ON sr.session_key = s.session_key
    INNER JOIN WEATHER w ON s.session_key = w.session_key
    WHERE d.first_name = :firstName
      AND d.last_name = :lastName
      AND s.session_type = 'Race'
      AND w.rainfall = 0
      AND s.session_key = (
          SELECT MAX(s2.session_key)
          FROM sessions s2
          WHERE s2.meeting_key = (
              SELECT s3.meeting_key FROM sessions s3 WHERE s3.session_key = s.session_key
          )
          AND s2.session_type = 'Race'
      )
""")
    suspend fun getDryRacePositions(firstName: String, lastName: String): List<DriverPosition>

    @Query("""
    SELECT sr.dnf AS dnf
    FROM SESSION_RESULT sr
    INNER JOIN drivers d ON sr.driver_number = d.driver_number
    INNER JOIN sessions s ON sr.session_key = s.session_key
    INNER JOIN WEATHER w ON s.session_key = w.session_key
    WHERE d.first_name = :firstName
      AND d.last_name = :lastName
      AND s.session_type = 'Race'
      AND w.rainfall > 0
      AND s.session_key = (
          SELECT MAX(s2.session_key)
          FROM sessions s2
          WHERE s2.meeting_key = (
              SELECT s3.meeting_key FROM sessions s3 WHERE s3.session_key = s.session_key
          )
          AND s2.session_type = 'Race'
      )
""")
    suspend fun getWetRaceDNFs(firstName: String, lastName: String): List<DnfResult>

    @Query("""
    SELECT sr.dnf AS dnf
    FROM SESSION_RESULT sr
    INNER JOIN drivers d ON sr.driver_number = d.driver_number
    INNER JOIN sessions s ON sr.session_key = s.session_key
    INNER JOIN WEATHER w ON s.session_key = w.session_key
    WHERE d.first_name = :firstName
      AND d.last_name = :lastName
      AND s.session_type = 'Race'
      AND w.rainfall = 0
      AND s.session_key = (
          SELECT MAX(s2.session_key)
          FROM sessions s2
          WHERE s2.meeting_key = (
              SELECT s3.meeting_key FROM sessions s3 WHERE s3.session_key = s.session_key
          )
          AND s2.session_type = 'Race'
      )
""")
    suspend fun getDryRaceDNFs(firstName: String, lastName: String): List<DnfResult>

    data class DriverPosition(
    val position: Int
)

    @Query("""
    SELECT d.first_name, d.last_name, sr.position, dp.team_name, sr.dnf
    FROM SESSION_RESULT sr
    INNER JOIN drivers d ON sr.driver_number = d.driver_number
    INNER JOIN sessions s ON sr.session_key = s.session_key
    INNER JOIN MEETINGS m ON s.meeting_key = m.meeting_key
    INNER JOIN CIRCUITS c ON m.circuit_key = c.circuit_key
    LEFT JOIN DRIVER_PARTICIPATION dp ON sr.driver_number = dp.driver_number 
        AND sr.session_key = dp.session_key
    WHERE c.location LIKE :location
    AND s.session_type = 'Race'
    AND m.year = :year
    ORDER BY sr.position ASC
""")
    suspend fun getRaceResults(location: String, year: Int): List<RaceResultRow>

    data class RaceResultRow(
        @ColumnInfo(name = "first_name") val firstName: String,
        @ColumnInfo(name = "last_name") val lastName: String,
        val position: Int,
        @ColumnInfo(name = "team_name") val teamName: String?,
        val dnf: Boolean = false
    )

    @Query("""
    SELECT d.first_name, d.last_name, dc.points_current, dc.position_current, dp.team_name
    FROM DRIVERS_CHAMPIONSHIP dc
    INNER JOIN drivers d ON dc.driver_number = d.driver_number
    LEFT JOIN DRIVER_PARTICIPATION dp 
    ON dc.driver_number = dp.driver_number
    AND dp.session_key = (
        SELECT MAX(session_key)
        FROM DRIVER_PARTICIPATION
    )
    WHERE dc.session_key = (
        SELECT MAX(session_key) FROM DRIVERS_CHAMPIONSHIP
    )
    ORDER BY dc.position_current ASC
""")
    suspend fun getCurrentDriversChampionship(): List<ChampionshipRow>

    @Query("""
    SELECT tc.team_name, tc.points_current, tc.position_current
    FROM TEAMS_CHAMPIONSHIP tc
    WHERE tc.session_key = (
        SELECT MAX(session_key) FROM TEAMS_CHAMPIONSHIP
    )
    ORDER BY tc.position_current ASC
""")
    suspend fun getCurrentConstructorsChampionship(): List<ConstructorsChampionshipRow>

    data class ChampionshipRow(
        @ColumnInfo(name = "first_name") val firstName: String,
        @ColumnInfo(name = "last_name") val lastName: String,
        @ColumnInfo(name = "points_current") val pointsCurrent: Int,
        @ColumnInfo(name = "position_current") val positionCurrent: Int,
        @ColumnInfo(name = "team_name") val teamName: String?
    )

    data class ConstructorsChampionshipRow(
        @ColumnInfo(name = "team_name") val teamName: String,
        @ColumnInfo(name = "points_current") val pointsCurrent: Int,
        @ColumnInfo(name = "position_current") val positionCurrent: Int
    )
}