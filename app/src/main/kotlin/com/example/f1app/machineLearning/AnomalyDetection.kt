package com.example.f1app.machineLearning

import kotlin.math.sqrt

//the function for training model
val trainingData: List<List<Double>> =  listOf()
val m  = 0
val n = 0

fun trainModel(trainingData: List<List<Double>>, m: Int, n: Int): Pair<DoubleArray, DoubleArray> {
    val means = DoubleArray(n)
    val variances = DoubleArray(n)

    for(j in 0 until n -1){
        //calculate the mean for feature j
        var total = 0.0
        for(i in 0 until m - 1){
            total  = total + trainingData[i][j]
        }

        means[j] = total / m

        //calculate variance for feature j
        var sumSquaredDiff = 0.0;
        for(i in 0 until m - 1){
            val diff = trainingData[i][j] - means[j]
            sumSquaredDiff = sumSquaredDiff + (diff * diff)
        }
        variances[j] = sumSquaredDiff / m
    }

    return Pair(means, variances)
}

//anomally detection function

val xTest = DoubleArray(n)
val means = DoubleArray(n)
val variances = DoubleArray(n)
val epsilon = 0.0

fun isAnomalous(xTest: DoubleArray, means: DoubleArray, variances: DoubleArray, n: Int, epsilon: Double): Boolean {
    var pTotal = 1.0
    val constantPI = 3.14159
    val e = 2.71828

    for(j in 0 until n - 1){
        val mu = means[j]
        val sigmaSq = variances[j]
        val x = xTest[j]

        //implementing the formula
        val exponent = -(Math.pow((x-mu), 2.0)) / (2 * sigmaSq)
        val prob_j = (1 / sqrt(2.0 * constantPI * sigmaSq)) * (Math.pow(e, exponent))

        //multiply probabilities
        pTotal = pTotal * prob_j
    }

    if(pTotal < epsilon){
        return true //it is an anomaly
    }
    else{
        return false //it is normal
    }
}