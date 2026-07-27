package com.example.f1app

import android.content.Context
import android.util.Log
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
        Log.d("IMPORT", "Starting live update for most recent race...")

        try {
            // get the most recent race session only
            val sessionsJson = fetch("https://api.openf1.org/v1/sessions?session_type=Race")
            Log.d("IMPORT", "Sessions response length: ${sessionsJson.length}")

            val sessionsNode = mapper.readTree(sessionsJson)
            Log.d("IMPORT", "Sessions node is array: ${sessionsNode.isArray}, size: ${sessionsNode.size()}")


            if (!sessionsNode.isArray || sessionsNode.size() == 0) {
                Log.d("IMPORT", "No sessions found")
                return
            }

            // find the most recent session by date_start
            var latestSession: com.fasterxml.jackson.databind.JsonNode? = null
            var latestDate = ""
            sessionsNode.forEach { session ->
                val dateStart = session.get("date_start")?.asText() ?: ""
                if (dateStart > latestDate) {
                    latestDate = dateStart
                    latestSession = session
                }
            }

            val session = latestSession
            if (session == null) {
                Log.d("IMPORT", "Could not find latest session")
                return
            }

            val sessionKey = session.get("session_key").asInt()
            val meetingKey = session.get("meeting_key").asInt()
            Log.d("IMPORT", "Latest race session_key: $sessionKey")

            // fetch positions for that session only
            val positionsJson = fetch("https://api.openf1.org/v1/position?session_key=$sessionKey")
            val positionsNode = mapper.readTree(positionsJson)

            if (positionsNode.isArray && positionsNode.size() > 0) {
                val finalPositions = mutableMapOf<Int, Int>()
                positionsNode.forEach { pos ->
                    val driverNum = pos.get("driver_number")?.asInt()
                    val position = pos.get("position")?.asInt()
                    if (driverNum != null && position != null) {
                        finalPositions[driverNum] = position
                    }
                }

                finalPositions.forEach { (driverNum, pos) ->
                    val result = SessionResult(
                        sessionKey = sessionKey,
                        driverNumber = driverNum,
                        position = pos,
                        meetingKey = meetingKey
                    )
                    db.driverDao().insertSessionResult(result)
                }
                Log.d("IMPORT", "Updated ${finalPositions.size} results for session $sessionKey")
            } else {
                Log.d("IMPORT", "No position data available yet for session $sessionKey")
            }

        } catch (e: Exception) {
            Log.e("IMPORT", "Live update failed: ${e.message}", e)
        }
    }
}