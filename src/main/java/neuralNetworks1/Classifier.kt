package neuralNetworks1

class Classifier(learningRate: Double, train: Pair<MutableList<List<Int>>, MutableList<Int>>, iterations: Int) {

    val train = train
    val iterations = iterations

    val neuron0 = Perceptron(15, learningRate)
    val neuron1 = Perceptron(15, learningRate)
    val neuron2 = Perceptron(15, learningRate)
    val neuron3 = Perceptron(15, learningRate)
    val neuron4 = Perceptron(15, learningRate)
    val neuron5 = Perceptron(15, learningRate)
    val neuron6 = Perceptron(15, learningRate)
    val neuron7 = Perceptron(15, learningRate)
    val neuron8 = Perceptron(15, learningRate)
    val neuron9 = Perceptron(15, learningRate)

    fun learn() {
        neuron0.learn(train, iterations, 0)
        neuron1.learn(train, iterations, 1)
        neuron2.learn(train, iterations, 2)
        neuron3.learn(train, iterations, 3)
        neuron4.learn(train, iterations, 4)
        neuron5.learn(train, iterations, 5)
        neuron6.learn(train, iterations, 6)
        neuron7.learn(train, iterations, 7)
        neuron8.learn(train, iterations, 8)
        neuron9.learn(train, iterations, 9)
    }

    fun predict(input: List<Int>): Int {
        val predictions = arrayOf(neuron0.predict(input),
            neuron1.predict(input),
            neuron2.predict(input),
            neuron3.predict(input),
            neuron4.predict(input),
            neuron5.predict(input),
            neuron6.predict(input),
            neuron7.predict(input),
            neuron8.predict(input),
            neuron9.predict(input),)

        val max = predictions.indices.maxBy{predictions[it]}

        return max
    }

}