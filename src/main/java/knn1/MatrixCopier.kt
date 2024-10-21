package knn1

class MatrixCopier {

    fun copy(matrix: Array<IntArray>): Array<IntArray> {
        val newMatrix = Array(matrix.size) { IntArray(matrix[0].size) }

        for (row in matrix.indices) {
            for (col in matrix[0].indices) {
                newMatrix[row][col] = matrix[row][col]
            }
        }

        return newMatrix
    }

}