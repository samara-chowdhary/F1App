package com.example.f1app

import android.util.Log
import kotlin.math.ln
import kotlin.math.pow

val weights =  listOf<Double>()
val features =  listOf<Double>()
val bias: Double = 0.0;

fun main() {

    // --- Test 1: sigmoid boundaries ---
    // sigmoid of a large positive number should be close to 1
    assert(sigmoid(10.0) > 0.99) { "FAIL: sigmoid(10.0) should be close to 1" }
    // sigmoid of a large negative number should be close to 0
    assert(sigmoid(-10.0) < 0.01) { "FAIL: sigmoid(-10.0) should be close to 0" }
    // sigmoid(0) should be exactly 0.5
    assert(sigmoid(0.0) == 0.5) { "FAIL: sigmoid(0.0) should be 0.5" }
    println("PASS: sigmoid tests")

    // --- Test 2: calculateZ ---
    // z = (1.0 * 2.0) + (2.0 * 3.0) + 0.5 bias = 2 + 6 + 0.5 = 8.5
    val testWeights = listOf(1.0, 2.0)
    val testFeatures = listOf(2.0, 3.0)
    val testBias = 0.5
    val expectedZ = 8.5
    assert(calculateZ(testWeights, testFeatures, testBias) == expectedZ) {
        "FAIL: calculateZ expected $expectedZ but got ${calculateZ(testWeights, testFeatures, testBias)}"
    }
    println("PASS: calculateZ test")

    // --- Test 3: predict returns 1.0 for high probability ---
    // large positive z means sigmoid > 0.5, so predict should return 1.0
    val highWeights = listOf(10.0)
    val highFeatures = listOf(1.0)
    assert(predict(highWeights, highFeatures, 0.0) == 1.0) { "FAIL: should predict 1.0 for high z" }
    println("PASS: predict high test")

    // --- Test 4: predict returns 0.0 for low probability ---
    // large negative z means sigmoid < 0.5, so predict should return 0.0
    val lowWeights = listOf(-10.0)
    val lowFeatures = listOf(1.0)
    assert(predict(lowWeights, lowFeatures, 0.0) == 0.0) { "FAIL: should predict 0.0 for low z" }
    println("PASS: predict low test")

    // --- Test 5: loss is 0 when prediction matches actual ---
    // if actual = 0, prediction = near 0, loss should be near 0
    val loss = calculateLoss(0.0001, 0.0)
    assert(loss < 0.01) { "FAIL: loss should be near 0 when prediction matches actual" }
    println("PASS: calculateLoss test")

    println("\nAll tests passed!")
}


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
fun predict() : Double {
    val z = calculateZ(weights, features, bias)
    val probability = sigmoid(z)
    if(probability >= 0.5){
        return 1.0
    }
    else{
        return 0.0
    }
}

//loss function
fun calculateLoss(prediction: Double, actual: Double) : Double{

    val term1 = -actual * ln(prediction)
    val term2 = (1 - actual) * ln(1 - prediction)

    val loss = term1 - term2
    return loss
}
