package ch.uzh.ifi.seal.smr.reconfigure.statistics.pdf;

import ch.uzh.ifi.seal.smr.reconfigure.helper.ListToArray;
import ch.uzh.ifi.seal.smr.reconfigure.statistics.StatisticalEvaluation;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

public class PDF implements StatisticalEvaluation {
    private final double outlierFactor = 1.5;
    private final int numberOfPoints = 1000;

    private List<Double> before;
    private List<Double> after;
    private double threshold;

    public PDF(List<Double> before, List<Double> after, double threshold) {
        this.before = before;
        this.after = after;
        this.threshold = threshold;
    }

    @Override
    public double getValue() {
        Pair<Double, Double> range = getRange();
        double min = range.getLeft();
        double max = range.getRight();

        List<Double> y = new ArrayList<>();
        double step = (max - min) / (numberOfPoints - 1);
        for (int i = 0; i < numberOfPoints; i++) {
            y.add(min + i * step);
        }

        double[] pdfBefore = ProbabilityDensityFunction.estimate(before, y);
        double[] pdfAfter = ProbabilityDensityFunction.estimate(after, y);

        double kldBefore = KullbackLeiblerDivergence.continuous(pdfBefore, pdfAfter, step);
        double kldAfter = KullbackLeiblerDivergence.continuous(pdfAfter, pdfBefore, step);

        return Math.pow(2.0, -kldBefore) * Math.pow(2.0, -kldAfter);
    }

    @Override
    public double getThreshold() {
        return threshold;
    }

    private Pair<Double, Double> getRange() {
        Pair<Double, Double> rangeBefore = getRangeDistribution(before);
        Pair<Double, Double> rangeAfter = getRangeDistribution(after);

        double min = Math.min(rangeBefore.getLeft(), rangeAfter.getLeft());
        double max = Math.max(rangeBefore.getRight(), rangeAfter.getRight());

        return Pair.of(min, max);
    }

    private Pair<Double, Double> getRangeDistribution(List<Double> list) {
        DescriptiveStatistics ds = new DescriptiveStatistics(ListToArray.toPrimitive(list));
        double q1 = ds.getPercentile(25.0);
        double q3 = ds.getPercentile(75.0);
        double iqr = q3 - q1;

        double max = q3 + outlierFactor * iqr;
        double min = q1 - outlierFactor * iqr;

        if (min < 0) {
            min = 0.0;
        }
        return Pair.of(min, max);
    }
}