package neuralNetworks1

import knn1.Numbers
import knn1.TrainGenerator
import knn1.noise.GreyScaleEvenNoiser
import org.jetbrains.kotlinx.dataframe.io.toDataFrame

fun main() {
    val trainGenerator = TrainGenerator()
    val numbers = Numbers()
    val noiser = GreyScaleEvenNoiser()
    val train = trainGenerator.generate(100, true, noiser, 30)

    val test = trainGenerator.generate(300, true, noiser, 30)

    val classifier = EnsembleClassifier(10, 0.05, 1000)

    classifier.learn()

    val table: MutableList<MutableList<Int>> = MutableList(10) { MutableList(10) {0} }
    table.add(0, mutableListOf(0,1,2,3,4,5,6,7,8,9))
    var rights = 0.0
    for (i in test.indices) {
        table[trainTranslate(test).second[i] + 1][classifier.predict(trainTranslate(test).first[i])] += 1
        if (classifier.predict(trainTranslate(test).first[i]) == trainTranslate(test).second[i]) {rights+=1}

    }
    println("Ensemble accuracy ${rights/test.size}")
    println(table.toDataFrame())

    val classifier2 = Classifier(0.05, trainTranslate(train), 1000)

    classifier2.learn()

    val table2: MutableList<MutableList<Int>> = MutableList(10) { MutableList(10) {0} }
    table2.add(0, mutableListOf(0,1,2,3,4,5,6,7,8,9))
    var rights2 = 0.0
    for (i in test.indices) {
        table2[trainTranslate(test).second[i] + 1][classifier2.predict(trainTranslate(test).first[i])] += 1
        if (classifier2.predict(trainTranslate(test).first[i]) == trainTranslate(test).second[i]) {rights2+=1}

    }
    println("Non-ensemble accuracy ${rights2/test.size}")
    println(table2.toDataFrame())

}

fun trainTranslate(oldTrain: MutableList<Pair<Int, Array<IntArray>>>): Pair<MutableList<List<Int>>, MutableList<Int>> {

    val labels = MutableList<Int>(oldTrain.size){0}
    val numbers = MutableList<List<Int>>(oldTrain.size){ listOf(0) }

    for (i in oldTrain.indices) {
        val label = oldTrain[i].first

        val number = oldTrain[i].second.flatMap { it.toList() }

        labels.set(i, label)
        numbers.set(i, number)

    }

    val newTrain = Pair<MutableList<List<Int>>, MutableList<Int>>(numbers, labels)

    return newTrain
}