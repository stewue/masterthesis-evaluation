package ch.uzh.ifi.seal.smr.soa.result.isdefaultvalue

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
    var defaultValuePresentIn121: Int? = null

    @CsvBindByPosition(position = 5)
    var defaultValuePresentIn121Percentage: Double? = null

    constructor()
    constructor(name: String, annotationPresent: Int? = null, isDefaultValue: Int? = null, isDefaultValuePercentage: Double? = null, isDefaultValueIn121: Int? = null, isDefaultValueIn121Percentage: Double? = null) {
        this.name = name
        this.annotationPresent = annotationPresent
        this.defaultValuePresent = isDefaultValue
        this.defaultValuePresentPercentage = isDefaultValuePercentage
        this.defaultValuePresentIn121 = isDefaultValueIn121
        this.defaultValuePresentIn121Percentage = isDefaultValueIn121Percentage
    }
}