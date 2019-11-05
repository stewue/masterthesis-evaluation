package ch.uzh.ifi.seal.smr.reconfigure

import java.io.File

val outputDirectory = "/home/user/stefan-masterthesis/"

fun main() {
    disableSystemErr()

    val folder = File("/home/user/stefan-masterthesis/100_iterations_1_second")

    folder.walk().forEach {
        if (it.isFile) {
            val (project, commit, benchmark, params) = it.nameWithoutExtension.split(";")
            val key = CsvLineKey(project, commit, benchmark, params)
            val list = CsvLineParser(it).getList()

            evalBenchmarkCOV(key, list)
            evalBenchmarkCi(key, list)
            evalBenchmarkDivergence(key, list)
        }
    }
}