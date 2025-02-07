package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.JdtBenchFinder
import ch.uzh.ifi.seal.bencher.execution.ConfigBasedConfigurator
import ch.uzh.ifi.seal.bencher.execution.unsetExecConfig
import ch.uzh.ifi.seal.smr.soa.evaluation.EvaluationHelper.convertResult
import ch.uzh.ifi.seal.smr.soa.evaluation.history.HistoryManager
import ch.uzh.ifi.seal.smr.soa.evaluation.java.JavaSourceVersionExtractor
import ch.uzh.ifi.seal.smr.soa.evaluation.java.JavaTargetVersionExtractor
import ch.uzh.ifi.seal.smr.soa.evaluation.jmhversion.JmhSourceCodeVersionExtractor
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.Result
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.apache.logging.log4j.LogManager
import org.eclipse.jgit.api.Git
import java.io.File
import java.nio.file.Path

abstract class Evaluation(private val inputFile: File, private val inputDir: String, private val outputDir: File, private val outputFile: File) {
    protected val log = LogManager.getLogger()

    fun start() {
        inputFile.forEachLine { project ->
            val dir = File(inputDir + project.toFileSystemName)
            if (dir.exists()) {
                log.info("[$project] evaluation started")
                processProject(project, dir, outputDir, outputFile)
            } else {
                log.warn("[$project] repo does not exist -> evaluation skipped")
            }
        }
    }

    protected open fun processProject(project: String, sourceDir: File, outputDir: File, outputFile: File) {
        val repository = HistoryManager.getRepo(sourceDir)
        val git = Git(repository)
        HistoryManager.resetToBranch(git)
    }

    protected fun evaluate(project: String, commitId: String?, commitTime: Int?, sourceDir: File, resultFile: Path, outputFile: File) {
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

            val allNumberOfLines = finder.methodNumberOfLines()
            if (allNumberOfLines.isLeft()) {
                throw RuntimeException("Could not retrieve number of lines: ${allNumberOfLines.left().get()}")
            }

            val so = finder.stateObj()
            if (hashes.isLeft()) {
                throw RuntimeException("Could not retrieve stateObj: ${hashes.left().get()}")
            }
            val stateObjects = stateObjectToSourceCodeFqns(so.right().get())

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
                val numberOfLines = allNumberOfLines.right().get().getValue(bench)
                val jmhParamSource = finder.jmhParamSource(bench)

                val item = convertResult(project, commitId, commitTime, jmhVersion, javaTarget, javaSource, bench, config, configBench, configClass, jmhParamSource, hash, numberOfLines, stateObjects)
                results.add(item)
            }

            OpenCSVWriter.write(resultFile, results)
        } catch (e: Exception) {
            log.error("Error during parsing project $project at code version $commitId: ${e.message}")
            outputFile.appendText("$project;$commitId;${e.message}\n")
        }
    }

    private fun stateObjectToSourceCodeFqns(input: Map<String, Map<String, MutableList<String>>>): Map<String, Map<String, MutableList<String>>> {
        return input.map { (fqn, values) ->
            fqn.replace("$", ".") to values
        }.toMap()
    }
}