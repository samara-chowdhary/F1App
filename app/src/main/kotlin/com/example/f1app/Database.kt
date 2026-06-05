package com.example.f1app


import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.f1app.databaseEntities.Driver
import com.example.f1app.databaseEntities.DriverDao
import com.example.f1app.databaseEntities.Session
import com.example.f1app.databaseEntities.SessionDao

@Database(entities = [Session::class, Driver::class], version = 2)
abstract class F1Database : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun driverDao(): DriverDao

    companion object {
        @Volatile private var INSTANCE: F1Database? = null

        fun getInstance(context: Context): F1Database {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    F1Database::class.java,
                    "f1_database"
                )
                    .createFromAsset("database/F1 Data.db")
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}