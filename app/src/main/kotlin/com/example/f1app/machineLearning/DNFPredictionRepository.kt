package com.example.f1app.machineLearning

import com.example.f1app.databaseEntities.DriverDao

class DNFPredictionRepository(val driverDao: DriverDao) {

    suspend fun predictDnfRisk(firstName: String, lastName: String, isWetRace: Boolean = false): String {
        val dnfResults = if (isWetRace) {
            driverDao.getWetRaceDNFs(firstName, lastName)
        } else {
            driverDao.getRecentDNFs(firstName, lastName)
        }

        if (dnfResults.isEmpty()) return "Unknown"

        val dnfRate = dnfResults.count { it.dnf } / dnfResults.size.toDouble()

        return when {
            dnfRate >= 0.3 -> "High"
            dnfRate >= 0.15 -> "Medium"
            else -> "Low"
        }
    }

    suspend fun getWetWeatherDnfImpact(firstName: String, lastName: String): String {
        val wetDnfs = driverDao.getWetRaceDNFs(firstName, lastName)
        val dryDnfs = driverDao.getDryRaceDNFs(firstName, lastName)

        if (wetDnfs.isEmpty() || dryDnfs.isEmpty()) return "Not enough data"

        val wetRate = wetDnfs.count { it.dnf } / wetDnfs.size.toDouble() * 100
        val dryRate = dryDnfs.count { it.dnf } / dryDnfs.size.toDouble() * 100
        val difference = wetRate - dryRate

        return when {
            difference > 5.0 -> "+${"%.0f".format(difference)}% more likely to DNF in the wet"
            difference < -5.0 -> "${"%.0f".format(-difference)}% less likely to DNF in the wet"
            else -> "No significant difference in the wet"
        }
    }
}