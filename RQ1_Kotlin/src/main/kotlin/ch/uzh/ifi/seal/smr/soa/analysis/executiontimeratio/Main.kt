package ch.uzh.ifi.seal.smr.soa.analysis.executiontimeratio

import ch.uzh.ifi.seal.smr.soa.analysis.executiontime.ResExecutionTime
import ch.uzh.ifi.seal.smr.soa.analysis.percentageString
import ch.uzh.ifi.seal.smr.soa.utils.CsvResExecutionTimeParser
import ch.uzh.ifi.seal.smr.soa.utils.Group
import java.io.File

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv")
    val all = CsvResExecutionTimeParser(file).getList().filter { !it.onlySingleShot && !it.onlyModeChanged }
    doGroup(all, Group.PROFESSIONAL_MANY)
    doGroup(all, Group.PROFESSIONAL_FEW)
    doGroup(all, Group.NOT_PROFESSIONAL_MANY)
    doGroup(all, Group.NOT_PROFESSIONAL_FEW)

    println("ALL")
    matrix(all)
}

private fun doGroup(all: Collection<ResExecutionTime>, group: Group) {
    println("~~~$group~~~")
    matrix(all.filter { it.group == group })
    println("")
}

private fun matrix(all: Collection<ResExecutionTime>) {
    val h = all.filter { it.measurementWarmupRatio > 1 }
    val e = all.filter { it.measurementWarmupRatio == 1.0 }
    val s = all.filter { it.measurementWarmupRatio < 1 }

    println("execution time is proportionally larger: ${h.size} (${percentageString(h.size, all.size)})")
    println("same ratio: ${e.size} (${percentageString(e.size, all.size)})")
    println("warmup time is proportionally larger: ${s.size} (${percentageString(s.size, all.size)})")
}