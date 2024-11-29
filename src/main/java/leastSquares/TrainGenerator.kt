package leastSquares

import kotlin.math.*
import kotlin.random.Random


class TrainGenerator {

    fun generateNormal(mean: Double, stdDev: Double): Double {
        val u1 = Math.random() // uniform distribution in (0, 1]
        val u2 = Math.random() // uniform distribution in (0, 1]
        val z0 = sqrt(-2.0 * ln(u1)) * cos(2.0 * PI * u2) // Standard normal distribution
        return z0 * stdDev + mean
    }

    fun generate(function: Function, noise: NoiseType): MutableList<Pair<Double, Double>> {
        val train = mutableListOf<Pair<Double, Double>>()

        for (i in 0..20) {
            val x = Random.nextDouble(0.0, 2*Math.PI)
            val y = when(function) {
                Function.COSX -> Math.cos(x)
                Function.POLYNOM -> 5*x*x*x+x*x+5
                Function.XSINX -> Math.sin(x)*x
            }
            val noisedy = when(noise) {
                NoiseType.EVEN -> Random.nextDouble(y-0.10*abs(y), y+0.10*abs(y))
                NoiseType.NORMAL -> generateNormal(y, 0.05*y)
            }

            train.add(Pair(x, noisedy))
        }

        return train
    }

}

