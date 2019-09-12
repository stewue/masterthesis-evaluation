package ch.uzh.ifi.seal.smr.soa.result.annotationpresent

import com.opencsv.bean.CsvBindByPosition

class ResAnnotationPresent {
    @CsvBindByPosition(position = 0)
    lateinit var name: String

    @CsvBindByPosition(position = 1)
    var annotationPresentPercentage: Double? = null

    @CsvBindByPosition(position = 2)
    var onlyClassAnnotationUsed: Int? = null

    @CsvBindByPosition(position = 3)
    var onlyClassAnnotationUsedPercentage: Double? = null

    @CsvBindByPosition(position = 4)
    var onlyMethodAnnotationUsed: Int? = null

    @CsvBindByPosition(position = 5)
    var onlyMethodAnnotationUsedPercentage: Double? = null

    @CsvBindByPosition(position = 6)
    var bothAnnotationsUsedSameValue: Int? = null

    @CsvBindByPosition(position = 7)
    var bothAnnotationsUsedSameValuePercentage: Double? = null

    @CsvBindByPosition(position = 8)
    var bothAnnotationsUsedDifferentValue: Int? = null

    @CsvBindByPosition(position = 9)
    var bothAnnotationsUsedDifferentValuePercentage: Double? = null

    @CsvBindByPosition(position = 10)
    var note: String? = null

    constructor()
    constructor(name: String, annotationPresentPercentage: Double? = null, onlyClassAnnotationUsed: Int? = null, onlyClassAnnotationUsedPercentage: Double? = null, onlyMethodAnnotationUsed: Int? = null, onlyMethodAnnotationUsedPercentage: Double? = null, bothAnnotationsUsedSameValue: Int? = null, bothAnnotationsUsedSameValuePercentage: Double? = null, bothAnnotationsUsedDifferentValue: Int? = null, bothAnnotationsUsedDifferentValuePercentage: Double? = null, note: String? = null) {
        this.name = name
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