package ch.uzh.ifi.seal.smr.soa.analysis.jmh121update

import ch.uzh.ifi.seal.bencher.execution.defaultExecConfig
import ch.uzh.ifi.seal.smr.soa.analysis.jmh120
import ch.uzh.ifi.seal.smr.soa.analysis.jmh121
import ch.uzh.ifi.seal.smr.soa.analysis.percentageString
import ch.uzh.ifi.seal.smr.soa.utils.CsvResJmh121Update
import java.io.File
import kotlin.reflect.KMutableProperty1

private val groupValues: MutableMap<Group, MutableMap<String, Int>> = Group.values().map { it to mutableMapOf<String, Int>() }.toMap().toMutableMap()

fun main() {
    val fileHistory = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmh121update.csv")

    val all = CsvResJmh121Update(fileHistory).getList().toList()
    val differentConfig = all.filter { !it.sameConfig }

    println("Different config: ${differentConfig.size} (${percentageString(differentConfig.size, all.size)})")
    println("")

    println("& warmupIterations & warmupTime & measurementIterations & measurementTime & forks")
    generateOutput("warmupIterations", all, ResJmh121Update::warmupIterationsPre, ResJmh121Update::warmupIterationsPost, defaultExecConfig(jmh120).warmupIterations, defaultExecConfig(jmh121).warmupIterations)
    generateOutput("warmupTime", all, ResJmh121Update::warmupTimePre, ResJmh121Update::warmupTimePost, 1.0, 10.0)
    generateOutput("measurementIterations", all, ResJmh121Update::measurementIterationsPre, ResJmh121Update::measurementIterationsPost, defaultExecConfig(jmh120).measurementIterations, defaultExecConfig(jmh121).measurementIterations)
    generateOutput("measurementTime", all, ResJmh121Update::measurementTimePre, ResJmh121Update::measurementTimePost, 1.0, 10.0)
    generateOutput("forks", all, ResJmh121Update::forksPre, ResJmh121Update::forksPost, defaultExecConfig(jmh120).forks, defaultExecConfig(jmh121).forks)

    printOutput()
}

private fun getGroup(oldValue: Number?, newValue: Number?, oldDefaultValue: Number, newDefaultValue: Number): Triple<Group, Number, Number> {
    val oldValueWithDefault = oldValue ?: oldDefaultValue
    val newValueWithDefault = newValue ?: newDefaultValue

    val first = if (newValueWithDefault == oldDefaultValue) {
        if (oldValue == newValue) {
            Group.OLD_DEFAULT_ALREADY_SET_BEFORE
        } else {
            Group.TO_OLD_DEFAULT
        }
    } else if (oldValueWithDefault == newDefaultValue && newValueWithDefault == newDefaultValue) {
        if (newValue == null) {
            Group.ALREADY_NEW_DEFAULT_REMOVED_ANNOTATION
        } else {
            Group.ALREADY_NEW_DEFAULT
        }
    } else if (oldValueWithDefault == oldDefaultValue && newValueWithDefault == newDefaultValue) {
        if (oldValue == null && newValue == null) {
            Group.DEFAULT_DEFAULT
        } else {
            Group.DEFAULT_DEFAULT_MANUAL
        }
    } else if (oldValueWithDefault != oldDefaultValue && newValueWithDefault != newDefaultValue && oldValueWithDefault != newValueWithDefault) {
        Group.RANDOM_TO_RANDOM
    } else if (oldValueWithDefault == oldDefaultValue && newValueWithDefault != newDefaultValue) {
        Group.DEFAULT_TO_RANDOM
    } else if (oldValueWithDefault != oldDefaultValue && newValueWithDefault == newDefaultValue) {
        Group.RANDOM_TO_DEFAULT
    } else {
        Group.SAME_RANDOM
    }

    return Triple(first, oldValueWithDefault, newValueWithDefault)
}

private fun generateOutput(title: String, all: List<ResJmh121Update>, propertyOld: KMutableProperty1<ResJmh121Update, out Number?>, propertyNew: KMutableProperty1<ResJmh121Update, out Number?>, oldDefaultValue: Number, newDefaultValue: Number) {
    val grouped = all.map { getGroup(propertyOld.get(it), propertyNew.get(it), oldDefaultValue, newDefaultValue) }.map { it.first }.groupingBy { it }.eachCount().toMutableMap()

    val allCount = grouped.map { it.value }.sum()

    Group.values().forEach { group ->
        val count = grouped[group] ?: 0
        groupValues.getValue(group)[title] = count
    }
}

