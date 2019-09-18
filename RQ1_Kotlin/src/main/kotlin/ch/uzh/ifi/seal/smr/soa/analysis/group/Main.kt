package ch.uzh.ifi.seal.smr.soa.analysis.group

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.Group
import ch.uzh.ifi.seal.smr.soa.utils.Row
import java.io.File

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val all = CsvProjectParser(file).getList().filter { it.mainRepo == true }
    doGroup(all, Group.PROFESSIONAL_MANY)
    doGroup(all, Group.PROFESSIONAL_FEW)
    doGroup(all, Group.NOT_PROFESSIONAL_MANY)
    doGroup(all, Group.NOT_PROFESSIONAL_FEW)
}

private fun doGroup(all: Collection<Row>, group: Group) {
    println("~~~$group~~~ ${all.filter { it.getGroup() == group }.size}")
}