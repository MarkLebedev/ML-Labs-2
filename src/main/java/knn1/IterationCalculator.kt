package knn1

import knn1.distance.ParentDistanceCalculator
import knn1.noise.ParentNoiser
import kotlin.random.Random

class IterationCalculator {

    fun calculate(greyScale: Boolean, iterations: Int, noiser: ParentNoiser, noiseLevel: Int, numbers: Array<Array<IntArray>>,
                  distance: ParentDistanceCalculator, train: MutableList<Pair<Int, Array<IntArray>>>, k: Int): Array<IntArray> {
        val results: Array<IntArray> = Array(iterations) { IntArray(2) }
        val copier = MatrixCopier()
        val scaler = GreyScaler()

        for (i in 0..iterations-1) {
            val trueIndex = Random.nextInt(numbers.size)

            var number = copier.copy(numbers[trueIndex])
            if (greyScale) {
                number = scaler.BWToGreyscale(number)
            }
            val noisedNumber = noiser.noise(number, noiseLevel)

            val kNeighbours = kNearestDistances(distance, noisedNumber, train, k)

            val predictedIndex = findMostCommonNeighbour(kNeighbours)

            results[i] = intArrayOf(trueIndex, predictedIndex)

        }

        return results
    }

    fun kNearestDistances(distanceCalculator: ParentDistanceCalculator, number: Array<IntArray>,
                          train: MutableList<Pair<Int, Array<IntArray>>>, k: Int): Array<Pair<Int, Double>> {
        val distances = Array<Pair<Int, Double>>(k) { Pair(0, 99999999.0) }

        for (i in train) {
            val distance = distanceCalculator.distance(number, i.second)

            if (distance < distances[findMaxDistanceInK(distances)].second) {
                distances[findMaxDistanceInK(distances)] = Pair(i.first, distance)
            }
        }

        return distances
    }

    fun findMaxDistanceInK(distances: Array<Pair<Int, Double>>): Int {
        var indexOfMaxDistance = -1
        var maxDistance = 0.0

        for (i in distances.indices) {
            if (distances[i].second >= maxDistance) {
                maxDistance = distances[i].second
                indexOfMaxDistance = i
            }
        }

        return indexOfMaxDistance
    }

    fun findMostCommonNeighbour(distances: Array<Pair<Int, Double>>): Int {
        val neighbours = MutableList<Int>(10) {0}
        for (i in distances) {
            neighbours[i.first]++
        }
        var bestNeighbourIndex = 0
        var bestNeighbourCount = 0
        for (i in neighbours.indices) {
            if (neighbours[i] > bestNeighbourCount) {
                bestNeighbourIndex = i
                bestNeighbourCount = neighbours[i]
            }
        }

        return bestNeighbourIndex
    }

}