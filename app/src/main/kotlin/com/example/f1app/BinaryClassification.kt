package com.example.f1app

import kotlin.math.pow

val weights =  listOf<Double>()
val features =  listOf<Double>()
val bias: Double = 0.0;

//calculating the "z' value
fun calculateZ(weights: List<Double>, features: List<Double>, bias: Double, z: Double): Double {
    var z = 0.0
    for(i in 0 until (weights.size - 1)){
        z += (weights[i] * features[i])

    }
    z += bias
    return z
}

//function for creating the sigmoid
fun sigmoid(z: Double): Double {
    val e = 2.71828
    val result = 1/(1 + e.pow(-z))
    return result
}

//fun predict(weights: List<Double>, features: List<Double>, bias: Double, z: Double): Double {
    //z = calculateZ(weights, features, bias)
    //probability = sigmoid(Z)
//}