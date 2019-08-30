package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.bencher.Benchmark
import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.JdtBenchFinder
import ch.uzh.ifi.seal.bencher.execution.ConfigBasedConfigurator
import ch.uzh.ifi.seal.bencher.execution.ExecutionConfiguration
import ch.uzh.ifi.seal.bencher.execution.unsetExecConfig
import ch.uzh.ifi.seal.smr.soa.jmhversion.JmhSourceCodeVersionExtractor
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import org.apache.logging.log4j.LogManager
import org.funktionale.option.Option
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

private val log = LogManager.getLogger()

fun main() {
    disableSystemErr()

    val dir = File("D:/projects")
    val outputDir = "D:/projects"

    dir.listFiles().forEach {
        if (it.isDirectory) {
            val name = it.name
            log.info("Process $name")
            doPerProject(name, null, it, outputDir)
        }
    }
}

fun doPerProject(project: String, codeVersion: String?, sourceDir: File, outputDir: String) {
    try {
        val jmhVersion = JmhSourceCodeVersionExtractor(sourceDir).get()

        val finder = JdtBenchFinder(sourceDir)

        val benchs = finder.all()
        if (benchs.isLeft()) {
            throw RuntimeException("Could not retrieve benchmarks: ${benchs.left().get()}")
        }

        val ce = finder.classExecutionInfos()
        if (ce.isLeft()) {
            throw RuntimeException("Could not retrieve class execution info: ${ce.left().get()}")
        }
        val be = finder.benchmarkExecutionInfos()
        if (be.isLeft()) {
            throw RuntimeException("Could not retrieve benchmark execution info: ${be.left().get()}")
        }

        val default = unsetExecConfig

        val configurator = ConfigBasedConfigurator(unsetExecConfig, ce.right().get(), be.right().get())

        val results = mutableListOf<Result>()

        benchs.right().get().forEach {
            val res = configurator.config(it)
            if (res.isLeft()) {
                throw java.lang.RuntimeException("Could not retrieve config: ${res.left().get()}")
            }

            val config = res.right().get()
            val item = convertResult(project, codeVersion, jmhVersion, it, config)
            results.add(item)
        }

        OpenCSVWriter.write(Paths.get(outputDir, "$project.csv"), results)
    } catch (e: Exception) {
        log.error("Error during parsing project $project at code version $codeVersion: ${e.message}")
    }
}

fun convertResult(p: String, codeVersion: String?, jmhVersion: JMHVersion?, bench: Benchmark, c: ExecutionConfiguration): Result {
    val project = p.replace('#', '/')
    val benchmarkName = "${bench.clazz}.${bench.name}"
    val warmupIterations = if (c.warmupIterations == unsetExecConfig.warmupIterations) {
        null
    } else {
        c.warmupIterations
    }
    val warmupTime = if (c.warmupTime == unsetExecConfig.warmupTime && c.warmupTimeUnit == unsetExecConfig.warmupTimeUnit) {
        null
    } else {
        inSeconds(c.warmupTime, c.warmupTimeUnit)
    }
    val measurementIterations = if (c.measurementIterations == unsetExecConfig.measurementIterations) {
        null
    } else {
        c.measurementIterations
    }
    val measurementTime = if (c.measurementTime == unsetExecConfig.measurementTime && c.measurementTimeUnit == unsetExecConfig.measurementTimeUnit) {
        null
    } else {
        inSeconds(c.measurementTime, c.measurementTimeUnit)
    }
    val forks = if (c.forks == unsetExecConfig.forks) {
        null
    } else {
        c.forks
    }
    val warmupForks = if (c.warmupForks == unsetExecConfig.warmupForks) {
        null
    } else {
        c.warmupForks
    }
    val (modeIsThroughput, modeIsAverageTime, modeIsSampleTime, modeIsSingleShotTime) = if (c.mode == unsetExecConfig.mode) {
        listOf(null, null, null, null)
    } else {
        listOf(
                c.mode.contains("Throughput") || c.mode.contains("All"),
                c.mode.contains("AverageTime") || c.mode.contains("All"),
                c.mode.contains("SampleTime") || c.mode.contains("All"),
                c.mode.contains("SingleShotTime") || c.mode.contains("All")
        )
    }
    val methodHasParams = bench.params.isNotEmpty()
    return Result(project, codeVersion, jmhVersion, benchmarkName, warmupIterations, warmupTime, measurementIterations, measurementTime, forks, warmupForks, modeIsThroughput, modeIsAverageTime, modeIsSampleTime, modeIsSingleShotTime, methodHasParams)
}

fun inSeconds(time: Int, unit: Option<TimeUnit>): Long {
    return if (time == -1 || !unit.isDefined()) {
        -1
    } else {
        TimeUnit.SECONDS.convert(time.toLong(), unit.get())
    }
}