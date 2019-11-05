package ch.uzh.ifi.seal.smr.reconfigure

import java.io.File

val outputDirectory = "D:\\"
//val outputDirectory = "/home/user/stefan-masterthesis/"

fun main() {
    val folder = File("D:\\rq2\\pre\\log4j2_100_iterations_1_second")

    folder.walk().forEach {
        if (it.isFile) {
            val (project, commit, benchmark, params) = it.nameWithoutExtension.split(";")
            val key = CsvLineKey(project, commit, benchmark, params)
            val list = CsvLineParser(it).getList()

//            evalBenchmarkCOV(key, list)
//            evalBenchmarkCiPercentage(key, list)
            evalBenchmarkDivergence(key, list)
        }
    }
}