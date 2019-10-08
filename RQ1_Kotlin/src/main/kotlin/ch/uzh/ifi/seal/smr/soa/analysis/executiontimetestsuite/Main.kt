package ch.uzh.ifi.seal.smr.soa.analysis.executiontimetestsuite

import ch.uzh.ifi.seal.smr.soa.analysis.jmh121
import ch.uzh.ifi.seal.smr.soa.utils.CsvResExecutionTimeParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

private val output = mutableListOf<ResExecutionTimeTestSuite>()

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()
    val executionTime = CsvResExecutionTimeParser(file).getList()

    val grouped = executionTime.groupBy { it.project }
    grouped.forEach { (project, list) ->
        val jmhVersion = list.first().jmhVersion
        var totalExecutionTime = 0.0
        list.forEach {
            totalExecutionTime += it.parameterizationCombinations * it.executionTime
        }
        val numberOfBenchmarks = list.size
        val parameterizationCombinations = list.map{ it.parameterizationCombinations }.sum()
        val defaultTimePerBenchmark = if(jmhVersion.compareTo(jmh121) == 1){
            500.0
        }else{
            400.0
        }
        val totalExecutionTimeDefault = defaultTimePerBenchmark * numberOfBenchmarks
        output.add(ResExecutionTimeTestSuite(project, jmhVersion, totalExecutionTime, totalExecutionTimeDefault, numberOfBenchmarks, parameterizationCombinations))
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResExecutionTimeTestSuite::class.java))
}