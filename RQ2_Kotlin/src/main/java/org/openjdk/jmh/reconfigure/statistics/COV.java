package org.openjdk.jmh.reconfigure.statistics;

import org.openjdk.jmh.reconfigure.helper.HistogramHelper;
import org.openjdk.jmh.reconfigure.helper.HistogramItem;
import org.openjdk.jmh.reconfigure.helper.ListToArray;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.List;

public class COV implements StatisticalEvaluation {
    private List<Double> list;
    private double threshold;

    public COV(List<HistogramItem> list, double threshold) {
        this.list = HistogramHelper.toArray(list);
        this.threshold = threshold;
    }

    @Override
    public double getValue() {
        double[] array = ListToArray.toPrimitive(list);
        DescriptiveStatistics ds = new DescriptiveStatistics(array);
        return ds.getStandardDeviation() / ds.getMean();
    }

    @Override
    public double getThreshold() {
        return threshold;
    }
}
