package ch.uzh.ifi.seal.smr.soa.history

import ch.uzh.ifi.seal.bencher.Benchmark
import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.JdtBenchFinder
import ch.uzh.ifi.seal.bencher.execution.ConfigBasedConfigurator
import ch.uzh.ifi.seal.bencher.execution.ExecutionConfiguration
import ch.uzh.ifi.seal.bencher.execution.defaultExecConfig
import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import java.io.File

val commits = listOf("0f681e6", "dcc91a7", "17b51aa", "2e4c35a", "d5056f2", "3d2e602", "0cd8cd3", "cfa0e4b", "38c0402", "beaf576", "84b7383", "ab7e923")
val dir = File("D:\\test")
// Pair first=bodyHash, second=(commit, benchmark)
val benchsLastChanged = mutableMapOf<String, Pair<ByteArray, Triple<String, Benchmark, ExecutionConfiguration>>>()

fun main() {
    disableSystemErr()

    commits.reversed().forEach { commit ->
        checkout(commit)

        val jdt = JdtBenchFinder(dir, "")
        val benchs = jdt.all().right().get()
        val classConfig = jdt.classExecutionInfos().right().get()
        val benchConfig = jdt.benchmarkExecutionInfos().right().get()

        val version = JMHVersion(1, 21)
        val configurator = ConfigBasedConfigurator(defaultExecConfig(version), classConfig, benchConfig)

        val hashes = jdt.hashes()

        benchs.forEach { bench ->
            val fqn = "${bench.clazz}.${bench.name}"
            val item = benchsLastChanged[fqn]
            val config = configurator.config(bench).right().get()
            val hash = hashes.getValue(bench)

            if (item == null) {
                benchsLastChanged[fqn] = Pair(hash, Triple(commit, bench, config))
                println("Benchmark $fqn found for the first time in commit $commit")
            } else {
                val lastBodyHash = benchsLastChanged[fqn]!!.first
                val lastBench = benchsLastChanged[fqn]!!.second.second
                val lastBenchConfig = benchsLastChanged[fqn]!!.second.third
                if (!lastBodyHash.contentEquals(hash)) {
                    benchsLastChanged[fqn] = Pair(hash, Triple(commit, bench, config))
                    println("Method body of benchmark $fqn was updated in commit $commit")
                } else if (lastBench.jmhParams != bench.jmhParams || config != lastBenchConfig) {
                    benchsLastChanged[fqn] = Pair(hash, Triple(commit, bench, config))
                    println("JmhParams or configuration of benchmark $fqn was updated in commit $commit")
                }
            }

        }
    }
}

fun checkout(commit: String) {
    val process = Runtime.getRuntime().exec("git checkout $commit", null, dir)
    process.waitFor()

    println("HEAD is now at $commit")
}