package ch.uzh.ifi.seal.smr.reconfigure.statistics.ci;

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class CI {
    private List<HistogramItem> histogramList;

    private final String paToolPath = "D:\\pa\\pa.exe";
    private int bootstrapSimulations = 1000;
    private double significanceLevel = 0.01;
    private String statistic = "mean";

    private double lower;
    private double upper;
    private double statisticMetric;

    public CI(List<HistogramItem> histogramList) {
        this.histogramList = histogramList;
    }

    public CI(List<HistogramItem> histogramList, int bootstrapSimulations, double significanceLevel, String statistic) {
        this.histogramList = histogramList;
        this.bootstrapSimulations = bootstrapSimulations;
        this.significanceLevel = significanceLevel;
        this.statistic = statistic;
    }

    public void run() {
        String file = getTmpFile();
        try {
            Process process = Runtime.getRuntime().exec(paToolPath + " -om -bs " + bootstrapSimulations + " -sig " + significanceLevel + " -st " + statistic + " " + file);
            String inputString = IOUtils.toString(process.getInputStream(), Charset.defaultCharset());
            String errorString = IOUtils.toString(process.getErrorStream(), Charset.defaultCharset());
            String output = (inputString + "\n" + errorString).trim();
            String line = getFirstLine(output);
            String[] parts = line.split(";");
            statisticMetric = Double.parseDouble(parts[3]);
            lower = Double.parseDouble(parts[4]);
            upper = Double.parseDouble(parts[5]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFirstLine(String input) {
        String[] lines = input.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (!line.startsWith("#") && !line.isEmpty()) {
                return line;
            }
        }

        return null;
    }

    private String getTmpFile() {
        try {
            File tmpFile = File.createTempFile(UUID.randomUUID().toString(), ".csv");
            FileWriter fw = new FileWriter(tmpFile);

            for (int i = 0; i < histogramList.size(); i++) {
                HistogramItem hi = histogramList.get(i);
                fw.append(";;;;;0;" + hi.getFork() + ";" + hi.getIteration() + ";;;" + hi.getCount() + ";" + hi.getValue() + "\n");
            }

            fw.flush();
            return tmpFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public double getLower() {
        return lower;
    }

    public double getUpper() {
        return upper;
    }

    public double getStatisticMetric() {
        return statisticMetric;
    }
}
