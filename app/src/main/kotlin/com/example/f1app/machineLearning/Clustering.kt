package com.example.f1app.machineLearning

//k means algorithm

fun kMeans(points: Array<FloatArray>, m: Int, n: Int, k: Int): Array<FloatArray> {
    val centroids = Array(k) { FloatArray(n) }
    val assignments = IntArray(m)
    var changed: Boolean

    //initialization
    for(i in 0 until k){
        val randomIndex = (0..m-1).random()
        for(j in 0 until n -1){
            centroids[i][j] = points[randomIndex][j]
        }
    }
    changed = true

    while(changed){
        changed = false

        //cluster assignment
        for(i in 0 until m){
            var minDist: Double = 9999999.0 //representing infinity
            var bestCluster = 1

            for(j in 0 until k){
                //calculate squared distance

                var dist = 0.0
                for(f in 0 until n){
                    val diff = points[i][f] - centroids[j][f]
                    dist = dist + (diff * diff)
                }

                if(dist < minDist){
                    minDist = dist
                    bestCluster = j
                }
            }

            if(assignments[i] != bestCluster){
                assignments[i] = bestCluster
                changed = true //algorithm continues if points are moved
            }
        }

        //move centroid
        for(j in 0 until k){
            val featureSums = FloatArray(n)
            var count = 0

            //reset sums
            for(f in 0 until n){
                featureSums[f] = 0f
            }

            //find centroid's points
            for(i in 0 until m){
                if(assignments[i] == j){
                    for(f in 0 until n){
                        featureSums[f] += points[i][f]
                    }
                    count += 1
                }
            }

            //update centroid position to the mean
            if(count > 0){
                for(f in 0 until n){
                    centroids[j][f] = featureSums[f] / count
                }
            }

        }

    }

    return centroids
}

fun assignCluster(point: FloatArray, centroids: Array<FloatArray>): Int {
    var minDist = Double.MAX_VALUE
    var bestCluster = 0
    for (j in centroids.indices) {
        var dist = 0.0
        for (f in point.indices) {
            val diff = point[f] - centroids[j][f]
            dist += diff * diff
        }
        if (dist < minDist) {
            minDist = dist
            bestCluster = j
        }
    }
    return bestCluster
}
