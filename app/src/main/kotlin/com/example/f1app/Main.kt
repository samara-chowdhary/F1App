package com.example.f1app

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.sql.DriverManager

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class Main(private var context: Context?) {
    var dbPath: String = context?.getFilesDir()?.getPath() + "/F1Data.db"
    var DB_URL: String = "jdbc:sqlite:" + dbPath

    @Throws(Exception::class)
    fun startImport() {
        println("Starting F1 Data Import...")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.openf1.org/v1/sessions?year=2024")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code " + response)
            val jsonData = response.body!!.string()

            val mapper = ObjectMapper()

            val sessions: Array<Session> =
                mapper.readValue<Array<Session>>(jsonData, Array<Session>::class.java)
            DriverManager.getConnection(DB_URL).use { conn ->
                val sql =
                    "INSERT OR REPLACE INTO sessions (session_key, meeting_key, session_name, session_type, date_start) VALUES (?, ?, ?, ?, ?)"
                val pstmt = conn.prepareStatement(sql)

                for (s in sessions) {
                    pstmt.setInt(1, s.sessionKey)
                    pstmt.setInt(2, s.meetingKey)
                    pstmt.setString(3, s.sessionName)
                    pstmt.setString(4, s.sessionType)
                    pstmt.setString(5, s.dateStart)
                    pstmt.addBatch()
                }
                pstmt.executeBatch()
                println("Success! Imported " + sessions.size + " sessions.")
            }
        }
    }
}