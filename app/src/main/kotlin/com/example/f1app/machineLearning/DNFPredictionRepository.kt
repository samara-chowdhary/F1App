package com.example.f1app.machineLearning
import com.example.f1app.databaseEntities.DriverDao

class DNFPredictionRepository(val driverDao: DriverDao) {

    suspend fun predictDnfRisk(firstName: String, lastName: String, isWetRace: Boolean = false): String {

        val positions = if (isWetRace) {
            driverDao.getWetRacePositions(firstName, lastName).map { it.position }
        } else {
            driverDao.getRecentPositions(firstName, lastName).map { it.position }
        }

        if (positions.size < 3) return "Unknown"

        val avg = positions.average()
        val variance = positions.sumOf { (it - avg) * (it - avg) } / positions.size
        val stdDev = Math.sqrt(variance)

        return when {
            stdDev >= 6.0 -> "High"
            stdDev >= 3.0 -> "Medium"
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