package ch.uzh.ifi.seal.smr.soa.bigquery

import com.opencsv.bean.CsvBindByPosition

class Row {
    @CsvBindByPosition(position = 0)
    var project: String = ""

    @CsvBindByPosition(position = 1)
    var forked: Boolean = false

    @CsvBindByPosition(position = 2)
    var watchers: Int = 0

    @CsvBindByPosition(position = 3)
    var stars: Int = 0

    @CsvBindByPosition(position = 4)
    var forks: Int = 0

    @CsvBindByPosition(position = 5)
    var subscribers: Int = 0

    @CsvBindByPosition(position = 6)
    var exist: Boolean = true

    constructor()

    constructor(project: String, forked: Boolean, watchers: Int, stars: Int, forks: Int, subscribers: Int) {
        this.project = project
        this.forked = forked
        this.watchers = watchers
        this.stars = stars
        this.forks = forks
        this.subscribers = subscribers
    }
}