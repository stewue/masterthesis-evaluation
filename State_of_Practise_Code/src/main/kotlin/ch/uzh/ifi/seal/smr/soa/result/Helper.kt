package ch.uzh.ifi.seal.smr.soa.result

import kotlin.math.round

fun percentageString(above: Int, below: Int): String {
    return "${percentage(above, below)}%"
}

fun percentage(above: Int, below: Int): Double {
    return round(10000.0 * above / below) / 100
}