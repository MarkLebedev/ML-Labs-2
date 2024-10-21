package knn1

import knn1.distance.ParentDistanceCalculator
import knn1.noise.ParentNoiser
import kotlin.random.Random

class IterationCalculator {

    fun calculate(iterations: Int, noiser: ParentNoiser, noiseLevel: Int, numbers: Array<Array<IntArray>>, distance: ParentDistanceCalculator): Array<IntArray> {
        val results: Array<IntArray> = Array(iterations) { IntArray(2) }
        val copier = MatrixCopier()

        for (i in 0..iterations-1) {
            val trueIndex: Int = Random.nextInt(0, 10)

            val noisedNumber = noiser.noise(copier.copy(numbers[trueIndex]), noiseLevel)

            var minDistance = 9999999.0
            var closestIndex = 0

            for (index in numbers.indices) {
                val dis = distance.distance(noisedNumber, numbers[index])
                if (dis < minDistance) {
                    minDistance = dis
                    closestIndex = index
                }
            }

            results[i] = intArrayOf(trueIndex, closestIndex)

        }

        return results
    }

}