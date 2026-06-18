package com.example.f1app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.f1app.databaseEntities.Driver
import com.example.f1app.databaseEntities.DriverDao
import com.example.f1app.databaseEntities.Session
import com.example.f1app.databaseEntities.SessionDao
import com.example.f1app.databaseEntities.SessionResult
import com.example.f1app.databaseEntities.Meeting
import com.example.f1app.databaseEntities.Circuit
import com.example.f1app.databaseEntities.DriverParticipation
import com.example.f1app.databaseEntities.DriverParticipationDao
import com.example.f1app.databaseEntities.MeetingDao

@Database(entities = [Session::class, Driver::class, SessionResult::class, Meeting::class, Circuit::class, DriverParticipation:: class], version = 2)
abstract class F1Database : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun driverDao(): DriverDao
    abstract fun meetingDao(): MeetingDao

    abstract fun driverParticipationDao(): DriverParticipationDao

    companion object {
        @Volatile
        private var INSTANCE: F1Database? = null

        fun getInstance(context: Context?): F1Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context!!.applicationContext,
                    F1Database::class.java,
                    "f1_database"
                )
                    .createFromAsset("database/F1DataFixed.db")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}