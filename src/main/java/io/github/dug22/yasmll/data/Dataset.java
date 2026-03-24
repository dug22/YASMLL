package io.github.dug22.yasmll.data;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.io.csv.CsvReadingProperties;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dataset<I, O> implements Serializable {

    private DataFrame dataFrame;
    public List<DataPoint<I, O>> dataPoints;
    public String[] inputColumns;
    public String outputColumn;

    public Dataset() {
        this.dataPoints = new ArrayList<>();
    }

    public Dataset<I, O> of(Column<?>... columns) {
        this.dataFrame = DataFrame.create(columns);
        return this;
    }

    public Dataset<I, O> of(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
        return this;
    }

    public Dataset<I, O> of(File file) {
        dataFrame = DataFrame.read().csv(file);
        return this;
    }

    public Dataset<I, O> of(String url) {
        dataFrame = DataFrame.read().csv(url);
        return this;
    }

    public Dataset<I, O> of(CsvReadingProperties properties) {
        dataFrame = DataFrame.read().csv(properties);
        return this;
    }

    public Dataset<I, O> inputs(String... inputColumns) {
        this.inputColumns = inputColumns;
        return this;
    }

    public Dataset<I, O> output(String outputColumn) {
        this.outputColumn = outputColumn;
        return this;
    }

    public Dataset<I, O> build() {
        if (inputColumns == null || outputColumn == null) {
            throw new IllegalArgumentException("Input & Output columns must be defined!");
        }

        if (dataFrame != null) {
            for (int i = 0; i < dataFrame.getRows().size(); i++) {
                List<I> inputs = new ArrayList<>();
                for (String column : inputColumns) {
                    I value = dataFrame.getRow(i).get(column);
                    inputs.add(value);
                }

                O output = dataFrame.getRow(i).get(outputColumn);
                DataPoint<I, O> newInstance = new DataPoint<>(inputs, output);
                dataPoints.add(newInstance);
            }
        }

        return this;
    }

    public DataFrame getDataFrame() {
        return dataFrame;
    }

    public List<DataPoint<I, O>> getDataPoints() {
        return dataPoints;
    }

    public TrainTestSplitResult<I, O> split(double ratio) {
        return new TrainTestSplit().split(this, ratio);
    }
}
