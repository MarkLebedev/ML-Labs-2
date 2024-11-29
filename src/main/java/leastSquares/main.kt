package leastSquares

import org.jetbrains.kotlinx.dataframe.*
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.line
import org.jetbrains.kotlinx.kandy.letsplot.layers.points
import kotlin.math.PI
import kotlin.math.pow
import kotlin.random.Random

val generator = TrainGenerator()
val solver = RegularizedSolver()

fun predictY(x: List<Double>, coefficients: List<Double>): List<Double> {
    return x.map { xi ->
        coefficients.mapIndexed { i, coeff -> coeff * xi.pow(i.toDouble()) }.sum()
    }
}

fun main() {

    val function = Function.XSINX
    val noise = NoiseType.NORMAL

    val train = generator.generate(function, noise)
    val x = MutableList<Double>(train.size) { 0.0 }
    val y = MutableList<Double>(train.size) { 0.0 }

    for (i in train.indices) {
        x.set(i, train[i].first)
        y.set(i, train[i].second)
    }

    val df = dataFrameOf(
        "x" to x,
        "y" to y
    )

    val unx = List(1000) { Random.nextDouble(0.0, 6.0)}

    for (M in 1..30) {
        val coeffs = solver.solve(train, M, 0.2)

        df.plot {
            points {
                x(x)
                y(y)
            }
            line {
                x(unx)
                y(predictY(unx, coeffs))
            }
        }.save("${function.toString()}${noise.toString()}$M.png")
    }


    val lambdas = listOf(0.8, 0.6, 0.4, 0.2, 0.0)
    var minError = 999999.0
    var bestLambda = 0.0
    var bestM = 0

    for (M in 1..15) {
        for (lambda in lambdas) {
            val error = solver.crossValidate(x, y, M, lambda, 10)
            if (error <= minError) {
                bestM = M
                bestLambda = lambda
                minError = error
            }
        }
    }

    val coeffs = solver.solve(train, bestM, bestLambda)

    df.plot {
        points {
            x(x)
            y(y)
        }
        line {
            x(unx)
            y(predictY(unx, coeffs))
        }
        layout {
            caption = "On $function function and $noise noise the best M is $bestM, best Lambda is $bestLambda."
        }
    }.save("bestAfterCrossValidation.png")

}