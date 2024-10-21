package knn1

import knn1.distance.DistanceType
import knn1.noise.NoiseType

fun main() {

    val settings = Settings(true, 40, 100, DistanceType.EUCLIDIDAN_SQUARED, NoiseType.UNEVEN)
    val numbers = Numbers()
    val scaler = GreyScaler()

    if (settings.greyScale == true) {
        for (number in numbers.all_numbers.indices) {
            numbers.all_numbers[number] = scaler.BWToGreyscale(numbers.all_numbers[number])
        }
    }

    val calculator = IterationCalculator()

    val res = calculator.calculate(settings.iterations, settings.noiser, settings.noiseLevel, numbers.all_numbers, settings.distance)

    var correct = 0.0
    var wrong = 0.0
    for (i in res) {
        if (i[0] == i[1]) {correct++} else {wrong++}
    }

    println(settings.distance.toString())
    println(settings.noiser.toString())

    println(correct/(correct+wrong))
}