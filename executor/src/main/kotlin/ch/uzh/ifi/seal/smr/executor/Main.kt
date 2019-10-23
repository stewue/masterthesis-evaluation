package ch.uzh.ifi.seal.smr.executor

import java.io.File

fun main() {
    val units = listOf(
        Unit(
            "~/jdk-13/bin/java",
            "~/thesis/log4j2.jar",
            "~/thesis/log4j2.json",
            "-e org.apache.logging.log4j.perf.jmh.Log4j2AppenderComparisonBenchmark.appenderMMap -e org.apache.logging.log4j.perf.jmh.Log4j2AppenderComparisonBenchmark.end2endMMap"
        )
    )

    units.forEach {
        val cmd = "${it.javaPath} -jar ${it.jarPath} ${it.patternString} -bm sample -f 1 -i 100 -wi 0 -r 1 -tu s -rf json -rff ${it.outputFile}"
        executeCommand(cmd)
    }
}

fun executeCommand(cmd: String, envVars: Array<String>? = null, dir: File? = null) {
    val process = Runtime.getRuntime().exec(cmd, envVars, dir)

    process.inputStream.reader().use {}
    process.errorStream.reader().use {}

    process.waitFor()
}