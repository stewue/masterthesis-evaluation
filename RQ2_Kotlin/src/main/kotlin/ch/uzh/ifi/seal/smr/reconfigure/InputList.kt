package ch.uzh.ifi.seal.smr.reconfigure

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
            println("${it.clazz}.${it.name};$params")
        }
    }
}