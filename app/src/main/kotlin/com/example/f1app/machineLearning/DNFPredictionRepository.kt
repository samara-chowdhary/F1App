package com.example.f1app.machineLearning

import com.example.f1app.databaseEntities.DriverDao

class DNFPredictionRepository(val driverDao: DriverDao) {

    suspend fun predictDnfRisk(
        firstName: String,
        lastName: String,
        cutOffDate: String?
    ): String {
        val dnfResults = driverDao.getRecentDNFs(firstName, lastName, cutOffDate = cutOffDate)

        if (dnfResults.isEmpty()) return "Unknown"

        val dnfRate = dnfResults.count { it.dnf } / dnfResults.size.toDouble()
        val raceCount = dnfResults.size.toDouble()

        // logistic regression features: [dnf rate, normalised race count]
        val features = listOf(dnfRate, raceCount / 10.0)

        // weights trained on typical F1 reliability patterns
        val weights = listOf(2.5, -0.3)
        val bias = -1.8

        val probability = predictProbability(weights, features, bias)

        return when {
            probability >= 0.35 -> "High"
            probability >= 0.15 -> "Medium"
            else -> "Low"
        }
    }

    private fun predictProbability(weights: List<Double>, features: List<Double>, bias: Double): Double {
        val z = weights.zip(features).sumOf { (w, x) -> w * x } + bias
        return 1.0 / (1.0 + Math.exp(-z))
    }
}