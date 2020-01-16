package ch.uzh.ifi.seal.smr.soa.analysis.annotationpresent

import com.opencsv.bean.CsvBindByPosition

class ResAnnotationPresent {
    @CsvBindByPosition(position = 0)
    lateinit var name: String

    @CsvBindByPosition(position = 1)
    var annotationPresent: Int? = null

    @CsvBindByPosition(position = 2)
    var annotationPresentPercentage: Double? = null

    @CsvBindByPosition(position = 3)
    var onlyClassAnnotationUsed: Int? = null

    @CsvBindByPosition(position = 4)
    var onlyClassAnnotationUsedPercentage: Double? = null

    @CsvBindByPosition(position = 5)
    var onlyMethodAnnotationUsed: Int? = null

    @CsvBindByPosition(position = 6)
    var onlyMethodAnnotationUsedPercentage: Double? = null

    @CsvBindByPosition(position = 7)
    var bothAnnotationsUsedSameValue: Int? = null

    @CsvBindByPosition(position = 8)
    var bothAnnotationsUsedSameValuePercentage: Double? = null

    @CsvBindByPosition(position = 9)
    var bothAnnotationsUsedDifferentValue: Int? = null

    @CsvBindByPosition(position = 10)
    var bothAnnotationsUsedDifferentValuePercentage: Double? = null

    @CsvBindByPosition(position = 11)
    var note: String? = null

    constructor()
    constructor(name: String, annotationPresent: Int? = null, annotationPresentPercentage: Double? = null, onlyClassAnnotationUsed: Int? = null, onlyClassAnnotationUsedPercentage: Double? = null, onlyMethodAnnotationUsed: Int? = null, onlyMethodAnnotationUsedPercentage: Double? = null, bothAnnotationsUsedSameValue: Int? = null, bothAnnotationsUsedSameValuePercentage: Double? = null, bothAnnotationsUsedDifferentValue: Int? = null, bothAnnotationsUsedDifferentValuePercentage: Double? = null, note: String? = null) {
        this.name = name
        this.annotationPresent = annotationPresent
        this.annotationPresentPercentage = annotationPresentPercentage
        this.onlyClassAnnotationUsed = onlyClassAnnotationUsed
        this.onlyClassAnnotationUsedPercentage = onlyClassAnnotationUsedPercentage
        this.onlyMethodAnnotationUsed = onlyMethodAnnotationUsed
        this.onlyMethodAnnotationUsedPercentage = onlyMethodAnnotationUsedPercentage
        this.bothAnnotationsUsedSameValue = bothAnnotationsUsedSameValue
        this.bothAnnotationsUsedSameValuePercentage = bothAnnotationsUsedSameValuePercentage
        this.bothAnnotationsUsedDifferentValue = bothAnnotationsUsedDifferentValue
        this.bothAnnotationsUsedDifferentValuePercentage = bothAnnotationsUsedDifferentValuePercentage
        this.note = note
    }

}