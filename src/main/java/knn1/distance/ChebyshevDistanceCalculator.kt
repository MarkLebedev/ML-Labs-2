package knn1.distance

import kotlin.math.absoluteValue

class ChebyshevDistanceCalculator: ParentDistanceCalculator() {

    override fun distance(number: Array<IntArray>, template: Array<IntArray>): Double {
        val converter = IntToDoubleConverter()
        val newNumber = converter.convert(number)
        val newTemplate = converter.convert(template)

        var maxDistance = 0.0
        var distance = 0.0
        for (row in newNumber.indices) {
            for (col in newNumber[row].indices) {
                val diff = newNumber[row][col] - newTemplate[row][col]
                distance = diff.absoluteValue
                if (distance > maxDistance) {
                    maxDistance = distance
                }
            }
        }

        return distance
    }

}