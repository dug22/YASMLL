package io.github.dug22.yasmll;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.yasmll.data.TrainTestSplitResult;
import io.github.dug22.yasmll.datasets.types.Iris;
import io.github.dug22.yasmll.metric.MetricType;
import io.github.dug22.yasmll.models.impl.KNNClassifier;
import io.github.dug22.yasmll.models.impl.NaiveBayes;
import io.github.dug22.yasmll.data.Dataset;

import java.util.List;
import java.util.Scanner;

public class IrisDatasetTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter which model you wish to test out!\n"
                           + "Type 1 to test out the KNN classifier model\n"
                           + "Type 2 to test out the Naive Bayes model\n");
        int model = Integer.parseInt(scanner.next());
        if (model == 1) {
            testKNN();
        } else if (model == 2) {
            testNaiveBayes();
        } else {
            System.exit(1);
        }
    }

    private static void testKNN() {
        Iris<Double, String> irisDataset = Iris.loadDataset();
        irisDataset.setInputs("SepalLength", "SepalWidth", "PetalLength", "PetalWidth").setOutput("Name").build();
        Dataset<Double, String> fullDataset = irisDataset.getDataset();
        TrainTestSplitResult<Double, String> split = fullDataset.split(0.8);
        KNNClassifier<Double, String> knn = new KNNClassifier<>(3);
        Dataset<Double, String> trainingDataset = split.trainingDataSet();
        Dataset<Double, String> testDataset = split.testDataset();
        knn.train(trainingDataset);
        List<String> predictions = knn.test(testDataset);
        knn.evaluate(MetricType.ACCURACY, testDataset.getDataPoints(), predictions);
        knn.summary();
    }

    private static void testNaiveBayes() {
        DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/iris.csv");
        IntegerColumn id = dataFrame.stringColumn("Name").apply(name -> {
            if (name.equalsIgnoreCase("Iris-setosa")) return String.valueOf(1);
            else if (name.equalsIgnoreCase("Iris-versicolor")) return String.valueOf(2);
            else if (name.equalsIgnoreCase("Iris-virginica")) return String.valueOf(3);
            else return String.valueOf(0);
        }).asIntegerColumn();
        id.setName("ID");
        dataFrame.addColumns(id);

        Dataset<Double, Integer> fullDataset = new Dataset<Double, Integer>()
                .of(dataFrame)
                .inputs(new String[]{"SepalLength", "SepalWidth", "PetalLength", "PetalWidth"})
                .output("ID")
                .build();

        TrainTestSplitResult<Double, Integer> split = fullDataset.split(0.8);
        NaiveBayes naiveBayes = new NaiveBayes();
        Dataset<Double, Integer> trainingDataset = split.trainingDataSet();
        Dataset<Double, Integer> testDataset = split.testDataset();
        naiveBayes.train(trainingDataset);
        List<Integer> predictions = naiveBayes.test(testDataset);
        naiveBayes.evaluate(MetricType.ACCURACY, testDataset.getDataPoints(), predictions);
        naiveBayes.summary();
        int prediction = naiveBayes.predict(List.of(5.6, 3.8, 1.4, 0.2));
        System.out.println(prediction);
    }
}
