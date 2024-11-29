package knn2

import knn1.Numbers
import knn1.TrainGenerator
import knn1.distance.EuclidianDistanceCalculator
import knn1.noise.BWEvenNoiser
import knn2.outlierDetectors.*
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.io.*
import org.jetbrains.kotlinx.dataframe.api.*

fun main() {
    val trainGenerator = TrainGenerator()
    val outlierGenerator = OutlierGenerator()
    val noiser = BWEvenNoiser()
    val numbers = Numbers()
    val distance = EuclidianDistanceCalculator()

    val outlierDetector = KNNOutlierDetector()

    val train = trainGenerator.generate(100, false, noiser, 30)
    val trainWithOutliers = outlierGenerator.generateOutliers(train, 20)

    val outliers = outlierDetector.detect(train, trainWithOutliers, distance, 7)

    val fn: MutableList<MutableList<Int>> = MutableList(10) { MutableList(10) {0} }
    fn.add(0, mutableListOf(0,1,2,3,4,5,6,7,8,9))
    val fp: MutableList<Int> = MutableList(10) {0}
    var results = mutableMapOf("TP" to 0.0, "FP" to 0.0, "TN" to 0.0, "FN" to 0.0)
    for (i in outliers.indices) {
        if (outliers[i] == true) {
            if (trainWithOutliers[i].first.second == true) {
                results["TP"] = results["TP"]!!.plus(1.0)
            } else {results["FP"] = results["FP"]!!.plus(1.0)
            fp[trainWithOutliers[i].first.first]+=1}
        } else {
            if (trainWithOutliers[i].first.second == true) {
                results["FN"] = results["FN"]!!.plus(1.0)
                fn[train[i].first+1][trainWithOutliers[i].first.first] += 1
            } else {results["TN"] = results["TN"]!!.plus(1.0)}
        }
    }

    val accuracy = (results["TP"]!! + results["TN"]!!)/(results["TP"]!! + results["TN"]!! + results["FP"]!! + results["FN"]!!)
    val precision = (results["TP"]!!)/(results["TP"]!! + results["FP"]!!)

    println(results)
    //println("FN: $fn")
    println("FP:")
    println(dataFrameOf(listOf("0","1","2","3","4","5","6","7","8","9"), fp))

    println("Accuracy: $accuracy, Precision: $precision")
}