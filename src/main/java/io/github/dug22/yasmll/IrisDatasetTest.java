package io.github.dug22.yasmll;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.yasmll.classifier.KNNClassifier;
import io.github.dug22.yasmll.classifier.NaiveBayes;
import io.github.dug22.yasmll.data.Dataset;
import io.github.dug22.yasmll.metric.Accuracy;

import java.util.List;

public class IrisDatasetTest {

    public static void main(String[] args) {
        testKNN();
        testNaiveBayes();
    }

    private static void testKNN() {

        Dataset<Double, String> fullDataset = new Dataset<Double, String>()
                .createFromDataFrame("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/iris.csv")
                .setInputColumns(new String[]{"SepalLength", "SepalWidth", "PetalLength", "PetalWidth"})
                .setOutputColumn("Name")
                .build();

        List<Dataset<Double, String>> trainTestSplit = fullDataset.split(0.8);
        Dataset<Double, String> trainingSet = trainTestSplit.get(0);
        Dataset<Double, String> testSet = trainTestSplit.get(1);
        KNNClassifier<Double, String> knn = new KNNClassifier<>(3);
        knn.train(trainingSet);
        List<String> predictions = knn.test(testSet);
        Accuracy accuracyEvaluator = new Accuracy();
        double score = accuracyEvaluator.evaluate(testSet.getDataPoints(), predictions);

        System.out.println("--- KNN Classification Results ---");
        System.out.printf("Total Test Samples: %d\n", testSet.getDataPoints().size());
        System.out.printf("Model Accuracy: %.2f%%\n", score * 100);


        String prediction = knn.predict(List.of(5.6, 3.8, 1.4, 0.2));
        System.out.println(prediction);
    }

    private static void testNaiveBayes() {
        DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/iris.csv");
        IntegerColumn id = dataFrame.stringColumn("Name").apply(name -> {
            if (name.equalsIgnoreCase("Iris-setosa")) {
                return String.valueOf(1);
            } else if (name.equalsIgnoreCase("Iris-versicolor")) {
                return String.valueOf(2);
            } else if (name.equalsIgnoreCase("Iris-virginica")) {
                return String.valueOf(3);
            } else {
                return String.valueOf(0);
            }
        }).asIntegerColumn();

        id.setName("ID");

        dataFrame.addColumns(id);

        dataFrame.head();

        Dataset<Double, Integer> fullDataset = new Dataset<Double, Integer>()
                .createFromDataFrame(dataFrame)
                .setInputColumns(new String[]{"SepalLength", "SepalWidth", "PetalLength", "PetalWidth"})
                .setOutputColumn("ID")
                .build();

        List<Dataset<Double, Integer>> trainTestSplit = fullDataset.split(0.8);
        Dataset<Double, Integer> trainingSet = trainTestSplit.get(0);
        Dataset<Double, Integer> testSet = trainTestSplit.get(1);
        NaiveBayes naiveBayes = new NaiveBayes();
        naiveBayes.train(trainingSet);
        List<Integer> predictions = naiveBayes.test(testSet);
        Accuracy accuracyEvaluator = new Accuracy();
        double score = accuracyEvaluator.evaluate(testSet.getDataPoints(), predictions);

        System.out.println("--- Naive Bayes Classification Results ---");
        System.out.printf("Total Test Samples: %d\n", testSet.getDataPoints().size());
        System.out.printf("Model Accuracy: %.2f%%\n", score * 100);


        int prediction = naiveBayes.predict(List.of(5.6, 3.8, 1.4, 0.2));
        System.out.println(prediction);
    }
}
