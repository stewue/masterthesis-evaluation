package ch.uzh.ifi.seal.smr.reconfigure.A_benchmark_list

import ch.uzh.ifi.seal.bencher.analysis.finder.asm.AsmBenchFinder
import java.io.File

fun main() {
    val jar = File("D:\\log4j2.jar")
    val bf = AsmBenchFinder(jar, "org.apache.logging.log4j").all()

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