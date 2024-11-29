package knn1

import knn1.distance.DistanceType
import knn1.noise.NoiseType
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.io.*
import org.jetbrains.kotlinx.dataframe.api.*

fun main() {

    val settings = Settings(false, 30, 1000, DistanceType.EUCLIDIAN, NoiseType.UNEVEN)
    val numbers = Numbers().all_numbers

    val generator = TrainGenerator()

    val train = generator.generate(100, settings.greyScale, settings.noiser, settings.noiseLevel)

    val iterationCalculator = IterationCalculator()

    val results = iterationCalculator.calculate(settings.greyScale, settings.iterations, settings.noiser,
        settings.noiseLevel, numbers, settings.distance, train, 5)

    var rights = 0.0
    var wrongs = 0.0

    val table: MutableList<MutableList<Int>> = MutableList(10) { MutableList(10) {0} }
    table.add(0, mutableListOf(0,1,2,3,4,5,6,7,8,9))

    for (i in results) {
        table[i[0]+1][i[1]] += 1

        if (i[0] == i[1]) {
            rights++
        } else {wrongs++}
    }

    println(table.toDataFrame())

    /*
    val n = Numbers()
    println(train[0].first)
    n.numberPrint(train[0].second)
    */

    print(rights/(rights+wrongs))

}