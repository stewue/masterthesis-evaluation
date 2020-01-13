package ch.uzh.ifi.seal.smr.reconfigure.process.A_benchmark_list

import ch.uzh.ifi.seal.bencher.analysis.finder.asm.AsmBenchFinder
import java.io.File

fun main() {
    val jarPath = "C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Projects\\log4j2.jar"
    val packageName = "org.apache.logging.log4j"

    val jar = File(jarPath)
    val bf = AsmBenchFinder(jar, packageName).all()

    if (bf.isLeft()) {
        throw RuntimeException(bf.left().get())
    }

    bf.right().get().forEach {
        it.parameterizedBenchmarks().forEach {
            val params = it.jmhParams.map { "${it.first}=${it.second}" }.joinToString("&")
            if (it.group == null) {
                println("${it.clazz}.${it.name};$params")
            } else {
                println("${it.clazz}.${it.group};$params")
            }
        }
    }
}