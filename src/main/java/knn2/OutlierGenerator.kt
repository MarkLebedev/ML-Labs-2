package knn2

import knn1.*
import kotlin.random.Random

class OutlierGenerator {

    fun generateOutliers(train: MutableList<Pair<Int, Array<IntArray>>>, outliersRate: Int): MutableList<Pair<Pair<Int, Boolean>, Array<IntArray>>> {

        val trainWithOutliers = mutableListOf<Pair<Pair<Int, Boolean>, Array<IntArray>>>()

        for (i in train.indices) {
            if (Random.nextInt(0,101) < outliersRate) {
                var wrongNumber = 0

                do {wrongNumber = Random.nextInt(0,10)} while (wrongNumber == train[i].first)

                trainWithOutliers.add(Pair(Pair(wrongNumber, true), train[i].second))
            } else {
                trainWithOutliers.add(Pair(Pair(train[i].first, false), train[i].second))
            }
        }

        return trainWithOutliers

    }

}