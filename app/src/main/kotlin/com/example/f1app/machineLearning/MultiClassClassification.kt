package com.example.f1app.machineLearning

import kotlin.math.ln

val multiWeights: List<List<Double>> = listOf()
val biases = listOf<Double>()


//function for calculating all of the z values
fun calculateAllZ(multiWeights: List<List<Double>>, features: List<Double>, biases: List<Double>) {
    val scores = DoubleArray(biases.size)

    for (i in 0 until biases.size - 1) {
        var z: Double = 0.0
        for (j in 0 until features.size - 1) {
            z = z + (multiWeights[i][j] * features[j])
        }
        scores[i] = z + biases[i]
    }
}

//application of the softmax function
fun applySoftmax(scores: DoubleArray): DoubleArray {
    val probabilities = DoubleArray(scores.size)
    var sumExp: Double = 0.0
    val e = 2.71828

    //calculate the denominator
    for(i in 0 until scores.size - 1){
        sumExp = sumExp + (Math.pow(e, scores[i]))
    }

    //divide each e^z by the sum
    for(i in 0 until scores.size - 1){
        probabilities[i] = Math.pow(e, scores[i]) / sumExp
    }

    return probabilities
}

//calculate the cross-entropy loss
val trueClassIndex = 0

fun calculateCrossEntropy(probabilities: DoubleArray, trueClassIndex: Int): Double {
    val loss = -1 * ln(probabilities[trueClassIndex] + 0.000000001)
    return loss
}