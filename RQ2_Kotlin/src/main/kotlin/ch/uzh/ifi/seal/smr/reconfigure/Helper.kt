package ch.uzh.ifi.seal.smr.reconfigure

fun getMax(list: List<Double>): Double {
    var max = list.first()

    list.forEach {
        if (it > max) {
            max = it
        }
    }

    return max
}

fun getMin(list: List<Double>): Double {
    var min = list.first()

    list.forEach {
        if (it < min) {
            min = it
        }
    }

    return min
}

fun List<Double>.median() = sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }