package neuralNetworks1

class Perceptron(
    val inputs: Int,
    val learningRate: Double
) {

    val weights = MutableList(inputs){0.0}
    var bias = 0.0

    fun predict(input: List<Int>): Double {
        var sum = bias
        for (i in weights.indices) {
            sum += weights[i]*input[i]
        }

        return sum
    }

    fun activation(input: Double): Int {
        if (input >= 0) {
            return 1
        } else {return 0}
    }

    fun learn(train: Pair<MutableList<List<Int>>, MutableList<Int>>, iterations: Int, target: Int) {

        val numbers = train.first
        val labels = train.second
        val labelsBinary = MutableList(labels.size){0}
        for (i in labels.indices) {
            if (labels[i] == target) {
                labelsBinary.set(i, 1)
            }
        }

        for (iteration in 0..iterations) {
            val index = iteration % numbers.size

            val error = labelsBinary[index] - activation(predict(numbers[index]))

            if ((activation(predict(numbers[index]))) != labelsBinary[index]) {
                for (j in weights.indices) {
                    weights[j] += error*learningRate*numbers[index][j]
                }

                bias += error*learningRate
            }
        }

    }

}