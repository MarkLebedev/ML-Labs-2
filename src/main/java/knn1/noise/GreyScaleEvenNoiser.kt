package knn1.noise

import knn1.MatrixCopier
import kotlin.random.Random

class GreyScaleEvenNoiser: ParentNoiser() {
    val copier = MatrixCopier()

    override fun noise(number: Array<IntArray>, noiseLevel: Int): Array<IntArray> {
        val newNumber: Array<IntArray> = copier.copy(number)

        for (row in newNumber) {
            for (pixel in row.indices) {
                val randInt = Random.nextInt(0, 101)
                if (randInt < noiseLevel) {
                    row[pixel] = Random.nextInt(0, 256)
                }
            }
        }

        return newNumber
    }

}