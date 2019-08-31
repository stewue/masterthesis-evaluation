package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.bencher.JMHVersion
import com.opencsv.bean.CsvBindByPosition

data class Result(
        @CsvBindByPosition(position = 0)
        val project: String,

        @CsvBindByPosition(position = 1)
        val codeVersion: String?,

        @CsvBindByPosition(position = 2)
        val jmhVersion: JMHVersion?,

        @CsvBindByPosition(position = 3)
        val javaTarget: String?,

        @CsvBindByPosition(position = 4)
        val javaSource: String?,

        @CsvBindByPosition(position = 5)
        val benchmarkName: String,

        @CsvBindByPosition(position = 6)
        val warmupIterations: Int?,

        @CsvBindByPosition(position = 7)
        val warmupIterationsClass: Int?,

        @CsvBindByPosition(position = 8)
        val warmupIterationsMethod: Int?,

        @CsvBindByPosition(position = 9)
        val warmupTime: Long?,

        @CsvBindByPosition(position = 10)
        val warmupTimeClass: Long?,

        @CsvBindByPosition(position = 11)
        val warmupTimeMethod: Long?,

        @CsvBindByPosition(position = 12)
        val warmupTimeStatus: Status,

        @CsvBindByPosition(position = 13)
        val warmupTimeStatusClass: Status,

        @CsvBindByPosition(position = 14)
        val warmupTimeStatusMethod: Status,

        @CsvBindByPosition(position = 15)
        val measurementIterations: Int?,

        @CsvBindByPosition(position = 16)
        val measurementIterationsClass: Int?,

        @CsvBindByPosition(position = 17)
        val measurementIterationsMethod: Int?,

        @CsvBindByPosition(position = 18)
        val measurementTime: Long?,

        @CsvBindByPosition(position = 19)
        val measurementTimeClass: Long?,

        @CsvBindByPosition(position = 20)
        val measurementTimeMethod: Long?,

        @CsvBindByPosition(position = 21)
        val measurementTimeStatus: Status,

        @CsvBindByPosition(position = 22)
        val measurementTimeStatusClass: Status,

        @CsvBindByPosition(position = 23)
        val measurementTimeStatusMethod: Status,

        @CsvBindByPosition(position = 24)
        val forks: Int?,

        @CsvBindByPosition(position = 25)
        val forksClass: Int?,

        @CsvBindByPosition(position = 26)
        val forksMethod: Int?,

        @CsvBindByPosition(position = 27)
        val warmupForks: Int?,

        @CsvBindByPosition(position = 28)
        val warmupForksClass: Int?,

        @CsvBindByPosition(position = 29)
        val warmupForksMethod: Int?,

        @CsvBindByPosition(position = 30)
        val modeIsThroughput: Boolean?,

        @CsvBindByPosition(position = 31)
        val modeIsThroughputClass: Boolean?,

        @CsvBindByPosition(position = 32)
        val modeIsThroughputMethod: Boolean?,

        @CsvBindByPosition(position = 33)
        val modeIsAverageTime: Boolean?,

        @CsvBindByPosition(position = 34)
        val modeIsAverageTimeClass: Boolean?,

        @CsvBindByPosition(position = 35)
        val modeIsAverageTimeMethod: Boolean?,

        @CsvBindByPosition(position = 36)
        val modeIsSampleTime: Boolean?,

        @CsvBindByPosition(position = 37)
        val modeIsSampleTimeClass: Boolean?,

        @CsvBindByPosition(position = 38)
        val modeIsSampleTimeMethod: Boolean?,

        @CsvBindByPosition(position = 39)
        val modeIsSingleShotTime: Boolean?,

        @CsvBindByPosition(position = 40)
        val modeIsSingleShotTimeClass: Boolean?,

        @CsvBindByPosition(position = 41)
        val modeIsSingleShotTimeMethod: Boolean?,

        @CsvBindByPosition(position = 42)
        val paramString: String?,

        @CsvBindByPosition(position = 43)
        val paramCount: Int,

        @CsvBindByPosition(position = 44)
        val hasBlackhole: Boolean,

        @CsvBindByPosition(position = 45)
        val hasControl: Boolean,

        @CsvBindByPosition(position = 46)
        val hasBenchmarkParams: Boolean,

        @CsvBindByPosition(position = 47)
        val hasIterationParams: Boolean,

        @CsvBindByPosition(position = 48)
        val hasThreadParams: Boolean,

        @CsvBindByPosition(position = 49)
        val jmhParamString: String?,

        @CsvBindByPosition(position = 50)
        val jmhParamPairs: String?,

        @CsvBindByPosition(position = 51)
        val jmhParamCount: Int,

        @CsvBindByPosition(position = 52)
        val jmhParamFromStateObjectCount: Int,

        @CsvBindByPosition(position = 53)
        val returnType: String?,

        @CsvBindByPosition(position = 54)
        val partOfGroup: Boolean,

        @CsvBindByPosition(position = 55)
        val methodHash: String
)