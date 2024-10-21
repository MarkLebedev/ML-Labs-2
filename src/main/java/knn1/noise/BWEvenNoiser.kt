package knn1.noise

import knn1.MatrixCopier
import kotlin.random.Random

class BWEvenNoiser: ParentNoiser() {
    val copier = MatrixCopier()

    override fun noise(number: Array<IntArray>, noiseLevel: Int): Array<IntArray> {
        val newNumber: Array<IntArray> = copier.copy(number)

        for (row in newNumber) {
            for (pixel in row.indices) {
                val randInt = Random.nextInt(0, 100)
                if (randInt < noiseLevel) {
                    row[pixel] = Random.nextInt(0, 2)
                }
            }
        }

        return newNumber
    }

}