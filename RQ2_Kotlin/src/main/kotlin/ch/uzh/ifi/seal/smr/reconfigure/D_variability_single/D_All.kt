package ch.uzh.ifi.seal.smr.reconfigure.D_variability_single

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineKey
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineParser
import java.io.File

class D_All(private val csvInput: File, private val outputDir: String) {
    fun run() {
        val cov = COV(outputDir)
        val ci = CiPercentage(outputDir)
        val divergence = Divergence(outputDir)

        cov.generateHeader()
        ci.generateHeader()
        divergence.generateHeader()

        csvInput.walk().forEach {
            if (it.isFile) {
                val (project, benchmark, params) = it.nameWithoutExtension.split("#")
                val key = CsvLineKey(project, "", benchmark, params)
                val list = CsvLineParser(it).getList()

                cov.run(key, list)
                ci.run(key, list)
                divergence.run(key, list)
                println("D: ${it.absolutePath} processed")
            }
        }
    }
}

