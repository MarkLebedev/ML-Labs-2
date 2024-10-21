package knn1

import knn1.noise.*
import kotlin.random.Random

class TrainGenerator {

    fun generate(size: Int, greyScale: Boolean, noiser: ParentNoiser, noiselevel: Int): MutableList<Pair<Int, Array<IntArray>>> {
        val train = MutableList(size) { Pair<Int, Array<IntArray>>(0, arrayOf())}

        val scaler = GreyScaler()
        val numbers = Numbers()

        println("Generating a train sequence with greyscale: $greyScale, noiser: ${noiser.javaClass}, size: $size")

        for (i in 0 until size) {
            val index = Random.nextInt(numbers.all_numbers.size)
            val copier = MatrixCopier()

            var number = copier.copy(numbers.all_numbers[index])

            if (greyScale) {
                number = scaler.BWToGreyscale(number)
            }

            number = noiser.noise(number, noiselevel)

            train[i] = Pair(index, number)
        }

        return train
    }

}