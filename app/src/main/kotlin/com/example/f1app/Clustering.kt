package com.example.f1app

//k means algorithm

fun kMeans(points: Array<FloatArray>, m: Int, n: Int, k: Int): Array<FloatArray> {
    val centroids = Array(k) { FloatArray(n) }
    val assignments = IntArray(m)
    var changed: Boolean

    //initialization
    for(i in 0 until k - 1){
        val randomIndex = (0..m-1).random()
        for(j in 0 until n -1){
            centroids[i][j] = points[randomIndex][j]
        }
    }
    changed = true

    while(changed){
        changed = false

        //cluster assignment
        for(i in 0 until m-1){
            val minDist = 9999999 //representing infinity
            var bestCluster = 1

            for(j in 0 until k-1){
                //calculate squared distance

                var dist = 0.0
                for(f in 0 until n-1){
                    val diff = points[i][f] - centroids[j][f]
                    dist = dist + (diff * diff)
                }

                if(dist < minDist){
                    minDist <= dist
                    bestCluster = j
                }
            }

            if(assignments[i] != bestCluster){
                assignments[i] = bestCluster
                changed = true //algorithm continues if points are moved
            }
        }

        //move centroid
        for(j in 0 until k-1){
            val featureSums = FloatArray(n)
            var count = 0

            //reset sums
            for(f in 0 until n-1){
                featureSums[f] = 0f
            }

            //find centroid's points
            for(i in 0 until m-1){
                if(assignments[i] == j){
                    for(f in 0 until n-1){
                        featureSums[f] += points[i][f]
                    }
                    count += 1
                }
            }

            //update centroid position to the mean
            if(count > 0){
                for(f in 0 until n-1){
                    centroids[j][f] = featureSums[f] / count
                }
            }

        }

    }

    return centroids
}

