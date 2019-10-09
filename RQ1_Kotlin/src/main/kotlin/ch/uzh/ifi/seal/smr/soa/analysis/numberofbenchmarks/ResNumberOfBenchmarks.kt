package ch.uzh.ifi.seal.smr.soa.analysis.numberofbenchmarks

import com.opencsv.bean.CsvBindByPosition

class ResNumberOfBenchmarks {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    var benchmarks: Int = 0

    @CsvBindByPosition(position = 2)
    var useJmhSince: Double = 0.0

    constructor()
    constructor(project: String, benchmarks: Int, useJmhSince: Double) {
        this.project = project
        this.benchmarks = benchmarks
        this.useJmhSince = useJmhSince
    }
}