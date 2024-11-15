package knn2

import knn1.Numbers
import knn1.TrainGenerator
import knn1.distance.EuclidianDistanceCalculator
import knn1.noise.BWEvenNoiser
import knn2.outlierDetectors.*

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

    var results = mutableMapOf("TP" to 0.0, "FP" to 0.0, "TN" to 0.0, "FN" to 0.0)
    for (i in outliers.indices) {
        if (outliers[i] == true) {
            if (trainWithOutliers[i].first.second == true) {
                results["TP"] = results["TP"]!!.plus(1.0)
            } else {results["FP"] = results["FP"]!!.plus(1.0)}
        } else {
            if (trainWithOutliers[i].first.second == true) {
                results["FN"] = results["FN"]!!.plus(1.0)
            } else {results["TN"] = results["TN"]!!.plus(1.0)}
        }
    }

    val accuracy = (results["TP"]!! + results["TN"]!!)/(results["TP"]!! + results["TN"]!! + results["FP"]!! + results["FN"]!!)
    val precision = (results["TP"]!!)/(results["TP"]!! + results["FP"]!!)

    println(results)

    println("Accuracy: $accuracy, Precision: $precision")
}