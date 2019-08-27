package ch.uzh.ifi.seal.smr.soa.csvmerger

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

fun main() {
    val versionFile = File("D:\\versions.csv")
    val benchmarkFile = File("D:\\benchmarks.csv")
    val javaFile = File("D:\\java.csv")
    val csv = File("D:\\RQ1_preprocessed_repo_list_additional_information.csv")
    val list = CsvResultParser(csv).getList()

    val versions = mutableMapOf<String, String?>()
    versionFile.forEachLine {
        val (p, v) = it.split(";")
        if (!v.isNullOrEmpty()) {
            versions[p] = v
        }
    }

    val benchmarks = mutableMapOf<String, Int?>()
    benchmarkFile.forEachLine {
        val (p, n) = it.split(";")
        benchmarks[p] = n.toIntOrNull()
    }

    val javaVersion = mutableMapOf<String, String?>()
    javaFile.forEachLine {
         val (p, v) = it.split(";")
         if (!v.isNullOrEmpty()) {
             javaVersion[p] = v
         }
     }

    list.forEach {
        val name = it.project
        val v = versions[name]
        val b = benchmarks[name]
        val j = javaVersion[name]

        if (v != null) {
            it.jmhVersion = v
        }

        if (b != null) {
            it.numberOFBenchmarks = b
        }

        if (j != null) {
            it.javaVersion = j
        }
    }

    OpenCSVWriter.write(csv.toPath(), list)
}