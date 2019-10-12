package ch.uzh.ifi.seal.smr.soa.analysis.samplecommits

import com.opencsv.bean.CsvBindByPosition

class SampleCommit {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    var commitTime: Int = 0

    @CsvBindByPosition(position = 2)
    lateinit var commitId: String

    constructor()
    constructor(project: String, commitTime: Int, commitId: String) {
        this.project = project
        this.commitTime = commitTime
        this.commitId = commitId
    }
}