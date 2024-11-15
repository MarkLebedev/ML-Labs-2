package leastSquares
import kotlin.math.pow
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.linalg.*
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.linalg.inv
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray.*
import org.jetbrains.kotlinx.multik.ndarray.*
import org.jetbrains.kotlinx.multik.ndarray.operations.mapIndexed
import org.jetbrains.kotlinx.multik.ndarray.operations.toArray

class Solver {

    fun invertMatrix(matrix: Array<DoubleArray>): Array<DoubleArray> {
        val n = matrix.size
        val augmented = Array(n) { row -> DoubleArray(2 * n) { col -> if (col < n) matrix[row][col] else if (col == n + row) 1.0 else 0.0 } }

        for (i in 0 until n) {
            val diag = augmented[i][i]
            for (j in 0 until 2 * n) {
                augmented[i][j] /= diag
            }

            for (k in 0 until n) {
                if (k != i) {
                    val factor = augmented[k][i]
                    for (j in 0 until 2 * n) {
                        augmented[k][j] -= factor * augmented[i][j]
                    }
                }
            }
        }

        return Array(n) { row -> DoubleArray(n) { col -> augmented[row][col + n] } }
    }

    fun polynomialRegression(
        x: List<Double>,
        y: List<Double>,
        degree: Int
    ): List<Double> {
        val A = mk.ndarray(x.map { xi -> DoubleArray(degree + 1) { pow -> xi.pow(pow) } }.toTypedArray())

        val Y = mk.ndarray(y.toDoubleArray(), y.size, 1)

        val ATA = A.transpose().dot(A)

        val ATY = A.transpose().dot(Y)

        val coefficients = invertMatrix(ATA.toArray()).toNDArray().dot(ATY)

        return coefficients.data.toList()
    }

    fun leastSquaredError(x: List<Double>, y: List<Double>, coefficients: List<Double>): Double {
        return x.zip(y).sumOf { (xi, yi) ->
            val predicted = coefficients.mapIndexed { i, coeff -> coeff * xi.pow(i) }.sum()
            (yi - predicted).pow(2)
        }
    }

    fun solve(train: MutableList<Pair<Double, Double>>, M: Int): List<Double> {
        val x = MutableList<Double>(train.size) { 0.0 }
        val y = MutableList<Double>(train.size) { 0.0 }

        for (i in train.indices) {
            x.set(i, train[i].first)
            y.set(i, train[i].second)
        }

        val degree = M

        val coefficients = polynomialRegression(x, y, degree)
        println("Coefficients: $coefficients")

        val error = leastSquaredError(x, y, coefficients)
        println("Least Squared Error: $error")

        return coefficients
    }

}