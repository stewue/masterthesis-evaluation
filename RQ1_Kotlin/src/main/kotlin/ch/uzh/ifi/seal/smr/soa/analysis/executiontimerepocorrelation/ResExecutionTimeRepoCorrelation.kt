package ch.uzh.ifi.seal.smr.soa.analysis.executiontimerepocorrelation

import ch.uzh.ifi.seal.bencher.JMHVersion
import com.opencsv.bean.CsvBindByPosition

class ResExecutionTimeRepoCorrelation {
    @CsvBindByPosition(position = 0)
    var project: String

    @CsvBindByPosition(position = 1)
    var className: String

    @CsvBindByPosition(position = 2)
    var jmhVersion: JMHVersion

    @CsvBindByPosition(position = 3)
    var benchmarkName: String

    @CsvBindByPosition(position = 4)
    var executionTimePercentage: Double

    @CsvBindByPosition(position = 5)
    var warmupTimePercentage: Double

    @CsvBindByPosition(position = 6)
    var measurementTimePercentage: Double

    @CsvBindByPosition(position = 7)
    var measurementWarmupRatio: Double

    @CsvBindByPosition(position = 8)
    var stars: Int

    @CsvBindByPosition(position = 9)
    var forks: Int

    @CsvBindByPosition(position = 10)
    var watchers: Int

    @CsvBindByPosition(position = 11)
    var numberOfCommits: Int

    @CsvBindByPosition(position = 12)
    var numberOfContributors: Int

    @CsvBindByPosition(position = 13)
    var numberOfBenchmarks: Int

    constructor(project: String, className: String, jmhVersion: JMHVersion, benchmarkName: String, executionTimePercentage: Double, warmupTimePercentage: Double, measurementTimePercentage: Double, measurementWarmupRatio: Double, stars: Int, forks: Int, watchers: Int, numberOfCommits: Int, numberOfContributors: Int, numberOfBenchmarks: Int) {
        this.project = project
        this.className = className
        this.jmhVersion = jmhVersion
        this.benchmarkName = benchmarkName
        this.executionTimePercentage = executionTimePercentage
        this.warmupTimePercentage = warmupTimePercentage
        this.measurementTimePercentage = measurementTimePercentage
        this.measurementWarmupRatio = measurementWarmupRatio
        this.stars = stars
        this.forks = forks
        this.watchers = watchers
        this.numberOfCommits = numberOfCommits
        this.numberOfContributors = numberOfContributors
        this.numberOfBenchmarks = numberOfBenchmarks
    }
}