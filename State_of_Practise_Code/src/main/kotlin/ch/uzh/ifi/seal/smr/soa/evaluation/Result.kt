package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.bencher.JMHVersion
import com.opencsv.bean.CsvBindByPosition

data class Result(
        @CsvBindByPosition(position = 0)
        val project: String,

        @CsvBindByPosition(position = 1)
        val commitId: String?,

        @CsvBindByPosition(position = 2)
        val commitTime: Int?,

        @CsvBindByPosition(position = 3)
        val jmhVersion: JMHVersion?,

        @CsvBindByPosition(position = 4)
        val javaTarget: String?,

        @CsvBindByPosition(position = 5)
        val javaSource: String?,

        @CsvBindByPosition(position = 6)
        val benchmarkName: String,

        @CsvBindByPosition(position = 7)
        val warmupIterations: Int?,

        @CsvBindByPosition(position = 8)
        val warmupIterationsClass: Int?,

        @CsvBindByPosition(position = 9)
        val warmupIterationsMethod: Int?,

        @CsvBindByPosition(position = 10)
        val warmupTime: Long?,

        @CsvBindByPosition(position = 11)
        val warmupTimeClass: Long?,

        @CsvBindByPosition(position = 12)
        val warmupTimeMethod: Long?,

        @CsvBindByPosition(position = 13)
        val warmupTimeStatus: Status,

        @CsvBindByPosition(position = 14)
        val warmupTimeStatusClass: Status,

        @CsvBindByPosition(position = 15)
        val warmupTimeStatusMethod: Status,

        @CsvBindByPosition(position = 16)
        val measurementIterations: Int?,

        @CsvBindByPosition(position = 17)
        val measurementIterationsClass: Int?,

        @CsvBindByPosition(position = 18)
        val measurementIterationsMethod: Int?,

        @CsvBindByPosition(position = 19)
        val measurementTime: Long?,

        @CsvBindByPosition(position = 20)
        val measurementTimeClass: Long?,

        @CsvBindByPosition(position = 21)
        val measurementTimeMethod: Long?,

        @CsvBindByPosition(position = 22)
        val measurementTimeStatus: Status,

        @CsvBindByPosition(position = 23)
        val measurementTimeStatusClass: Status,

        @CsvBindByPosition(position = 24)
        val measurementTimeStatusMethod: Status,

        @CsvBindByPosition(position = 25)
        val forks: Int?,

        @CsvBindByPosition(position = 26)
        val forksClass: Int?,

        @CsvBindByPosition(position = 27)
        val forksMethod: Int?,

        @CsvBindByPosition(position = 28)
        val warmupForks: Int?,

        @CsvBindByPosition(position = 29)
        val warmupForksClass: Int?,

        @CsvBindByPosition(position = 30)
        val warmupForksMethod: Int?,

        @CsvBindByPosition(position = 31)
        val modeIsThroughput: Boolean?,

        @CsvBindByPosition(position = 32)
        val modeIsThroughputClass: Boolean?,

        @CsvBindByPosition(position = 33)
        val modeIsThroughputMethod: Boolean?,

        @CsvBindByPosition(position = 34)
        val modeIsAverageTime: Boolean?,

        @CsvBindByPosition(position = 35)
        val modeIsAverageTimeClass: Boolean?,

        @CsvBindByPosition(position = 36)
        val modeIsAverageTimeMethod: Boolean?,

        @CsvBindByPosition(position = 37)
        val modeIsSampleTime: Boolean?,

        @CsvBindByPosition(position = 38)
        val modeIsSampleTimeClass: Boolean?,

        @CsvBindByPosition(position = 39)
        val modeIsSampleTimeMethod: Boolean?,

        @CsvBindByPosition(position = 40)
        val modeIsSingleShotTime: Boolean?,

        @CsvBindByPosition(position = 41)
        val modeIsSingleShotTimeClass: Boolean?,

        @CsvBindByPosition(position = 42)
        val modeIsSingleShotTimeMethod: Boolean?,

        @CsvBindByPosition(position = 43)
        val paramString: String?,

        @CsvBindByPosition(position = 44)
        val paramCount: Int,

        @CsvBindByPosition(position = 45)
        val hasBlackhole: Boolean,

        @CsvBindByPosition(position = 46)
        val hasControl: Boolean,

        @CsvBindByPosition(position = 47)
        val hasBenchmarkParams: Boolean,

        @CsvBindByPosition(position = 48)
        val hasIterationParams: Boolean,

        @CsvBindByPosition(position = 49)
        val hasThreadParams: Boolean,

        @CsvBindByPosition(position = 50)
        val jmhParamString: String?,

        @CsvBindByPosition(position = 51)
        val jmhParamPairs: String?,

        @CsvBindByPosition(position = 52)
        val jmhParamCount: Int,

        @CsvBindByPosition(position = 53)
        val jmhParamFromStateObjectCount: Int,

        @CsvBindByPosition(position = 54)
        val returnType: String?,

        @CsvBindByPosition(position = 55)
        val partOfGroup: Boolean,

        @CsvBindByPosition(position = 56)
        val methodHash: String
)