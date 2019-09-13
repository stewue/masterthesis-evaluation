package ch.uzh.ifi.seal.smr.soa.analysis.comments

import com.opencsv.bean.CsvBindByPosition

class ResComment {
    @CsvBindByPosition(position = 0)
    var found: Boolean = false

    @CsvBindByPosition(position = 1)
    lateinit var project: String

    @CsvBindByPosition(position = 2)
    lateinit var className: String

    @CsvBindByPosition(position = 3)
    lateinit var benchmarkName: String

    constructor()
    constructor(found: Boolean, project: String, className: String, benchmarkName: String) {
        this.found = found
        this.project = project
        this.className = className
        this.benchmarkName = benchmarkName
    }
}