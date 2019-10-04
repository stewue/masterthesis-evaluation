package ch.uzh.ifi.seal.smr.soa.analysis.hash

import com.opencsv.bean.CsvBindByPosition

class ResHashChanged {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    lateinit var benchmarkName: String

    @CsvBindByPosition(position = 2)
    var numberOfChanges: Int = 0

    constructor()
    constructor(project: String, benchmarkName: String, numberOfChanges: Int) {
        this.project = project
        this.benchmarkName = benchmarkName
        this.numberOfChanges = numberOfChanges
    }
}