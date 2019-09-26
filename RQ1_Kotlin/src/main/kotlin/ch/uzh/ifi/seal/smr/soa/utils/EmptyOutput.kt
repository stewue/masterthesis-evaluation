package ch.uzh.ifi.seal.smr.soa.utils

import java.io.File

fun main() {
    val outputFile = File("D:\\mp\\out.csv").toPath()
    val output = mutableListOf<Result>()
    val res = Result()
    res.project = ""
    res.className = ""
    res.benchmarkName = ""
    res.methodHash = ""
    output.add(res)

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(Result::class.java))
}