package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.JdtBenchFinder
import ch.uzh.ifi.seal.bencher.execution.ConfigBasedConfigurator
import ch.uzh.ifi.seal.bencher.execution.unsetExecConfig
import ch.uzh.ifi.seal.smr.soa.evaluation.EvaluationHelper.convertResult
import ch.uzh.ifi.seal.smr.soa.java.JavaSourceVersionExtractor
import ch.uzh.ifi.seal.smr.soa.java.JavaTargetVersionExtractor
import ch.uzh.ifi.seal.smr.soa.jmhversion.JmhSourceCodeVersionExtractor
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.apache.logging.log4j.LogManager
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

abstract class Evaluation(private val inputFile: File, private val inputDir: String, private val outputDir: File, private val outputFile: File) {
    protected val log = LogManager.getLogger()

    fun start(numberOfThreads: Int) {
        val executor = Executors.newFixedThreadPool(numberOfThreads) as ThreadPoolExecutor
        inputFile.forEachLine { project ->
            executor.submit<Any> {
                val dir = File(inputDir + project.toFileSystemName)
                if (dir.exists()) {
                    log.info("[$project] evaluation started")
                    processProject(project, dir, outputDir, outputFile)
                    log.info("[$project] evaluation ended")
                } else {
                    log.warn("[$project] repo does not exist -> evaluation skipped")
                }
            }
        }
    }

    protected abstract fun processProject(project: String, sourceDir: File, outputDir: File, outputFile: File)

    protected fun evaluate(project: String, commitId: String?, commitTime: Int?, sourceDir: File, outputDir: File, outputFile: File) {
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

                val item = convertResult(project, commitId, commitTime, jmhVersion, javaTarget, javaSource, bench, config, configBench, configClass, jmhParamSource, hash)
                results.add(item)
            }

            OpenCSVWriter.write(Paths.get(outputDir.absolutePath, "${project.toFileSystemName}.csv"), results)
        } catch (e: Exception) {
            log.error("Error during parsing project $project at code version $commitId: ${e.message}")
            outputFile.appendText("$project;$commitId;${e.message}\n")
        }
    }
}