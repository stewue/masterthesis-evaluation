package ch.uzh.ifi.seal.smr.soa.analysis.samplecommits

import com.opencsv.bean.CsvBindByPosition

class ResSampleCommit {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    var commits: Int = 0

    @CsvBindByPosition(position = 2)
    var useJmhSince: Double = 0.0

    constructor(project: String, benchmarks: Int, useJmhSince: Double) {
        this.project = project
        this.commits = benchmarks
        this.useJmhSince = useJmhSince
    }
}