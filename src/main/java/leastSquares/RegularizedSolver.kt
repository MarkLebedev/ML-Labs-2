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

class RegularizedSolver {

    fun createIdentityMatrix(size: Int): Array<DoubleArray> {
        return Array(size) { row -> DoubleArray(size) { col -> if (row == col) 1.0 else 0.0 } }
    }

    fun addMatrices(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray> {
        // Check that matrices have the same dimensions
        require(matrix1.size == matrix2.size && matrix1[0].size == matrix2[0].size) {
            "Matrices must have the same dimensions for addition."
        }

        // Create a result matrix of the same size
        val result = Array(matrix1.size) { DoubleArray(matrix1[0].size) }

        // Add corresponding elements from both matrices
        for (i in matrix1.indices) {
            for (j in matrix1[i].indices) {
                result[i][j] = matrix1[i][j] + matrix2[i][j]
            }
        }

        return result
    }

    fun scaleMatrix(matrix: Array<DoubleArray>, lambda: Double): Array<DoubleArray> {
        val result = Array(matrix.size) { DoubleArray(matrix[0].size) }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                result[i][j] = matrix[i][j]*lambda
            }
        }

        return result
    }

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
        degree: Int,
        lambda: Double
    ): List<Double> {
        val A = mk.ndarray(x.map { xi -> DoubleArray(degree + 1) { pow -> xi.pow(pow) } }.toTypedArray())

        val Y = mk.ndarray(y.toDoubleArray(), y.size, 1)

        val ATA = A.transpose().dot(A)

        val identity = createIdentityMatrix(degree + 1)
        val regularizedATA = addMatrices(ATA.toArray(), scaleMatrix(identity, lambda))

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

    fun solve(train: MutableList<Pair<Double, Double>>, M: Int, lambda: Double): List<Double> {
        val x = MutableList<Double>(train.size) { 0.0 }
        val y = MutableList<Double>(train.size) { 0.0 }

        for (i in train.indices) {
            x.set(i, train[i].first)
            y.set(i, train[i].second)
        }

        val degree = M

        val coefficients = polynomialRegression(x, y, degree, lambda)
        println("Coefficients: $coefficients")

        val error = leastSquaredError(x, y, coefficients)
        println("Least Squared Error: $error")

        return coefficients
    }

    fun crossValidate(
        x: List<Double>,
        y: List<Double>,
        degree: Int,
        lambda: Double,
        k: Int
    ): Double {
        val n = x.size
        val foldSize = n / k
        val indices = x.indices.shuffled()

        val errors = mutableListOf<Double>()

        for (fold in 0 until k) {
            // Split into training and validation sets
            val validationIndices = indices.subList(fold * foldSize, (fold + 1) * foldSize)
            val trainingIndices = indices - validationIndices.toSet()

            val xTrain = trainingIndices.map { x[it] }
            val yTrain = trainingIndices.map { y[it] }
            val xValidation = validationIndices.map { x[it] }
            val yValidation = validationIndices.map { y[it] }

            // Train model with regularization
            val coefficients = polynomialRegression(xTrain, yTrain, degree, lambda)

            // Compute validation error
            val validationError = leastSquaredError(xValidation, yValidation, coefficients)
            errors.add(validationError)
        }

        // Return average validation error
        return errors.average()
    }

}