package ch.uzh.ifi.seal.smr.soa.analysis.isdefaultvalue

import com.opencsv.bean.CsvBindByPosition

class ResIsDefault {
    @CsvBindByPosition(position = 0)
    lateinit var name: String

    @CsvBindByPosition(position = 1)
    var annotationPresent: Int? = null

    @CsvBindByPosition(position = 2)
    var defaultValuePresent: Int? = null

    @CsvBindByPosition(position = 3)
    var defaultValuePresentPercentage: Double? = null

    @CsvBindByPosition(position = 4)
    var annotationPresentPre121: Int? = null

    @CsvBindByPosition(position = 5)
    var defaultValuePresentPre121: Int? = null

    @CsvBindByPosition(position = 6)
    var defaultValuePresentPre121Percentage: Double? = null

    @CsvBindByPosition(position = 7)
    var annotationPresentPost121: Int? = null

    @CsvBindByPosition(position = 8)
    var defaultValuePresentPost121: Int? = null

    @CsvBindByPosition(position = 9)
    var defaultValuePresentPost121Percentage: Double? = null

    constructor()
    constructor(name: String, annotationPresent: Int? = null, defaultValuePresent: Int? = null, defaultValuePresentPercentage: Double? = null, annotationPresentPre121: Int? = null, defaultValuePresentPre121: Int? = null, defaultValuePresentPre121Percentage: Double? = null, annotationPresentPost121: Int? = null, defaultValuePresentPost121: Int? = null, defaultValuePresentPost121Percentage: Double? = null) {
        this.name = name
        this.annotationPresent = annotationPresent
        this.defaultValuePresent = defaultValuePresent
        this.defaultValuePresentPercentage = defaultValuePresentPercentage
        this.annotationPresentPre121 = annotationPresentPre121
        this.defaultValuePresentPre121 = defaultValuePresentPre121
        this.defaultValuePresentPre121Percentage = defaultValuePresentPre121Percentage
        this.annotationPresentPost121 = annotationPresentPost121
        this.defaultValuePresentPost121 = defaultValuePresentPost121
        this.defaultValuePresentPost121Percentage = defaultValuePresentPost121Percentage
    }



}