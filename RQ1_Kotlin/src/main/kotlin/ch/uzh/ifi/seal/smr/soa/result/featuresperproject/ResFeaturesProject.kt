package ch.uzh.ifi.seal.smr.soa.result.featuresperproject

import com.opencsv.bean.CsvBindByPosition

class ResFeaturesProject {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    var numberOfBenchmarks: Int? = null

    @CsvBindByPosition(position = 2)
    var avgNumberOfBenchmarksPerClass: Double? = null

    @CsvBindByPosition(position = 3)
    var avgNumberOfBenchmarksPerFile: Double? = null

    @CsvBindByPosition(position = 4)
    var parametrizationUsedPercentage: Double? = null

    @CsvBindByPosition(position = 5)
    var groupsUsedPercentage: Double? = null

    @CsvBindByPosition(position = 6)
    var blackholeUsedPercentage: Double? = null

    @CsvBindByPosition(position = 7)
    var controlUsedPercentage: Double? = null

    @CsvBindByPosition(position = 8)
    var hasStateObjectsWithJmhParamsPercentage: Double? = null

    @CsvBindByPosition(position = 9)
    var hasStateObjectsWithoutJmhParamsPercentage: Double? = null

    @CsvBindByPosition(position = 10)
    var returnTypeUsedPercentage: Double? = null

    @CsvBindByPosition(position = 11)
    var returnTypeOrBlackholeUsedPercentage: Double? = null

    @CsvBindByPosition(position = 12)
    var nothingSetPercentage: Double? = null

    @CsvBindByPosition(position = 13)
    var benchmarkIsInnerClassPercentage: Double? = null

    // TODO execution time
    constructor()

    constructor(project: String, numberOfBenchmarks: Int?, avgNumberOfBenchmarksPerClass: Double?, avgNumberOfBenchmarksPerFile: Double?, parametrizationUsedPercentage: Double?, groupsUsedPercentage: Double?, blackholeUsedPercentage: Double?, controlUsedPercentage: Double?, hasStateObjectsWithJmhParamsPercentage: Double?, hasStateObjectsWithoutJmhParamsPercentage: Double?, returnTypeUsedPercentage: Double?, returnTypeOrBlackholeUsedPercentage: Double?, nothingSetPercentage: Double?, benchmarkIsInnerClassPercentage: Double?) {
        this.project = project
        this.numberOfBenchmarks = numberOfBenchmarks
        this.avgNumberOfBenchmarksPerClass = avgNumberOfBenchmarksPerClass
        this.avgNumberOfBenchmarksPerFile = avgNumberOfBenchmarksPerFile
        this.parametrizationUsedPercentage = parametrizationUsedPercentage
        this.groupsUsedPercentage = groupsUsedPercentage
        this.blackholeUsedPercentage = blackholeUsedPercentage
        this.controlUsedPercentage = controlUsedPercentage
        this.hasStateObjectsWithJmhParamsPercentage = hasStateObjectsWithJmhParamsPercentage
        this.hasStateObjectsWithoutJmhParamsPercentage = hasStateObjectsWithoutJmhParamsPercentage
        this.returnTypeUsedPercentage = returnTypeUsedPercentage
        this.returnTypeOrBlackholeUsedPercentage = returnTypeOrBlackholeUsedPercentage
        this.nothingSetPercentage = nothingSetPercentage
        this.benchmarkIsInnerClassPercentage = benchmarkIsInnerClassPercentage
    }
}