package knn1.distance

class IntToDoubleConverter {

    fun convert(number: Array<IntArray>): Array<DoubleArray> {
        val newNumber = Array(5) {DoubleArray(3) {0.0} }
        for (i in number.indices) {
            for (j in number[i].indices) {
                newNumber[i][j] = number[i][j].toDouble()
            }
        }

        return newNumber
    }

}