package ch.uzh.ifi.seal.smr.soa.analysis.jmhversionchange

import com.opencsv.bean.CsvBindByPosition

class ResJmhVersionChange {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    var benchmarks: Int = 0

    @CsvBindByPosition(position = 2)
    var numberOfChanges: Int = 0

    @CsvBindByPosition(position = 3)
    var averageChangeTime: Double? = null

    @CsvBindByPosition(position = 4)
    var useJmhSince: Double = 0.0

    constructor()
    constructor(project: String, benchmarks: Int, numberOfChanges: Int, averageChangeTime: Double?, useJmhSince: Double) {
        this.project = project
        this.benchmarks = benchmarks
        this.numberOfChanges = numberOfChanges
        this.averageChangeTime = averageChangeTime
        this.useJmhSince = useJmhSince
    }
}