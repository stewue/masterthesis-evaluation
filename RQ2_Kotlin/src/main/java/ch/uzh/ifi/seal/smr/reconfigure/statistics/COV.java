package ch.uzh.ifi.seal.smr.reconfigure.statistics;

import ch.uzh.ifi.seal.smr.reconfigure.helper.ListToArray;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.List;

public class COV {
    public static double of(double [] input){
        DescriptiveStatistics ds = new DescriptiveStatistics(input);
        return ds.getStandardDeviation() / ds.getMean();
    }

    public static double of(List<Double> input){
        return of(ListToArray.toPrimitive(input));
    }
}
