package ch.uzh.ifi.seal.smr.soa.evaluation

import com.opencsv.bean.CsvBindByPosition

data class Result(
        @CsvBindByPosition(position = 0)
        val project: String,

        @CsvBindByPosition(position = 1)
        val jmhVersion: String,

        @CsvBindByPosition(position = 2)
        val benchmarkName: String,

        @CsvBindByPosition(position = 3)
        val warmupIterations: Int,

        @CsvBindByPosition(position = 4)
        val warmupIterationsIsDefault: Boolean,

        @CsvBindByPosition(position = 5)
        val warmupTime: Long,

        @CsvBindByPosition(position = 6)
        val warmupTimeIsDefault: Boolean,

        @CsvBindByPosition(position = 7)
        val measurementIterations: Int,

        @CsvBindByPosition(position = 8)
        val measurementIterationsIsDefault: Boolean,

        @CsvBindByPosition(position = 9)
        val measurementTime: Long,

        @CsvBindByPosition(position = 10)
        val measurementTimeIsDefault: Boolean,

        @CsvBindByPosition(position = 11)
        val forks: Int,

        @CsvBindByPosition(position = 12)
        val forksIsDefault: Boolean,

        @CsvBindByPosition(position = 13)
        val warmupForks: Int,

        @CsvBindByPosition(position = 14)
        val warmupForksIsDefault: Boolean,

        @CsvBindByPosition(position = 15)
        val modeIsThroughput: Boolean,

        @CsvBindByPosition(position = 16)
        val modeIsAverageTime: Boolean,

        @CsvBindByPosition(position = 17)
        val modeIsSampleTime: Boolean,

        @CsvBindByPosition(position = 18)
        val modeIsSingleShotTime: Boolean,

        @CsvBindByPosition(position = 19)
        val modeIsDefault: Boolean,

        @CsvBindByPosition(position = 20)
        val methodhasParams: Boolean
)