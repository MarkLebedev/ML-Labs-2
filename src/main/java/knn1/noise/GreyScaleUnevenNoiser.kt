package knn1.noise

import knn1.MatrixCopier
import kotlin.random.Random

class GreyScaleUnevenNoiser: ParentNoiser() {
    val copier = MatrixCopier()

    override fun noise(number: Array<IntArray>, noiseLevel: Int): Array<IntArray> {
        val newNumber: Array<IntArray> = copier.copy(number)

        for (row in newNumber.indices) {
            val randInt = Random.nextInt(0, 100)
            if (randInt < noiseLevel) {
                newNumber[row] = IntArray(3) {Random.nextInt(0, 101)}
            }
        }

        return newNumber
    }

}