package ch.uzh.ifi.seal.smr.soa.analysis.benchmarkrepocorrelation

import ch.uzh.ifi.seal.smr.soa.utils.toInt
import com.opencsv.bean.CsvBindByPosition

class ResBenchmarkRepoCorrelation {
    @CsvBindByPosition(position = 0)
    var project: String

    @CsvBindByPosition(position = 1)
    var className: String

    @CsvBindByPosition(position = 2)
    var benchmarkName: String

    @CsvBindByPosition(position = 3)
    var warmupIterations: Int?

    @CsvBindByPosition(position = 4)
    var warmupIterationsDefault: Int

    @CsvBindByPosition(position = 5)
    var warmupTime: Long?

    @CsvBindByPosition(position = 6)
    var warmupTimeDefault: Int

    @CsvBindByPosition(position = 7)
    var measurementIterations: Int?

    @CsvBindByPosition(position = 8)
    var measurementIterationsDefault: Int

    @CsvBindByPosition(position = 9)
    var measurementTime: Long?

    @CsvBindByPosition(position = 10)
    var measurementTimeDefault: Int

    @CsvBindByPosition(position = 11)
    var forks: Int?

    @CsvBindByPosition(position = 12)
    var forksDefault: Int

    @CsvBindByPosition(position = 13)
    var warmupForks: Int?

    @CsvBindByPosition(position = 14)
    var warmupForksDefault: Int

    @CsvBindByPosition(position = 15)
    var modeIsThroughput: Int?

    @CsvBindByPosition(position = 16)
    var modeIsAverageTime: Int?

    @CsvBindByPosition(position = 17)
    var modeIsSampleTime: Int?

    @CsvBindByPosition(position = 18)
    var modeIsSingleShotTime: Int?

    @CsvBindByPosition(position = 19)
    var modeDefault: Int

    @CsvBindByPosition(position = 20)
    var stars: Int

    @CsvBindByPosition(position = 21)
    var projectForks: Int

    @CsvBindByPosition(position = 22)
    var watchers: Int

    @CsvBindByPosition(position = 23)
    var numberOfCommits: Int

    @CsvBindByPosition(position = 24)
    var numberOfContributors: Int

    @CsvBindByPosition(position = 25)
    var numberOfBenchmarks: Int

    @CsvBindByPosition(position = 26)
    var nothingSet: Int

    @CsvBindByPosition(position = 27)
    var parameterizationCombinations: Int

    constructor(project: String, className: String, benchmarkName: String, warmupIterations: Int?, warmupTime: Long?, measurementIterations: Int?, measurementTime: Long?, forks: Int?, warmupForks: Int?, modeIsThroughput: Int?, modeIsAverageTime: Int?, modeIsSampleTime: Int?, modeIsSingleShotTime: Int?, stars: Int, projectForks: Int, watchers: Int, numberOfCommits: Int, numberOfContributors: Int, numberOfBenchmarks: Int, nothingSet: Int, parameterizationCombinations: Int) {
        this.project = project
        this.className = className
        this.benchmarkName = benchmarkName
        this.warmupIterations = warmupIterations
        this.warmupIterationsDefault = (warmupIterations == null).toInt()
        this.warmupTime = warmupTime
        this.warmupTimeDefault = (warmupTime == null).toInt()
        this.measurementIterations = measurementIterations
        this.measurementIterationsDefault = (measurementIterations == null).toInt()
        this.measurementTime = measurementTime
        this.measurementTimeDefault = (measurementTime == null).toInt()
        this.forks = forks
        this.forksDefault = (forks == null).toInt()
        this.warmupForks = warmupForks
        this.warmupForksDefault = (warmupForks == null).toInt()
        this.modeIsThroughput = modeIsThroughput
        this.modeIsAverageTime = modeIsAverageTime
        this.modeIsSampleTime = modeIsSampleTime
        this.modeIsSingleShotTime = modeIsSingleShotTime
        this.modeDefault = (modeIsThroughput == null || modeIsAverageTime == null || modeIsSampleTime == null || modeIsSingleShotTime == null).toInt()
        this.stars = stars
        this.projectForks = projectForks
        this.watchers = watchers
        this.numberOfCommits = numberOfCommits
        this.numberOfContributors = numberOfContributors
        this.numberOfBenchmarks = numberOfBenchmarks
        this.nothingSet = nothingSet
        this.parameterizationCombinations = parameterizationCombinations
    }
}