package neuralNetworks1

import knn1.TrainGenerator
import knn1.noise.GreyScaleEvenNoiser

class EnsembleClassifier(size: Int, learningRate: Double, iterations: Int) {

    val trainGenerator = TrainGenerator()
    val noiser = GreyScaleEvenNoiser()

    val trains = List(size){ trainTranslate(TrainGenerator().generate(100, true, noiser, 30)) }

    val ensemble = MutableList<Classifier>(size){Classifier(learningRate, trains[it], iterations)}

    fun learn() {
        for (classifier in ensemble) {
            classifier.learn()
        }
    }

    fun predict(input: List<Int>): Int {
        val predictions = MutableList(ensemble.size){0}

        for (i in ensemble.indices) {
            predictions.set(i, ensemble[i].predict(input))
        }

        val numbers = listOf(0,1,2,3,4,5,6,7,8,9)
        val votes = mapOf(0 to numbers.count { it == 0 },
            1 to predictions.count { it == 1 },
            2 to predictions.count { it == 2 },
            3 to predictions.count { it == 3 },
            4 to predictions.count { it == 4 },
            5 to predictions.count { it == 5 },
            6 to predictions.count { it == 6 },
            7 to predictions.count { it == 7 },
            8 to predictions.count { it == 8 },
            9 to predictions.count { it == 9 })


        return votes.maxBy { it.value }.key
    }

}