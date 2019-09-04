package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.bencher.Benchmark
import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.JMHConstants
import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.JdtBenchFinder
import ch.uzh.ifi.seal.bencher.execution.ConfigBasedConfigurator
import ch.uzh.ifi.seal.bencher.execution.ExecutionConfiguration
import ch.uzh.ifi.seal.bencher.execution.unsetExecConfig
import ch.uzh.ifi.seal.smr.soa.java.JavaSourceVersionExtractor
import ch.uzh.ifi.seal.smr.soa.java.JavaTargetVersionExtractor
import ch.uzh.ifi.seal.smr.soa.jmhversion.JmhSourceCodeVersionExtractor
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.convertToString
import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.apache.logging.log4j.LogManager
import org.funktionale.option.Option
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

private val log = LogManager.getLogger()

fun main(args: Array<String>) {
    disableSystemErr()

    if (args.size != 4) {
        log.error("Needed arguments: inputFile inputDir outputDir outputFile #threads")
        exitProcess(-1)
    }

    val inputFile = File(args[0])
    val inputDir = args[1]
    val outputDir = File(args[2])
    val outputFile = File(args[3])
    val numberOfThreads = args[4].toInt()

    val executor = Executors.newFixedThreadPool(numberOfThreads) as ThreadPoolExecutor
    inputFile.forEachLine { project ->
        executor.submit<Any> {
            val dir = File(inputDir + project.toFileSystemName)
            if (dir.exists()) {
                log.info("Process $dir start")
                doPerProject(project, null, dir, outputDir, outputFile)
                log.info("Process $dir end")
            } else {
                log.warn("Project $project does not exist in input directory -> skipped")
            }
        }
    }
}

private fun doPerProject(project: String, codeVersion: String?, sourceDir: File, outputDir: File, outputFile: File) {
    try {
        val jmhVersion = JmhSourceCodeVersionExtractor(sourceDir).get()
        val javaTarget = JavaTargetVersionExtractor(sourceDir).get()
        val javaSource = JavaSourceVersionExtractor(sourceDir).get()

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

        val hashes = finder.methodHashes()
        if (hashes.isLeft()) {
            throw RuntimeException("Could not retrieve hashes: ${hashes.left().get()}")
        }

        val cb = be.right().get()
        val cc = ce.right().get()
        val configurator = ConfigBasedConfigurator(unsetExecConfig, cc, cb)

        val results = mutableListOf<Result>()

        benchs.right().get().forEach { bench ->
            val res = configurator.config(bench)
            if (res.isLeft()) {
                throw java.lang.RuntimeException("Could not retrieve config: ${res.left().get()}")
            }

            val config = res.right().get()
            val configBench = cb.getValue(bench)
            val configClass = cc.toList().filter { bench.clazz == it.first.name }.first().second
            val hash = hashes.right().get().getValue(bench)
            val jmhParamSource = finder.jmhParamSource(bench)

            val item = convertResult(project, codeVersion, jmhVersion, javaTarget, javaSource, bench, config, configBench, configClass, jmhParamSource, hash)
            results.add(item)
        }

        OpenCSVWriter.write(Paths.get(outputDir.absolutePath, "${project.toFileSystemName}.csv"), results)
    } catch (e: Exception) {
        log.error("Error during parsing project $project at code version $codeVersion: ${e.message}")
        outputFile.appendText("$project;$codeVersion;${e.message}\n")
    }
}

