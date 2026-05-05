package com.example.f1app

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import kotlinx.serialization.descriptors.PrimitiveKind

//prediction vals
val predictX =  listOf<Double>()
val predictW =  listOf<Double>()
var b: Double = 0.0

//prediction function
fun predict(predictX: List<Double>, predictW: List<Double>, b: Double): Double {
    var total: Double = 0.0

    for (j in predictW.indices){
        total += predictW[j] * predictX[j]
    }
    return total + b
}
//cost vals
val costX : List<List<Double>> = listOf()
val costY = listOf<Double>()

//cost function
fun computeCost(costX: List<List<Double>>, costY: List<Double>, predictW: List<Double>, b: Double): Double {
    val m = costY.size;
    var totalCostAcc: Double = 0.0;

    for(i in 0 until m){
        val prediction = predict(costX[i], predictW, b)
        val error = prediction - costY[i]
        totalCostAcc += (error * error)
    }

    return totalCostAcc / (m * 2.0)
}

//gradient computation function
fun computeGradient(costX: List<List<Double>>, costY: List<Double>, predictW: List<Double>, b: Double, m: Int ): Pair<DoubleArray, Double> {
    var dj_dw = DoubleArray(predictW.size)
    var dj_db = 0.0

    for(j in 0 until predictW.size){
        dj_dw[j] = 0.0
    }

    for(i in 0 until m){
        val prediction = predict(costX[i], predictW, b)
        val error = prediction - costY[i]
        dj_db += error

        for(j in 0 until predictW.size){
            dj_dw[j] = dj_dw[j] + (error * costX[i][j])
        }
    }

    dj_db /= m

    for(j in 0 until predictW.size){
        dj_dw[j] = dj_dw[j] / m
    }

    return Pair(dj_dw, dj_db)
}


//gradient descent calculation

fun gradientDescent(X: List<List<Double>>, y: List<Double>, initialW: List<Double>, initialB: Double, alpha: Double, iterations: Int): Pair<List<Double>, Double> {

    //mutable versions - so they can be updated
    var w = initialW.toDoubleArray()
    var gradB = initialB
    val m = X.size

    for (iter in 0 until iterations) {
        //calculate the gradient
        val (dj_dw, dj_db) = computeGradient(X, y, w.toList(), gradB, m)

        //update all of the weights in w
        for (j in 0 until w.size) {
            w[j] = w[j] - (alpha * dj_dw[j])
        }

        //update the bias
        gradB -= (alpha * dj_db)

        // Print progress every 1000 steps
        if (iter % 1000 == 0) {
            val cost = computeCost(X, y, w.toList(), gradB)
            println("Iteration $iter: Cost = $cost")
        }
    }

    return Pair(w.toList(), gradB)
}

//test
fun test() {
    // Data for y = 2x + 1
    val X = listOf(listOf(1.0), listOf(2.0), listOf(3.0), listOf(4.0))
    val y = listOf(3.0, 5.0, 7.0, 9.0)

    val (finalW, finalB) = gradientDescent(X, y, listOf(0.0), 0.0, 0.01, 5000)

    println("--- Results ---")
    println("Final Weight: ${finalW[0]} (Target: 2.0)")
    println("Final Bias: $finalB (Target: 1.0)")
}



