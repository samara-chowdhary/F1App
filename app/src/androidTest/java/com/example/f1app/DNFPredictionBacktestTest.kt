package com.example.f1app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.f1app.machineLearning.DNFPredictionRepository
import com.example.f1app.tests.fetchRaceSessionKeys
import com.example.f1app.tests.runDnfMultiSessionBacktest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DnfPredictionBacktestTest {

    @Test
    fun testDnfModelAccuracyAcrossEntireSeason() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, F1Database::class.java).build()
        val repo = DNFPredictionRepository(db.driverDao())

        // Dynamically fetch all Race session keys for a season
        val seasonSessionKeys = fetchRaceSessionKeys(year = 2024)

        assertTrue("Session keys should not be empty", seasonSessionKeys.isNotEmpty())

        // Run the DNF backtest on all sessions automatically
        val report = runDnfMultiSessionBacktest(repo, seasonSessionKeys)

        // Check that DNF prediction accuracy is within the target bounds
        assertTrue(
            "DNF prediction accuracy should be at least 70%",
            report.accuracyPercentage >= 0.70
        )
    }
}