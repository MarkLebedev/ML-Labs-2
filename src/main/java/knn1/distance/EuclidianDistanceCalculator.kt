package knn1.distance
import kotlin.math.*

class EuclidianDistanceCalculator: ParentDistanceCalculator() {

    override fun distance(number: Array<IntArray>, template: Array<IntArray>): Double {
        val converter = IntToDoubleConverter()
        val newNumber = converter.convert(number)
        val newTemplate = converter.convert(template)

        var distance = 0.0
        for (row in newNumber.indices) {
            for (col in newNumber[row].indices) {
                val diff = newNumber[row][col] - newTemplate[row][col]
                distance += diff.pow(2)
            }
        }

        return sqrt(distance)
    }

}