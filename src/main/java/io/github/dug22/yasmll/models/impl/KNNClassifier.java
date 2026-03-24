package io.github.dug22.yasmll.models.impl;

import io.github.dug22.yasmll.data.DataPoint;
import io.github.dug22.yasmll.data.Dataset;
import io.github.dug22.yasmll.models.IModel;

import java.io.Serializable;
import java.util.*;

public class KNNClassifier<I, O> implements IModel<I, O>, Serializable {

    private final Map<String, String> summaryMap;
    private Dataset<I, O> trainingDataset;
    private final int k;

    public KNNClassifier (int k) {
        this.k = k;
        this.summaryMap = new HashMap<>();
    }

    @Override
    public Map<String, String> summaryMap() {
        return summaryMap;
    }

    @Override
    public KNNClassifier<I, O> fit(Dataset<I, O> trainingDataset) {
        this.trainingDataset = trainingDataset;
        return this;
    }

    @Override
    public List<O> test(Dataset<I, O> dataset) {
        List<O> predictions = new ArrayList<>();
        dataset.getDataPoints().forEach(dataPoint -> predictions.add(predict(dataPoint)));
        summaryMap().put("Test Size", String.valueOf(predictions.size()));
        return predictions;
    }

    public O predict(List<I> input) {
        return predict(new DataPoint<>(input, null));
    }

    public O predict(DataPoint<I, O> dataPoint) {
        PriorityQueue<Neighbor<O>> nearestNeighbors = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
        for (DataPoint<I, O> trainingDataPoint : trainingDataset.getDataPoints()) {
            double euclideanDistance = calculateEuclideanDistance(dataPoint.input(), trainingDataPoint.input());
            nearestNeighbors.add(new Neighbor<>(trainingDataPoint.output(), euclideanDistance));
        }

        Map<O, Integer> outputCounts = new HashMap<>();
        for (int i = 0; i < k && !nearestNeighbors.isEmpty(); i++) {
            O output = nearestNeighbors.poll().output;
            outputCounts.put(output, outputCounts.getOrDefault(output, 0) + 1);
        }

        Map.Entry<O, Integer> maxEntry = null;
        for (Map.Entry<O, Integer> entry : outputCounts.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;

            }
        }

        return maxEntry.getKey();
    }

    public void summary(){
        System.out.println("--- KNN Classification Results ---");
        System.out.printf("Total Test Samples: %s\n", summaryMap().get("Test Size"));
        System.out.printf("Model " +  summaryMap().get("Metric") + " : %.2f%%\n", Double.parseDouble(summaryMap().get("Score")) * 100);
    }

    private double calculateEuclideanDistance(List<I> inputA, List<I> inputB) {
        double sum = 0.0;
        int index = 0;
        while (index < inputA.size()) {
            double valueA = ((Number) inputA.get(index)).doubleValue();
            double valueB = ((Number) inputB.get(index)).doubleValue();
            sum += Math.pow(valueA - valueB, 2);
            index++;
        }

        return Math.sqrt(sum);
    }

    private record Neighbor<O>(O output, double distance) {
    }

}