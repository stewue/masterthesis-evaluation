package ch.uzh.ifi.seal.smr.reconfigure.statistics.ci;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;

public class OpenCSVWriter {
    public static void write(File outputFile, List<Input> input){
        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }

            Writer writer = Files.newBufferedWriter(outputFile.toPath());

            StatefulBeanToCsvBuilder<Input> builder = new StatefulBeanToCsvBuilder<Input>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .withMappingStrategy(new CustomMappingStrategy(Input.class));

            StatefulBeanToCsv<Input> beanToCsv = builder.build();
            beanToCsv.write(input);

            writer.close();
        }catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e){
            e.printStackTrace();
        }
    }
}
