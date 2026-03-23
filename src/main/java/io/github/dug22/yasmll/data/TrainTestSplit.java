package io.github.dug22.yasmll.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainTestSplit {

    public <I, O> List<Dataset<I, O>> split(Dataset<I, O> dataset, double ratio) {
        List<DataPoint<I, O>> dataPoints = dataset.getDataPoints();
        Collections.shuffle(dataPoints);
        int trainSize = (int) (dataset.getDataPoints().size() * ratio);
        Dataset<I, O> trainDataset = new Dataset<>();
        trainDataset.dataPoints = new ArrayList<>(dataPoints.subList(0, trainSize));
        Dataset<I, O> testDataset = new Dataset<>();
        testDataset.dataPoints = new ArrayList<>(dataPoints.subList(trainSize, dataPoints.size()));
        return List.of(trainDataset, testDataset);
    }
}
