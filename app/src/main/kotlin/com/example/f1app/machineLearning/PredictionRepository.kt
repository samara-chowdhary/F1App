package com.example.f1app.machineLearning

import android.util.Log
import com.example.f1app.databaseEntities.DriverDao

class PredictionRepository(val driverDao: DriverDao) {

    var trainedW: List<Double> = listOf(0.0, 0.0, 0.0)
    var trainedB: Double = 0.0
    var isModelTrained = false

    suspend fun trainModelForDriver(firstName: String, lastName: String, trackLocation: String) {
        val recentRaw = driverDao.getRecentPositions(firstName, lastName)
        val recentPositions = recentRaw.map { it.position }

        if (recentPositions.size < 2) return

        val costX = mutableListOf<List<Double>>()
        val costY = mutableListOf<Double>()

        for (i in 1 until recentPositions.size) {
            val historySubset = recentPositions.subList(0, i)
            val avgPos = historySubset.average()
            val lastPos = historySubset.last().toDouble()
            val bestPos = historySubset.minOrNull()?.toDouble() ?: avgPos
            costX.add(listOf(avgPos, lastPos, bestPos))
            costY.add(recentPositions[i].toDouble())
        }

        if (costX.isEmpty()) return

        val (finalW, finalB) = gradientDescent(
            X = costX,
            y = costY,
            initialW = listOf(0.0, 0.0, 0.0),
            initialB = 0.0,
            alpha = 0.001,
            iterations = 5000
        )

        this.trainedW = finalW
        this.trainedB = finalB
        this.isModelTrained = true
    }

    suspend fun predictNextPosition(
        firstName: String,
        lastName: String,
        trackLocation: String,
        isWetRace: Boolean = false
    ): Double? {
        val recentRaw = if (isWetRace) {
            driverDao.getWetRacePositions(firstName, lastName)
        } else {
            driverDao.getRecentPositions(firstName, lastName)
        }
        val recentPositions = recentRaw.map { it.position }

        val trackRaw = driverDao.getHistoricalPositions(firstName, lastName, "%$trackLocation%")
        val trackPositions = trackRaw.map { it.position }

        if (recentPositions.isEmpty() && trackPositions.isEmpty()) return null

        val recentAvg = if (recentPositions.isNotEmpty()) recentPositions.average() else null
        val trackAvg = if (trackPositions.isNotEmpty()) trackPositions.average() else null

        val prediction = when {
            recentAvg != null && trackAvg != null -> (recentAvg * 0.7) + (trackAvg * 0.3)
            recentAvg != null -> recentAvg
            else -> trackAvg!!
        }

        return prediction.coerceIn(1.0, 20.0)
    }

    private fun predict(x: List<Double>, w: List<Double>, b: Double): Double {
        return x.zip(w).sumOf { it.first * it.second } + b
    }

    private fun gradientDescent(
        X: List<List<Double>>,
        y: List<Double>,
        initialW: List<Double>,
        initialB: Double,
        alpha: Double,
        iterations: Int
    ): Pair<List<Double>, Double> {
        var w = initialW.toMutableList()
        var b = initialB
        val m = X.size
        val n = w.size

        repeat(iterations) {
            val dw = MutableList(n) { 0.0 }
            var db = 0.0

            for (i in 0 until m) {
                val prediction = predict(X[i], w, b)
                val error = prediction - y[i]
                for (j in 0 until n) {
                    dw[j] += error * X[i][j]
                }
                db += error
            }

            for (j in 0 until n) {
                w[j] -= alpha * dw[j] / m
            }
            b -= alpha * db / m
        }

        return Pair(w, b)
    }

    private fun removeAnomalies(positions: List<Int>): List<Int> {
        if (positions.size < 4) return positions

        val trainingData = positions.map { listOf(it.toDouble()) }
        val m = trainingData.size
        val n = 1

        val (means, variances) = trainModel(trainingData, m, n)
        val epsilon = 0.05

        val filtered = positions.filter { pos ->
            val xTest = doubleArrayOf(pos.toDouble())
            !isAnomalous(xTest, means, variances, n, epsilon)
        }

        Log.d("PREDICTION", "Anomaly detection: $positions -> $filtered")

        return if (filtered.size < 2) positions else filtered
    }
}