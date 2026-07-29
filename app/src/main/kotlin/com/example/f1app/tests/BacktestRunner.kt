package com.example.f1app.tests

import android.util.Log
import com.example.f1app.machineLearning.PredictionRepository
import kotlin.math.pow
import kotlin.math.sqrt
import java.net.URL
import org.json.JSONArray

//data class for individual test cases
data class BacktestTestCase(
    val firstName: String,
    val lastName: String,
    val trackLocation: String,
    val gridPosition: Int,  //baseline comparison
    val actualPosition: Int,
    val cutoffDate: String
)

//data class to hold the results of the backtest
data class BacktestReport(
    val totalCases: Int,
    val mae: Double,
    val rmse: Double,
    val exactMatches: Int,
    val baselineMae: Double
) {
    val accuracyPercentage: Double
        get() = if (totalCases > 0) (exactMatches.toDouble() / totalCases) * 100.0 else 0.0
}


suspend fun runBacktestSuite(
    predictionRepo: PredictionRepository,
    testCases: List<BacktestTestCase>
): BacktestReport {
    var totalAbsoluteError = 0.0
    var totalSquaredError = 0.0
    var totalBaselineError = 0.0
    var exactMatches = 0
    var validPredictions = 0

    testCases.forEach { test ->
        val predicted = predictionRepo.predictNextPosition(
            test.firstName,
            test.lastName,
            test.trackLocation,
            test.cutoffDate
        )

        if (predicted != null) {
            val error = Math.abs(predicted - test.actualPosition)
            val baselineError = Math.abs(test.gridPosition - test.actualPosition)

            totalAbsoluteError += error
            totalSquaredError += error.toDouble().pow(2.0)
            totalBaselineError += baselineError

            if (predicted.toInt() == test.actualPosition) {
                exactMatches++
            }
            validPredictions++

            //outputting the results of the test
            Log.d(
                "BACKTEST",
                "${test.firstName} ${test.lastName} @ ${test.trackLocation} | " +
                        "Pred: $predicted | Actual: ${test.actualPosition} | Error: $error"
            )
        } else {
            Log.w("BACKTEST", "Prediction returned null for ${test.firstName} ${test.lastName}")
        }
    }

    val mae = if (validPredictions > 0) totalAbsoluteError / validPredictions else 0.0
    val rmse = if (validPredictions > 0) sqrt(totalSquaredError / validPredictions) else 0.0
    val baselineMae = if (validPredictions > 0) totalBaselineError / validPredictions else 0.0

    val report = BacktestReport(validPredictions, mae, rmse, exactMatches, baselineMae)

    Log.i("BACKTEST_RESULTS", "========================================")
    Log.i("BACKTEST_RESULTS", "Evaluated Cases : ${report.totalCases}")
    Log.i("BACKTEST_RESULTS", "Model MAE       : %.2f".format(report.mae))
    Log.i("BACKTEST_RESULTS", "Model RMSE      : %.2f".format(report.rmse))
    Log.i("BACKTEST_RESULTS", "Grid Baseline   : %.2f".format(report.baselineMae))
    Log.i("BACKTEST_RESULTS", "Exact Accuracy  : %.1f%%".format(report.accuracyPercentage))
    Log.i("BACKTEST_RESULTS", "========================================")

    return report
}


suspend fun fetchDynamicTestCases(sessionKey: String): List<BacktestTestCase> {
    val testCases = mutableListOf<BacktestTestCase>()

    try {
        //query openf1
        val resultsUrl = "https://api.openf1.org/v1/session_result?session_key=$sessionKey"
        val driversUrl = "https://api.openf1.org/v1/drivers?session_key=$sessionKey"

        val resultsJson = JSONArray(URL(resultsUrl).readText())
        val driversJson = JSONArray(URL(driversUrl).readText())

        //mapping the driver to the driver info
        val driverMap = mutableMapOf<Int, Pair<String, String>>()
        for (i in 0 until driversJson.length()) {
            val obj = driversJson.getJSONObject(i)
            val num = obj.getInt("driver_number")
            val first = obj.optString("first_name", "")
            val last = obj.optString("last_name", "")
            driverMap[num] = Pair(first, last)
        }

        //building test cases from results
        for (i in 0 until resultsJson.length()) {
            val result = resultsJson.getJSONObject(i)
            val driverNum = result.getInt("driver_number")
            val actualPos = result.optInt("position", 0)
            val gridPos = if (result.has("grid_position") && !result.isNull("grid_position")) {
                result.getInt("grid_position")
            } else {
                result.optInt("position", 0)
            }

            val names = driverMap[driverNum]
            if (names != null && actualPos > 0) {
                testCases.add(
                    BacktestTestCase(
                        firstName = names.first,
                        lastName = names.second,
                        trackLocation = "Latest Session",
                        gridPosition = gridPos,
                        actualPosition = actualPos,
                        cutoffDate = String()
                    )
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return testCases
}

suspend fun runMultiSessionBacktest(
    predictionRepo: PredictionRepository,
    sessionKeys: List<String>
): BacktestReport {
    val allTestCases = mutableListOf<BacktestTestCase>()

    //fetch test cases from every session
    sessionKeys.forEach { sessionKey ->
        Log.d("BACKTEST", "Fetching dynamic test cases for session: $sessionKey")
        val cases = fetchDynamicTestCases(sessionKey)
        allTestCases.addAll(cases)
    }

    Log.i("BACKTEST", "Total driver cases collected across ${sessionKeys.size} sessions: ${allTestCases.size}")

    //runs the backtest across the combined data set
    return runBacktestSuite(predictionRepo, allTestCases)
}

suspend fun fetchRaceSessionKeys(year: Int): List<String> {
    val sessionKeys = mutableListOf<String>()

    try {
        // Query OpenF1 sessions endpoint for Race sessions in the specified year
        val url = "https://api.openf1.org/v1/sessions?year=$year&session_name=Race"
        val jsonArray = JSONArray(URL(url).readText())

        for (i in 0 until jsonArray.length()) {
            val sessionObj = jsonArray.getJSONObject(i)
            val sessionKey = sessionObj.optString("session_key", "")
            if (sessionKey.isNotEmpty()) {
                sessionKeys.add(sessionKey)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return sessionKeys
}
