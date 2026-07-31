package com.example.f1app.machineLearning

import com.example.f1app.databaseEntities.DriverDao

class DNFPredictionRepository(val driverDao: DriverDao) {

    suspend fun predictDnfRisk(
        firstName: String,
        lastName: String,
        isWetRace: Boolean = false,
        cutOffDate: String?
    ): String {
        val dnfResults = if (isWetRace) {
            driverDao.getWetRaceDNFs(firstName, lastName, cutoffDate = cutOffDate)
        } else {
            driverDao.getRecentDNFs(firstName, lastName, cutOffDate = cutOffDate)
        }

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

    suspend fun getWetWeatherDnfImpact(
        firstName: String,
        lastName: String,
        cutOffDate: String? = null
    ): String {
        val wetDnfs = driverDao.getWetRaceDNFs(firstName, lastName, cutoffDate = cutOffDate)
        val dryDnfs = driverDao.getDryRaceDNFs(firstName, lastName, cutoffDate = cutOffDate)

        if (wetDnfs.isEmpty() || dryDnfs.isEmpty()) return "Not enough data"

        // logistic regression for both wet and dry
        val wetRate = wetDnfs.count { it.dnf } / wetDnfs.size.toDouble()
        val dryRate = dryDnfs.count { it.dnf } / dryDnfs.size.toDouble()

        val weights = listOf(2.5, -0.3)
        val bias = -1.8

        val wetProb = predictProbability(weights, listOf(wetRate, wetDnfs.size / 10.0), bias) * 100
        val dryProb = predictProbability(weights, listOf(dryRate, dryDnfs.size / 10.0), bias) * 100
        val difference = wetProb - dryProb

        return when {
            difference > 5.0 -> "+${"%.0f".format(difference)}% more likely to DNF in the wet"
            difference < -5.0 -> "${"%.0f".format(-difference)}% less likely to DNF in the wet"
            else -> "No significant difference in the wet"
        }
    }

    private fun predictProbability(weights: List<Double>, features: List<Double>, bias: Double): Double {
        val z = weights.zip(features).sumOf { (w, x) -> w * x } + bias
        return 1.0 / (1.0 + Math.exp(-z))
    }
}