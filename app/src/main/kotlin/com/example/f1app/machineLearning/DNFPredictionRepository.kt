package com.example.f1app.machineLearning

import android.util.Log
import com.example.f1app.databaseEntities.DriverDao

class DNFPredictionRepository(val driverDao: DriverDao) {

    suspend fun predictDnfRisk(firstName: String, lastName: String): String {
        val recentResults = driverDao.getRecentDNFs(firstName, lastName)

        if (recentResults.isEmpty()) {
            return "Unknown"
        }

        val dnfCount = recentResults.count { it.dnf }
        val totalRaces = recentResults.size
        val dnfRate = dnfCount.toDouble() / totalRaces

        Log.d("DNF_PREDICTION", "$firstName $lastName: $dnfCount/$totalRaces DNFs, rate: $dnfRate")

        return when {
            dnfRate >= 0.9 -> "High"
            dnfRate >= 0.5 -> "Medium"
            else -> "Low"
        }
    }
}