private fun printOutput() {
    println("\\textbf{active change}" + getBoldLine(listOf(Group.TO_OLD_DEFAULT, Group.RANDOM_TO_RANDOM, Group.DEFAULT_TO_RANDOM, Group.RANDOM_TO_DEFAULT, Group.DEFAULT_DEFAULT_MANUAL, Group.ALREADY_NEW_DEFAULT_REMOVED_ANNOTATION)) + " \\\\")
    println("n\\textsubscript{\\phantom{x}} \$\\rightarrow\$ d\\textsubscript{o}" + getLine(Group.TO_OLD_DEFAULT) + " \\\\")
    println("u\\textsubscript{1} \$\\rightarrow\$ u\\textsubscript{2}" + getLine(Group.RANDOM_TO_RANDOM) + " \\\\")
    println("d\\textsubscript{o} \$\\rightarrow\$ u\\textsubscript{2}" + getLine(Group.DEFAULT_TO_RANDOM) + " \\\\")
    println("u\\textsubscript{1} \$\\rightarrow\$ d\\textsubscript{n}" + getLine(Group.RANDOM_TO_DEFAULT) + " \\\\")
    println("d\\textsubscript{o} \$\\rightarrow\$ d\\textsubscript{n}" + getLine(Group.DEFAULT_DEFAULT_MANUAL) + " \\\\")
    println("d\\textsubscript{n} \$\\rightarrow\$ n" + getLine(Group.ALREADY_NEW_DEFAULT_REMOVED_ANNOTATION) + " \\\\")
    println("\\hline")
    println("\\textbf{passive change}" + getBoldLine(listOf(Group.DEFAULT_DEFAULT)) + " \\\\")
    println("n\\textsubscript{\\phantom{x}} \$\\rightarrow\$ n" + getLine(Group.DEFAULT_DEFAULT) + " \\\\")
    println("\\hline")
    println("\\textbf{no change}" + getBoldLine(listOf(Group.OLD_DEFAULT_ALREADY_SET_BEFORE, Group.ALREADY_NEW_DEFAULT, Group.SAME_RANDOM)) + " \\\\")
    println("d\\textsubscript{o} \$\\rightarrow\$ d\\textsubscript{o}" + getLine(Group.OLD_DEFAULT_ALREADY_SET_BEFORE) + " \\\\")
    println("d\\textsubscript{n} \$\\rightarrow\$ d\\textsubscript{n}" + getLine(Group.ALREADY_NEW_DEFAULT) + " \\\\")
    println("u\\textsubscript{1} \$\\rightarrow$ u\\textsubscript{1}" + getLine(Group.SAME_RANDOM) + " \\\\")
}

private fun getLine(group: Group): String {
    val map = groupValues.getValue(group)

    var ret = getCellNormal(map, "warmupIterations")
    ret += getCellNormal(map, "warmupTime")
    ret += getCellNormal(map, "measurementIterations")
    ret += getCellNormal(map, "measurementTime")
    ret += getCellNormal(map, "forks")

    return ret
}

private fun getCellNormal(map: Map<String, Int>, column: String): String {
    val count = map.getValue(column)
    val countAll = groupValues.map { it.value.getValue(column) }.sum()
    return getCell(count, countAll)
}

private fun getBoldLine(groups: List<Group>): String {
    var ret = getBoldCell(groups, "warmupIterations")
    ret += getBoldCell(groups, "warmupTime")
    ret += getBoldCell(groups, "measurementIterations")
    ret += getBoldCell(groups, "measurementTime")
    ret += getBoldCell(groups, "forks")

    return ret
}

private fun getBoldCell(groups: List<Group>, column: String): String {
    val count = groupValues.filter { groups.contains(it.key) }.map { it.value.getValue(column) }.sum()
    val countAll = groupValues.map { it.value.getValue(column) }.sum()

    return getCell(count, countAll, true)
}

private fun getCell(count: Int, allCount: Int, bold: Boolean = false): String {
    var ret = " & "

    if (bold) {
        ret += "\\textbf{"
    }

    ret += "$count"

    if (bold) {
        ret += "} & \\textbf{"
    }else{
        ret += " & "
    }

    ret += "(${percentageString(count, allCount)})".replace("%", "\\%")

    if (bold) {
        ret += "}"
    }

    return ret
}