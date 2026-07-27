package com.example.f1app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.f1app.databaseEntities.F1Database
import com.example.f1app.machineLearning.PredictionRepository
import com.example.f1app.machineLearning.fetchDynamicTestCases
import com.example.f1app.machineLearning.runBacktestSuite
import com.example.f1app.tests.runBacktestSuite
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PredictionBacktestTest {

    @Test
    fun testModelAccuracyWithDynamicData() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, F1Database::class.java).build()
        val repo = PredictionRepository(db.driverDao())

        //fetch session results dynamically
        val dynamicCases = fetchDynamicTestCases(sessionKey = "latest")

        //ensure valid tests were returned
        assertTrue("Test cases should not be empty", dynamicCases.isNotEmpty())

        //execute the backtest suite
        val report = runBacktestSuite(repo, dynamicCases)

        //ensure model performance meets standard of < 4 posoitions
        assertTrue("Model MAE should be within target bounds", report.mae < 4.0)
    }
}