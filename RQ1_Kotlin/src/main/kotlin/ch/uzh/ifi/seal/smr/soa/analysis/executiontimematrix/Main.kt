package ch.uzh.ifi.seal.smr.soa.analysis.executiontimematrix

import ch.uzh.ifi.seal.smr.soa.analysis.executiontime.ResExecutionTime
import ch.uzh.ifi.seal.smr.soa.analysis.percentageString
import ch.uzh.ifi.seal.smr.soa.utils.CsvResExecutionTimeParser
import ch.uzh.ifi.seal.smr.soa.utils.median
import java.io.File

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv")
    val all = CsvResExecutionTimeParser(file).getList()
    val somethingChanged = all.filter { it.onlySingleShot == false && it.onlyModeChanged == false }

    println("something changed: ${somethingChanged.size} (${percentageString(somethingChanged.size, all.size)}%)")

    matrix(somethingChanged)
    println("")
}

private fun matrix(all: Collection<ResExecutionTime>) {
    val elwl = all.filter { it.executionTimePercentage < 1 && it.warmupTimePercentage < 1 }.size
    val eswl = all.filter { it.executionTimePercentage == 1.0 && it.warmupTimePercentage < 1 }.size
    val ehwl = all.filter { it.executionTimePercentage > 1 && it.warmupTimePercentage < 1 }.size
    val elws = all.filter { it.executionTimePercentage < 1 && it.warmupTimePercentage == 1.0 }.size
    val esws = all.filter { it.executionTimePercentage == 1.0 && it.warmupTimePercentage == 1.0 }.size
    val ehws = all.filter { it.executionTimePercentage > 1 && it.warmupTimePercentage == 1.0 }.size
    val elwh = all.filter { it.executionTimePercentage < 1 && it.warmupTimePercentage > 1 }.size
    val eswh = all.filter { it.executionTimePercentage == 1.0 && it.warmupTimePercentage > 1 }.size
    val ehwh = all.filter { it.executionTimePercentage > 1 && it.warmupTimePercentage > 1 }.size

    print("$elwh ${percentageString(elwh, all.size)}")
    print("\t | \t")
    print("$eswh ${percentageString(eswh, all.size)}")
    print("\t | \t")
    println("$ehwh ${percentageString(ehwh, all.size)}")

    print("$elws ${percentageString(elws, all.size)}")
    print("\t | \t")
    print("$esws ${percentageString(esws, all.size)}")
    print("\t | \t")
    println("$ehws ${percentageString(ehws, all.size)}")

    print("$elwl ${percentageString(elwl, all.size)}")
    print("\t | \t")
    print("$eswl ${percentageString(eswl, all.size)}")
    print("\t | \t")
    println("$ehwl ${percentageString(ehwl, all.size)}")

    println("x=executionTime, y=warmupTime")
    println("execution time median=${all.map { it.executionTimePercentage }.median()}, avg=${all.map { it.executionTimePercentage }.average()}")
    println("warmup time median=${all.map { it.warmupTimePercentage }.median()}, avg=${all.map { it.warmupTimePercentage }.average()}")
    println("measurement time median=${all.map { it.measurementTimePercentage }.median()}, avg=${all.map { it.measurementTimePercentage }.average()}")

}