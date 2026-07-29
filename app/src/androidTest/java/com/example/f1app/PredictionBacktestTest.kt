package com.example.f1app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.f1app.machineLearning.PredictionRepository
import com.example.f1app.tests.fetchRaceSessionKeys
import com.example.f1app.tests.runMultiSessionBacktest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PredictionBacktestTest {

    @Test
    fun testModelAccuracyAcrossEntireSeason() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, F1Database::class.java).build()
        val repo = PredictionRepository(db.driverDao())

        // 1. Dynamically fetch all Race session keys for a season
        val seasonSessionKeys = fetchRaceSessionKeys(year = 2024)

        assertTrue("Session keys should not be empty", seasonSessionKeys.isNotEmpty())

        // 2. Run the backtest on all of them automatically
        val report = runMultiSessionBacktest(repo, seasonSessionKeys)

        assertTrue("Season MAE should be within target bounds", report.mae < 10.0)
    }
}