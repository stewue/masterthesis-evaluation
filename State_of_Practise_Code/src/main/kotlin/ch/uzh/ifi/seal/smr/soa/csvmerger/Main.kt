package ch.uzh.ifi.seal.smr.soa.csvmerger

import ch.uzh.ifi.seal.smr.soa.evaluation.OpenCSVWriter
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

val mapper = ObjectMapper()

fun main() {
    val versionFile = File("D:\\versions.csv")
    val benchmarkFile = File("D:\\benchmarks.csv")
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

    list.forEach {
        val name = it.project
        val v = versions[name]
        val b = benchmarks[name]

        if (v != null) {
            it.jmhVersion = v
        }

        if (b != null) {
            it.numberOFBenchmarks = b
        }
    }

    OpenCSVWriter.write(csv.toPath(), list)
}