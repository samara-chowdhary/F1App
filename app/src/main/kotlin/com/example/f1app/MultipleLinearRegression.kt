package com.example.f1app

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier

//prediction vals
val predictX =  listOf<Double>()
val predictW =  listOf<Double>()
var b = 0

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
fun computeCost(costX: List<List<Double>>, costy: List<Double>, predictW: List<Double>, b: Double): Double {
    val m = costY.size;
    val totalCost: Double = 0.0;

    for(i in 0 until m){
        val prediction = predict(costX[i], predictW, b)
        val error = prediction - costY[i]
        val totalCost = totalCost + (error * error)
    }

    val J = totalCost / (m * 2.0)

    return J
}

//gradient computation function
fun computeGradient(costX: List<List<Double>>, costY: List<Double>, predictW: List<Double>, b: Double){

}
