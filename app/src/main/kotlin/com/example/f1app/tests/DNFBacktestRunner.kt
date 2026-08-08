package com.example.f1app.tests

import android.util.Log
import com.example.f1app.machineLearning.DNFPredictionRepository
import java.net.URL
import org.json.JSONArray

// data class for individual DNF test cases
data class DnfBacktestTestCase(
    val firstName: String,
    val lastName: String,
    val actualDnf: Boolean,
    val cutoffDate: String
)

// data class to hold the results of the DNF backtest
data class DnfBacktestReport(
    val totalCases: Int,
    val correctPredictions: Int,
    val highPredictions: Int,
    val mediumPredictions: Int,
    val lowPredictions: Int
) {
    val accuracyPercentage: Double
        get() = if (totalCases > 0) {
            (correctPredictions.toDouble() / totalCases) * 100.0
        } else {
            0.0
        }
}


// runs the DNF backtest
suspend fun runDnfBacktestSuite(
    predictionRepo: DNFPredictionRepository,
    testCases: List<DnfBacktestTestCase>
): DnfBacktestReport {

    var correctPredictions = 0
    var highPredictions = 0
    var mediumPredictions = 0
    var lowPredictions = 0
    var validPredictions = 0

    testCases.forEach { test ->

        val predicted = predictionRepo.predictDnfRisk(
            test.firstName,
            test.lastName,
            test.cutoffDate
        )

        if (predicted != "Unknown") {

            // count the prediction categories
            when (predicted) {
                "High" -> highPredictions++
                "Medium" -> mediumPredictions++
                "Low" -> lowPredictions++
            }

            /*
             * High = predicted DNF
             * Medium/Low = predicted to finish
             */
            val predictedDnf = predicted == "High"

            if (predictedDnf == test.actualDnf) {
                correctPredictions++
            }

            validPredictions++

            // outputting the results of the test
            Log.d(
                "DNF_BACKTEST",
                "${test.firstName} ${test.lastName} | " +
                        "Pred: $predicted | " +
                        "Actual DNF: ${test.actualDnf} | " +
                        "Correct: ${predictedDnf == test.actualDnf}"
            )

        } else {

            Log.w(
                "DNF_BACKTEST",
                "Prediction returned Unknown for " +
                        "${test.firstName} ${test.lastName}"
            )
        }
    }

    val report = DnfBacktestReport(
        totalCases = validPredictions,
        correctPredictions = correctPredictions,
        highPredictions = highPredictions,
        mediumPredictions = mediumPredictions,
        lowPredictions = lowPredictions
    )

    Log.i("DNF_BACKTEST_RESULTS", "========================================")
    Log.i("DNF_BACKTEST_RESULTS", "Evaluated Cases : ${report.totalCases}")
    Log.i(
        "DNF_BACKTEST_RESULTS",
        "Correct         : ${report.correctPredictions}"
    )
    Log.i(
        "DNF_BACKTEST_RESULTS",
        "Accuracy        : %.1f%%".format(report.accuracyPercentage)
    )
    Log.i(
        "DNF_BACKTEST_RESULTS",
        "High Risk       : ${report.highPredictions}"
    )
    Log.i(
        "DNF_BACKTEST_RESULTS",
        "Medium Risk     : ${report.mediumPredictions}"
    )
    Log.i(
        "DNF_BACKTEST_RESULTS",
        "Low Risk        : ${report.lowPredictions}"
    )
    Log.i("DNF_BACKTEST_RESULTS", "========================================")

    return report
}


// dynamically fetch DNF test cases from OpenF1
suspend fun fetchDynamicDnfTestCases(
    sessionKey: String
): List<DnfBacktestTestCase> {

    val testCases = mutableListOf<DnfBacktestTestCase>()

    try {

        // query OpenF1
        val resultsUrl =
            "https://api.openf1.org/v1/session_result?session_key=$sessionKey"

        val driversUrl =
            "https://api.openf1.org/v1/drivers?session_key=$sessionKey"

        val resultsJson = JSONArray(URL(resultsUrl).readText())
        val driversJson = JSONArray(URL(driversUrl).readText())

        // mapping the driver to the driver info
        val driverMap = mutableMapOf<Int, Pair<String, String>>()

        for (i in 0 until driversJson.length()) {

            val obj = driversJson.getJSONObject(i)

            val num = obj.getInt("driver_number")
            val first = obj.optString("first_name", "")
            val last = obj.optString("last_name", "")

            driverMap[num] = Pair(first, last)
        }

        // building test cases from results
        for (i in 0 until resultsJson.length()) {

            val result = resultsJson.getJSONObject(i)

            val driverNum = result.getInt("driver_number")

            val actualPos = result.optInt("position", 0)

            val names = driverMap[driverNum]

            if (names != null) {

                /*
                 * A valid finishing position means the driver finished.
                 * No valid position means the driver did not finish.
                 */
                val actualDnf = actualPos <= 0

                testCases.add(
                    DnfBacktestTestCase(
                        firstName = names.first,
                        lastName = names.second,
                        actualDnf = actualDnf,
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


// runs the backtest across all sessions
suspend fun runDnfMultiSessionBacktest(
    predictionRepo: DNFPredictionRepository,
    sessionKeys: List<String>
): DnfBacktestReport {

    val allTestCases = mutableListOf<DnfBacktestTestCase>()

    // fetch test cases from every session
    sessionKeys.forEach { sessionKey ->

        Log.d(
            "DNF_BACKTEST",
            "Fetching dynamic DNF test cases for session: $sessionKey"
        )

        val cases = fetchDynamicDnfTestCases(sessionKey)

        allTestCases.addAll(cases)
    }

    Log.i(
        "DNF_BACKTEST",
        "Total driver cases collected across " +
                "${sessionKeys.size} sessions: ${allTestCases.size}"
    )

    // run the backtest across the combined data set
    return runDnfBacktestSuite(
        predictionRepo,
        allTestCases
    )
}
