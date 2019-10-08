package ch.uzh.ifi.seal.smr.soa.analysis.executiontimetestsuite

import ch.uzh.ifi.seal.bencher.JMHVersion
import com.opencsv.bean.CsvBindByPosition

class ResExecutionTimeTestSuite {
    @CsvBindByPosition(position = 0)
    var project: String

    @CsvBindByPosition(position = 1)
    var jmhVersion: JMHVersion

    @CsvBindByPosition(position = 2)
    var totalExecutionTime: Double

    @CsvBindByPosition(position = 3)
    var totalExecutionTimeDefault: Double

    @CsvBindByPosition(position = 4)
    var totalExecutionTimePercentage: Double

    @CsvBindByPosition(position = 5)
    var benchmarks: Int

    @CsvBindByPosition(position = 6)
    var parameterizationCombinations: Int

    constructor(project: String, jmhVersion: JMHVersion, totalExecutionTime: Double, totalExecutionTimeDefault: Double, benchmarks: Int, parameterizationCombinations: Int) {
        this.project = project
        this.jmhVersion = jmhVersion
        this.totalExecutionTime = totalExecutionTime
        this.totalExecutionTimeDefault = totalExecutionTimeDefault
        this.totalExecutionTimePercentage = totalExecutionTime / totalExecutionTimeDefault
        this.benchmarks = benchmarks
        this.parameterizationCombinations = parameterizationCombinations
    }
}