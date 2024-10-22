package knn2.outlierDetectors

import knn1.IterationCalculator
import knn1.distance.ParentDistanceCalculator

class KNNOutlierDetector {
    val calculator = IterationCalculator()

    fun detect(train: MutableList<Pair<Int, Array<IntArray>>>,trainWithOutliers: MutableList<Pair<Pair<Int, Boolean>, Array<IntArray>>>,
               distance: ParentDistanceCalculator, k: Int): MutableList<Boolean> {
        val outliers = mutableListOf<Boolean>()

        for (i in trainWithOutliers.indices) {
            val number = trainWithOutliers[i].second

            val newTrain = mutableListOf<Pair<Int, Array<IntArray>>>()
            for (j in train.indices) {
                newTrain.add(train[j].copy())
            }

            newTrain.remove(train[i])

            val distances = calculator.kNearestDistances(distance, number, newTrain, k)

            val predictedNumber = calculator.findMostCommonNeighbour(distances)

            val isOutlier = (predictedNumber != trainWithOutliers[i].first.first)

            outliers.add(isOutlier)
        }

        return outliers
    }

}