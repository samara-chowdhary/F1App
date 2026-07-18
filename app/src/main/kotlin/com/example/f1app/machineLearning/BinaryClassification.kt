package com.example.f1app.machineLearning

import kotlin.math.ln
import kotlin.math.pow

//function for creating the sigmoid
fun sigmoid(z: Double): Double {
    val e = 2.71828
    val result = 1/(1 + e.pow(-z))
    return result
}

//calculating the 'z' value
fun calculateZ(weights: List<Double>, features: List<Double>, bias: Double): Double {
    var z = 0.0
    for (i in 0 until weights.size) {
        z += (weights[i] * features[i])
    }
    z += bias
    return z
}

//prediction function
fun predictBinary(weights: List<Double>, features: List<Double>, bias: Double) : Double {
    val z = calculateZ(weights, features, bias)
    val probability = sigmoid(z)
    if(probability >= 0.5){
        return 1.0
    }
    else{
        return 0.0
    }
}

fun predictProbability(weights: List<Double>, features: List<Double>, bias: Double): Double {  // added: needed for DNF probability
    val z = calculateZ(weights, features, bias)
    return sigmoid(z)
}

//loss function
fun calculateLoss(prediction: Double, actual: Double) : Double{

    val term1 = -actual * ln(prediction + 1e-9)
    val term2 = (1 - actual) * ln(1 - prediction + 1e-9)

    val loss = term1 - term2
    return loss
}
