package ch.uzh.ifi.seal.smr.soa.result.jmhversionchange

import com.opencsv.bean.CsvBindByPosition

class ResJmhVersionChange {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    var benchmarks: Int = 0

    @CsvBindByPosition(position = 2)
    var numberOfChanges: Int = 0

    @CsvBindByPosition(position = 3)
    var averageChangeTime: Double = 0.0

    constructor()
    constructor(project: String, benchmarks: Int, numberOfChanges: Int, averageChangeTime: Double) {
        this.project = project
        this.benchmarks = benchmarks
        this.numberOfChanges = numberOfChanges
        this.averageChangeTime = averageChangeTime
    }

}