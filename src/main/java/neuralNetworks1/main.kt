package neuralNetworks1

import knn1.Numbers
import knn1.TrainGenerator
import knn1.noise.GreyScaleEvenNoiser

fun main() {
    val trainGenerator = TrainGenerator()
    val numbers = Numbers()
    val noiser = GreyScaleEvenNoiser()
    var train = trainGenerator.generate(100, true, noiser, 30)

    val test = trainGenerator.generate(100, true, noiser, 30)

    val classifier = EnsembleClassifier(10, 0.05, 1000)

    classifier.learn()

    var rights = 0.0
    for (i in test.indices) {
        if (classifier.predict(trainTranslate(test).first[i]) == trainTranslate(test).second[i]) {rights+=1}

    }
    println("Ensemble accuracy ${rights/100}")

    val classifier2 = Classifier(0.05, trainTranslate(train), 1000)

    classifier2.learn()

    var rights2 = 0.0
    for (i in test.indices) {
        if (classifier2.predict(trainTranslate(test).first[i]) == trainTranslate(test).second[i]) {rights2+=1}

    }
    println("Non-ensemble accuracy ${rights2/100}")

}

fun trainTranslate(oldTrain: MutableList<Pair<Int, Array<IntArray>>>): Pair<MutableList<List<Int>>, MutableList<Int>> {

    val labels = MutableList<Int>(oldTrain.size){0}
    val numbers = MutableList<List<Int>>(oldTrain.size){ listOf(0) }

    for (i in oldTrain.indices) {
        val label = oldTrain[i].first

        val number = oldTrain[i].second.flatMap { it.toList() }

        for (j in oldTrain[i].second) {

        }

        labels.set(i, label)
        numbers.set(i, number)

    }

    val newTrain = Pair<MutableList<List<Int>>, MutableList<Int>>(numbers, labels)

    return newTrain
}