package ch.uzh.ifi.seal.smr.soa.analysis.jmh121update

import ch.uzh.ifi.seal.smr.soa.analysis.percentageString
import ch.uzh.ifi.seal.smr.soa.utils.CsvResJmh121Update
import ch.uzh.ifi.seal.smr.soa.utils.median
import java.io.File
import kotlin.reflect.KMutableProperty1

fun main (){
    val fileHistory = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmh121update.csv")

    val items = CsvResJmh121Update(fileHistory).getList()
    val differentConfig = items.filter { !it.sameConfig }

    println("Different config: ${differentConfig.size} (${percentageString(differentConfig.size, items.size)})")
    println("")

    val differentWarmupIterations = items.filter { it.warmupIterationsOldDefault == true }
    val differentWarmupTime = items.filter { it.warmupTimeOldDefault == true }
    val differentMeasurementIterations = items.filter { it.measurementIterationsOldDefault == true }
    val differentMeasurementTime = items.filter { it.measurementTimeOldDefault == true }
    val differentForks = items.filter { it.forksOldDefault == true }

    println("WarmupIterations changed to old default value: ${differentWarmupIterations.size} (${percentageString(differentWarmupIterations.size, differentConfig.size)})")
    println("WarmupTime changed to old default value: ${differentWarmupTime.size} (${percentageString(differentWarmupTime.size, differentConfig.size)})")
    println("MeasurementIterations changed to old default value: ${differentMeasurementIterations.size} (${percentageString(differentMeasurementIterations.size, differentConfig.size)})")
    println("MeasurementTime changed to old default value: ${differentMeasurementTime.size} (${percentageString(differentMeasurementTime.size, differentConfig.size)})")
    println("Forks changed to old default value: ${differentForks.size} (${percentageString(differentForks.size, differentConfig.size)})")
    println("")

    val iterationsOneChanged = items.filter { it.iterationsOneChanged == true }
    val timeOneChanged = items.filter { it.timeOneChanged == true }
    val bothChanged = items.filter { it.iterationsOneChanged == true && it.timeOneChanged == true }

    println("iterations changed: ${iterationsOneChanged.size} (${percentageString(iterationsOneChanged.size, differentConfig.size)})")
    println("time changed: ${timeOneChanged.size} (${percentageString(timeOneChanged.size, differentConfig.size)})")
    println("both changed: ${bothChanged.size} (${percentageString(bothChanged.size, differentConfig.size)})")
    println("")

    helper("WarmupIterations", ResJmh121Update::warmupIterationsDifference, items)
    helper("WarmupTime", ResJmh121Update::warmupTimeDifference, items)
    helper("MeasurementIterations", ResJmh121Update::measurementIterationsDifference, items)
    helper("MeasurementTime", ResJmh121Update::measurementTimeDifference, items)
    helper("Forks", ResJmh121Update::forksDifference, items)
}

private fun helper(title: String, property: KMutableProperty1<ResJmh121Update, out Number?>, items: Collection<ResJmh121Update>){
    val difference = items.map {property.get(it)?.toDouble() }.filterNotNull()
    val larger = difference.filter{ it > 0 }.count()
    val smaller = difference.filter{ it < 0 }.count()
    val same = difference.filter{ it == 0.0 }.count()
    print("$title: median=${difference.median()}, ")
    print("total=${difference.size}, ")
    print("larger=$larger (${percentageString(larger, difference.size)}), ")
    print("smaller=$smaller (${percentageString(smaller, difference.size)}), ")
    println("same=$same (${percentageString(same, difference.size)}), ")
}