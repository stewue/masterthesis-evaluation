package ch.uzh.ifi.seal.smr.soa.analysis.javaversionchange

import com.opencsv.bean.CsvBindByPosition

class ResJavaVersionChange {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    var numberOfChanges: Int = 0

    constructor()
    constructor(project: String, numberOfChanges: Int) {
        this.project = project
        this.numberOfChanges = numberOfChanges
    }

}