private fun convertResult(project: String, codeVersion: String?, jmhVersion: JMHVersion?, javaTarget: String?, javaSource: String?, bench: Benchmark, ec: ExecutionConfiguration, b: ExecutionConfiguration, c: ExecutionConfiguration, jmhParamSource: Map<String, String>, hash: ByteArray): Result {
    return Result(
            project = project,
            codeVersion = codeVersion,
            jmhVersion = jmhVersion,
            javaTarget = javaTarget,
            javaSource = javaSource,
            benchmarkName = "${bench.clazz}.${bench.name}",
            warmupIterations = iterations(ec.warmupIterations, unsetExecConfig.warmupIterations),
            warmupIterationsClass = iterations(c.warmupIterations, unsetExecConfig.warmupIterations),
            warmupIterationsMethod = iterations(b.warmupIterations, unsetExecConfig.warmupIterations),
            warmupTime = time(ec.warmupTime, ec.warmupTimeUnit, unsetExecConfig.warmupTime, unsetExecConfig.warmupTimeUnit),
            warmupTimeClass = time(c.warmupTime, c.warmupTimeUnit, unsetExecConfig.warmupTime, unsetExecConfig.warmupTimeUnit),
            warmupTimeMethod = time(b.warmupTime, b.warmupTimeUnit, unsetExecConfig.warmupTime, unsetExecConfig.warmupTimeUnit),
            warmupTimeStatus = status(ec.warmupTime, ec.warmupTimeUnit, unsetExecConfig.warmupTime, unsetExecConfig.warmupTimeUnit),
            warmupTimeStatusClass = status(c.warmupTime, c.warmupTimeUnit, unsetExecConfig.warmupTime, unsetExecConfig.warmupTimeUnit),
            warmupTimeStatusMethod = status(b.warmupTime, b.warmupTimeUnit, unsetExecConfig.warmupTime, unsetExecConfig.warmupTimeUnit),
            measurementIterations = iterations(ec.measurementIterations, unsetExecConfig.measurementIterations),
            measurementIterationsClass = iterations(c.measurementIterations, unsetExecConfig.measurementIterations),
            measurementIterationsMethod = iterations(b.measurementIterations, unsetExecConfig.measurementIterations),
            measurementTime = time(ec.measurementTime, ec.measurementTimeUnit, unsetExecConfig.measurementTime, unsetExecConfig.measurementTimeUnit),
            measurementTimeClass = time(c.measurementTime, c.measurementTimeUnit, unsetExecConfig.measurementTime, unsetExecConfig.measurementTimeUnit),
            measurementTimeMethod = time(b.measurementTime, b.measurementTimeUnit, unsetExecConfig.measurementTime, unsetExecConfig.measurementTimeUnit),
            measurementTimeStatus = status(ec.measurementTime, ec.measurementTimeUnit, unsetExecConfig.measurementTime, unsetExecConfig.measurementTimeUnit),
            measurementTimeStatusClass = status(c.measurementTime, c.measurementTimeUnit, unsetExecConfig.measurementTime, unsetExecConfig.measurementTimeUnit),
            measurementTimeStatusMethod = status(b.measurementTime, b.measurementTimeUnit, unsetExecConfig.measurementTime, unsetExecConfig.measurementTimeUnit),
            forks = forks(ec.forks, unsetExecConfig.forks),
            forksClass = forks(c.forks, unsetExecConfig.forks),
            forksMethod = forks(b.forks, unsetExecConfig.forks),
            warmupForks = forks(ec.warmupForks, unsetExecConfig.warmupForks),
            warmupForksClass = forks(c.warmupForks, unsetExecConfig.warmupForks),
            warmupForksMethod = forks(b.warmupForks, unsetExecConfig.warmupForks),
            modeIsThroughput = mode(ec.mode, unsetExecConfig.mode, "Throughput"),
            modeIsThroughputClass = mode(c.mode, unsetExecConfig.mode, "Throughput"),
            modeIsThroughputMethod = mode(b.mode, unsetExecConfig.mode, "Throughput"),
            modeIsAverageTime = mode(ec.mode, unsetExecConfig.mode, "AverageTime"),
            modeIsAverageTimeClass = mode(c.mode, unsetExecConfig.mode, "AverageTime"),
            modeIsAverageTimeMethod = mode(b.mode, unsetExecConfig.mode, "AverageTime"),
            modeIsSampleTime = mode(ec.mode, unsetExecConfig.mode, "SampleTime"),
            modeIsSampleTimeClass = mode(c.mode, unsetExecConfig.mode, "SampleTime"),
            modeIsSampleTimeMethod = mode(b.mode, unsetExecConfig.mode, "SampleTime"),
            modeIsSingleShotTime = mode(ec.mode, unsetExecConfig.mode, "SingleShotTime"),
            modeIsSingleShotTimeClass = mode(c.mode, unsetExecConfig.mode, "SingleShotTime"),
            modeIsSingleShotTimeMethod = mode(b.mode, unsetExecConfig.mode, "SingleShotTime"),
            paramString = paramString(bench.params),
            paramCount = bench.params.size,
            hasBlackhole = bench.params.contains(JMHConstants.Class.blackhole),
            hasControl = bench.params.contains(JMHConstants.Class.control),
            hasBenchmarkParams = bench.params.contains(JMHConstants.Class.benchmarkParams),
            hasIterationParams = bench.params.contains(JMHConstants.Class.iterationParams),
            hasThreadParams = bench.params.contains(JMHConstants.Class.threadParams),
            jmhParamString = jmhParamString(jmhParamSource),
            jmhParamPairs = jmhParamPairs(bench.jmhParams),
            jmhParamCount = jmhParamSource.size,
            jmhParamFromStateObjectCount = jmhParamStateObjectCounter(bench.clazz, jmhParamSource),
            returnType = returnType(bench.returnType),
            partOfGroup = bench.group != null,
            methodHash = hash.convertToString
    )
}

