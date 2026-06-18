package com.example.f1app

import android.content.Context
import com.example.f1app.databaseEntities.*
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class DatabaseImport(private val context: Context) {
    private val db = F1Database.getInstance(context)
    private val client = OkHttpClient()
    private val mapper = ObjectMapper()

    private fun fetch(url: String): String {
        val request = Request.Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return response.body!!.string()
        }
    }

    suspend fun startImport() {
        println("Starting F1 Data Import...")

        try {
            // Sessions
            val sessions = mapper.readValue(
                fetch("https://api.openf1.org/v1/sessions"),
                Array<Session>::class.java
            ).toList()
            db.sessionDao().insertAll(sessions)
            println("Imported ${sessions.size} sessions")

            // Drivers
            val drivers = mapper.readValue(
                fetch("https://api.openf1.org/v1/drivers"),
                Array<Driver>::class.java
            ).toList()
            val uniqueDrivers = drivers.distinctBy { it.driverNumber }
            db.driverDao().insertAll(uniqueDrivers)
            println("Imported ${uniqueDrivers.size} drivers")

            // Driver Participation
            val participations = drivers.map {
                DriverParticipation(
                    driverNumber = it.driverNumber,
                    sessionKey = it.sessionKey ?: 0,
                    teamName = it.teamName
                )
            }
            db.driverParticipationDao().insertAll(participations)
            println("Imported ${participations.size} driver participations")

            // Meetings
            val meetings = mapper.readValue(
                fetch("https://api.openf1.org/v1/meetings"),
                Array<Meeting>::class.java
            ).toList()
            db.meetingDao().insertAll(meetings)
            println("Imported ${meetings.size} meetings")

            println("Import complete!")

        } catch (e: Exception) {
            println("Import failed: ${e.message}")
            e.printStackTrace()
        }
    }
}