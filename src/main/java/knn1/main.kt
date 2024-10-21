package knn1

import knn1.distance.DistanceType
import knn1.noise.NoiseType

fun main() {

    val settings = Settings(true, 20, 1000, DistanceType.EUCLIDIDAN_SQUARED, NoiseType.EVEN)
    val numbers = Numbers().all_numbers

    val generator = TrainGenerator()

    val train = generator.generate(100, settings.greyScale, settings.noiser, noiselevel = settings.noiseLevel)

    val iterationCalculator = IterationCalculator()

    val results = iterationCalculator.calculate(settings.greyScale, settings.iterations, settings.noiser, settings.noiseLevel,
        numbers, settings.distance, train, 5)

    var rights = 0.0
    var wrongs = 0.0

    for (i in results) {
        if (i[0] == i[1]) {
            rights++
        } else {wrongs++}
    }

    print(rights/(rights+wrongs))

}