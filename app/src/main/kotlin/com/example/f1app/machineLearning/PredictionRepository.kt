package com.example.f1app.machineLearning

import com.example.f1app.databaseEntities.DriverDao

class PredictionRepository(val driverDao: DriverDao) {

    var trainedW: List<Double> = listOf(0.0, 0.0, 0.0)
    var trainedB: Double = 0.0
    var isModelTrained = false

    fun trainModelForDriver(firstName: String, lastName: String, trackLocation: String) {
        val rawPositions = driverDao.getHistoricalPositions(firstName, lastName, "%$trackLocation%")
        val pastPositions = rawPositions.map { it.position }

        if (pastPositions.size < 2) return

        val costX = mutableListOf<List<Double>>()
        val costY = mutableListOf<Double>()

        for (i in 1 until pastPositions.size) {
            val historySubset = pastPositions.subList(0, i)

            val avgPos = historySubset.average()
            val lastPos = historySubset.last().toDouble()
            val bestPos = historySubset.minOrNull()?.toDouble() ?: avgPos

            costX.add(listOf(avgPos, lastPos, bestPos))
            costY.add(pastPositions[i].toDouble())
        }

        if (costX.isEmpty()) return

        val initialW = listOf(0.0, 0.0, 0.0)
        val initialB = 0.0

        val (finalW, finalB) = gradientDescent(
            X = costX,
            y = costY,
            initialW = initialW,
            initialB = initialB,
            alpha = 0.01,
            iterations = 5000
        )

        this.trainedW = finalW
        this.trainedB = finalB
        this.isModelTrained = true
    }

    fun predictNextPosition(firstName: String, lastName: String, trackLocation: String): Double? {
        val rawPositions = driverDao.getHistoricalPositions(firstName, lastName, "%$trackLocation%")
        val pastPositions = rawPositions.map { it.position }
        if (pastPositions.isEmpty()) return null

        if (!isModelTrained) {
            trainModelForDriver(firstName, lastName, trackLocation)
        }

        val currentAvg = pastPositions.average()
        val currentLast = pastPositions.last().toDouble()
        val currentBest = pastPositions.minOrNull()?.toDouble() ?: currentAvg

        val predictX = listOf(currentAvg, currentLast, currentBest)

        return predict(predictX, trainedW, trainedB)
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
        return Pair(initialW, initialB)
    }
}