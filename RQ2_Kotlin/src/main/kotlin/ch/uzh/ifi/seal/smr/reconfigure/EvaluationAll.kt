package ch.uzh.ifi.seal.smr.reconfigure

import java.io.File

fun main() {
    disableSystemErr()

    val folder = File("/home/user/stefan-masterthesis/100_iterations_1_second")

    folder.walk().forEach {
        if (it.isFile) {
            evalBenchmarkCOV(it)
            evalBenchmarkCi(it)
            evalBenchmarkDivergence(it)
        }
    }
}