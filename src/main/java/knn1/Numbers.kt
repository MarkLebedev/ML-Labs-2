package knn1

class Numbers {

    val zero: Array<IntArray> = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 1, 1)
    )

    val one: Array<IntArray> = arrayOf(
        intArrayOf(0, 1, 0),
        intArrayOf(0, 1, 0),
        intArrayOf(0, 1, 0),
        intArrayOf(0, 1, 0),
        intArrayOf(0, 1, 0)
    )

    val two: Array<IntArray> = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 0),
        intArrayOf(1, 1, 1)
    )

    val three: Array<IntArray> = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(1, 1, 1)
    )

    val four: Array<IntArray> = arrayOf(
        intArrayOf(1, 0, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(0, 0, 1)
    )

    val five: Array<IntArray> = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 0),
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(1, 1, 1)
    )

    val six: Array<IntArray> = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 0),
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 1, 1)
    )

    val seven: Array<IntArray> = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(0, 1, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(0, 0, 1)
    )

    val eight: Array<IntArray> = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 1, 1)
    )

    val nine: Array<IntArray> = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(1, 1, 1)
    )

    val all_numbers: Array<Array<IntArray>> = arrayOf(
        zero, one, two, three, four, five, six, seven, eight, nine
    )

    fun numberPrint(number: Array<IntArray>) {
        println("___")
        for (i in number) {
            println(i.joinToString())
        }
    }
}