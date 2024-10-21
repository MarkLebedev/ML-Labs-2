package knn1

import kotlin.random.Random


class GreyScaler {
    val copier = MatrixCopier()

    fun BWToGreyscale(number: Array<IntArray>): Array<IntArray> {
        val newNumber: Array<IntArray> = copier.copy(number)

        for (row in number) {
            for (pixel in row.indices) {
                if (row[pixel] == 1) {
                    row.set(pixel, Random.nextInt(155, 256))
                } else {row.set(pixel, Random.nextInt(0, 101))}
            }
        }

        return newNumber
    }

}