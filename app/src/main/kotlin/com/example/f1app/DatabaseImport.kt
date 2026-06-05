package com.example.f1app

import android.content.Context
import com.example.f1app.databaseEntities.Session
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class DatabaseImport(private val context: Context) {
    private val db = F1Database.getInstance(context)
    private val client = OkHttpClient()
    private val mapper = ObjectMapper()

    suspend fun startImport() {
        println("Starting F1 Data Import...")

        val request = Request.Builder()
            .url("https://api.openf1.org/v1/sessions?year=2024")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val sessions = mapper.readValue(response.body!!.string(), Array<Session>::class.java).toList()
            db.sessionDao().insertAll(sessions)
            println("Success! Imported ${sessions.size} sessions.")
        }
    }
}