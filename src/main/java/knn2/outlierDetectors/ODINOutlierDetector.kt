package knn2.outlierDetectors

import jdk.jfr.Threshold
import knn1.IterationCalculator
import knn1.distance.ParentDistanceCalculator

class ODINOutlierDetector {
    val calculator = IterationCalculator()

    fun detect(train: MutableList<Pair<Int, Array<IntArray>>>, trainWithOutliers: MutableList<Pair<Pair<Int, Boolean>, Array<IntArray>>>,
               distance: ParentDistanceCalculator, k: Int, inDegreeThreshold: Int): MutableList<Boolean> {
        val outliers = mutableListOf<Boolean>()


        for (i in trainWithOutliers.indices) {

            var inDegreeNumber = 0

            for (j in trainWithOutliers.indices) {
                val number = trainWithOutliers[j].second
                val distances = Array<Pair<Int, Double>>(k) { Pair(0, 99999999.0) }
                for (n in trainWithOutliers.indices){
                    if (n != j) {
                        val d = distance.distance(number, trainWithOutliers[n].second)

                        if (d < distances[calculator.findMaxDistanceInK(distances)].second) {
                            distances[calculator.findMaxDistanceInK(distances)] = Pair(n, d)}
                    }
                }

                for (m in distances.indices) {
                    if (distances[m].first == i) {inDegreeNumber += 1; break}
                }
            }

            if (inDegreeNumber < inDegreeThreshold) {outliers.add(true)} else {outliers.add(false)}
        }

        return outliers
    }

}