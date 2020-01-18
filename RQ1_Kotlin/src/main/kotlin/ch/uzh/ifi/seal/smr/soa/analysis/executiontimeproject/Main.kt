package ch.uzh.ifi.seal.smr.soa.analysis.executiontimeproject

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import java.io.File
import kotlin.math.round

fun main() {
    val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results\\ReactiveX#RxJava.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\jmh-jdk-microbenchmarks.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results\\apache#logging-log4j2.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\jmh-core-benchmarks.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results\\raphw#byte-buddy.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results\\JCTools#JCTools.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results\\protostuff#protostuff.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results\\jenetics#jenetics.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results\\SquidPony#SquidLib.csv")
    //val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results\\openzipkin#zipkin.csv")

    val results = CsvResultParser(resultFile).getList()

    // set jmh version if not present
    results.forEach { it.jmhVersion = JMHVersion(1, 20) }

    val totalTime = results.map { it.calcExecutionTime() * it.parameterizationCombinations }.sum()
    println("${round(totalTime / 3600 * 100) / 100}h")
}