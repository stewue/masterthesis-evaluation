package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.JdtBenchFinder
import ch.uzh.ifi.seal.bencher.execution.ConfigBasedConfigurator
import ch.uzh.ifi.seal.bencher.execution.defaultExecConfig
import org.funktionale.option.Option
import java.io.File
import java.util.concurrent.TimeUnit

fun main() {
    val project = "myProject"
    val sourceDir = File("D:\\logging-log4j2-log4j-2.8")
    val version = JMHVersion(1, 20)

    val finder = JdtBenchFinder(sourceDir)

    val ce = finder.classExecutionInfos()
    if (ce.isLeft()) {
        throw RuntimeException("Could not retrieve class execution info: ${ce.left().get()}")
    }
    val be = finder.benchmarkExecutionInfos()
    if (be.isLeft()) {
        throw RuntimeException("Could not retrieve benchmark execution info: ${be.left().get()}")
    }

    val default = defaultExecConfig(version)

    val configurator = ConfigBasedConfigurator(default, ce.right().get(), be.right().get())

    val results = mutableListOf<Result>()

    finder.all().right().get().forEach {
        val res = configurator.config(it)

        if (res.isLeft()) {
            throw java.lang.RuntimeException("Could not retrieve config: ${res.left().get()}")
        }

        val c = res.right().get()

        val benchmarkName = "${it.clazz}.${it.name}${JmhParamHelper.getJmhParamsString(it.jmhParams)}"
        val warmupIterationsIsDefault = isDefault(c.warmupIterations, default.warmupIterations)
        val warmupTime = inSeconds(c.warmupTime, c.warmupTimeUnit)
        val warmupTimeIsDefault = isDefault(warmupTime, inSeconds(default.warmupTime, default.warmupTimeUnit))
        val measurementIterationsIsDefault = isDefault(c.measurementIterations, default.measurementIterations)
        val measurementTime = inSeconds(c.measurementTime, c.measurementTimeUnit)
        val measurementTimeIsDefault = isDefault(measurementTime, inSeconds(default.measurementTime, default.measurementTimeUnit))
        val forksIsDefault = isDefault(c.forks, default.forks)
        val warmupForksIsDefault = isDefault(c.warmupForks, default.warmupForks)
        val modeIsDefault = isDefault(c.mode, default.mode)

        val r = Result(project, benchmarkName, c.warmupIterations, warmupIterationsIsDefault, warmupTime, warmupTimeIsDefault, c.measurementIterations, measurementIterationsIsDefault, measurementTime, measurementTimeIsDefault, c.forks, forksIsDefault, c.warmupForks, warmupForksIsDefault, c.mode, modeIsDefault)
        results.add(r)
    }

    OpenCSVWriter.write("test.csv", results)
}

fun isDefault(a: Any, b: Any) = a == b

fun inSeconds(time: Int, unit: Option<TimeUnit>): Long {
    return if (time == -1 || !unit.isDefined()) {
        -1
    } else {
        TimeUnit.SECONDS.convert(time.toLong(), unit.get())
    }
}