private fun inSeconds(time: Int, unit: Option<TimeUnit>): Long? {
    if (time == -1) {
        return null
    }

    val u = if (unit == Option.empty<TimeUnit>()) {
        TimeUnit.SECONDS
    } else {
        unit.get()
    }
    return TimeUnit.NANOSECONDS.convert(time.toLong(), u)
}

private fun iterations(value: Int, default: Int): Int? {
    return if (value == default) {
        null
    } else {
        value
    }
}

private fun time(value: Int, valueUnit: Option<TimeUnit>, default: Int, defaultUnit: Option<TimeUnit>): Long? {
    return if (value == default && valueUnit == defaultUnit) {
        null
    } else {
        inSeconds(value, valueUnit)
    }
}

private fun forks(value: Int, default: Int): Int? {
    return if (value == default) {
        null
    } else {
        value
    }
}

private fun mode(value: List<String>, default: List<String>, type: String): Boolean? {
    return if (value == default) {
        null
    } else {
        value.contains(type) || value.contains("All")
    }
}

private fun paramString(params: List<String>): String? {
    return if (params.isEmpty()) {
        null
    } else {
        params.joinToString(separator = "&")
    }
}

private fun jmhParamString(jmhParamSource: Map<String, String>): String? {
    return if (jmhParamSource.isEmpty()) {
        null
    } else {
        var res = ""
        jmhParamSource.toList().forEachIndexed { index, (value, source) ->
            if (index > 0) {
                res += "&"
            }
            res += "$value=$source"
        }
        res
    }
}

private fun jmhParamStateObjectCounter(benchmarkClass: String, jmhParamSource: Map<String, String>): Int {
    return jmhParamSource.map { (value, source) ->
        if (source == benchmarkClass) {
            0
        } else {
            1
        }
    }.sum()
}

private fun status(value: Int, valueUnit: Option<TimeUnit>, default: Int, defaultUnit: Option<TimeUnit>): Status {
    return when {
        value == default && valueUnit == defaultUnit -> Status.BOTH_UNSET
        valueUnit == defaultUnit -> Status.TIME_SET
        value == default -> Status.UNIT_SET
        else -> Status.BOTH_SET
    }
}

private fun returnType(input: String): String? {
    return if (input == "void") {
        null
    } else {
        input
    }
}

private fun jmhParamPairs(jmhParams: List<Pair<String, String>>): String? {
    return if (jmhParams.isEmpty()) {
        null
    } else {
        var res = ""
        jmhParams.forEachIndexed { index, (name, value) ->
            if (index > 0) {
                res += "&"
            }
            res += "$name=$value"
        }
        res
    }
}