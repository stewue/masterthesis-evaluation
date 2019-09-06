package ch.uzh.ifi.seal.smr.soa.result

import kotlin.math.round

fun percentage(above: Int, below: Int): String {
    val percentage = round(10000.0 * above / below) / 100
    return "$percentage%"
}