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
        val measurementIterations: Int?,

        @CsvBindByPosition(position = 13)
        val measurementIterationsClass: Int?,

        @CsvBindByPosition(position = 14)
        val measurementIterationsMethod: Int?,

        @CsvBindByPosition(position = 15)
        val measurementTime: Long?,

        @CsvBindByPosition(position = 16)
        val measurementTimeClass: Long?,

        @CsvBindByPosition(position = 17)
        val measurementTimeMethod: Long?,

        @CsvBindByPosition(position = 18)
        val forks: Int?,

        @CsvBindByPosition(position = 19)
        val forksClass: Int?,

        @CsvBindByPosition(position = 20)
        val forksMethod: Int?,

        @CsvBindByPosition(position = 21)
        val warmupForks: Int?,

        @CsvBindByPosition(position = 22)
        val warmupForksClass: Int?,

        @CsvBindByPosition(position = 23)
        val warmupForksMethod: Int?,

        @CsvBindByPosition(position = 24)
        val modeIsThroughput: Boolean?,

        @CsvBindByPosition(position = 25)
        val modeIsThroughputClass: Boolean?,

        @CsvBindByPosition(position = 26)
        val modeIsThroughputMethod: Boolean?,

        @CsvBindByPosition(position = 27)
        val modeIsAverageTime: Boolean?,

        @CsvBindByPosition(position = 28)
        val modeIsAverageTimeClass: Boolean?,

        @CsvBindByPosition(position = 29)
        val modeIsAverageTimeMethod: Boolean?,

        @CsvBindByPosition(position = 30)
        val modeIsSampleTime: Boolean?,

        @CsvBindByPosition(position = 31)
        val modeIsSampleTimeClass: Boolean?,

        @CsvBindByPosition(position = 32)
        val modeIsSampleTimeMethod: Boolean?,

        @CsvBindByPosition(position = 33)
        val modeIsSingleShotTime: Boolean?,

        @CsvBindByPosition(position = 34)
        val modeIsSingleShotTimeClass: Boolean?,

        @CsvBindByPosition(position = 35)
        val modeIsSingleShotTimeMethod: Boolean?,

        @CsvBindByPosition(position = 36)
        val paramString: String?,

        @CsvBindByPosition(position = 37)
        val paramCount: Int,

        @CsvBindByPosition(position = 38)
        val hasBlackhole: Boolean,

        @CsvBindByPosition(position = 39)
        val hasControl: Boolean,

        @CsvBindByPosition(position = 40)
        val hasBenchmarkParams: Boolean,

        @CsvBindByPosition(position = 41)
        val hasIterationParams: Boolean,

        @CsvBindByPosition(position = 42)
        val hasThreadParams: Boolean,

        @CsvBindByPosition(position = 43)
        val jmhParamString: String?,

        @CsvBindByPosition(position = 44)
        val jmhParamCount: Int,

        @CsvBindByPosition(position = 45)
        val jmhParamFromStateObjectCount: Int,

        @CsvBindByPosition(position = 46)
        val partOfGroup: Boolean,

        @CsvBindByPosition(position = 47)
        val methodHash: String
)