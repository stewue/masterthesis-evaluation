package ch.uzh.ifi.seal.smr.reconfigure.D_variability_single

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineKey
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineParser
import java.io.File

val outputDirectory = "D:\\"
//val outputDirectory = "/home/user/stefan-masterthesis/"

fun main() {
    val folder = File("D:\\rq2\\results\\csv-jenetics")

//    csvheaderCov()
    csvheaderCi()
//    csvheaderDivergence()

    folder.walk().forEach {
        if (it.isFile) {
            val (project, benchmark, params) = it.nameWithoutExtension.split("#")
            val key = CsvLineKey(project, "", benchmark, params)
            val list = CsvLineParser(it).getList()

//            evalBenchmarkCOV(key, list)
//            evalBenchmarkCiPercentage(key, list)
            evalBenchmarkDivergence(key, list)
        }
    }
}