package io.github.dug22.yasmll;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.yasmll.metric.MetricType;
import io.github.dug22.yasmll.models.impl.KNNClassifier;
import io.github.dug22.yasmll.models.impl.NaiveBayes;
import io.github.dug22.yasmll.data.Dataset;

import java.util.List;

public class IrisDatasetTest {

    public static void main(String[] args) {
        testKNN();
    }

    private static void testKNN() {
        var fullDataset = new Dataset<Double, String>()
                .of("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/iris.csv")
                .inputs("SepalLength", "SepalWidth", "PetalLength", "PetalWidth")
                .output("Name")
                .build();
        var split = fullDataset.split(0.8);
        var knn = new KNNClassifier<Double, String>(3).fit(split.trainingDataSet());
        knn.evaluate(MetricType.ACCURACY, split.testDataset().getDataPoints(), knn.test(split.testDataset()));
        knn.summary();
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

        var fullDataset = new Dataset<Double, Integer>()
                .of(dataFrame)
                .inputs(new String[]{"SepalLength", "SepalWidth", "PetalLength", "PetalWidth"})
                .output("ID")
                .build();

        var split = fullDataset.split(0.8);
        NaiveBayes naiveBayes = new NaiveBayes().fit(split.trainingDataSet());
        naiveBayes.evaluate(MetricType.ACCURACY, split.testDataset().getDataPoints(), naiveBayes.test(split.testDataset()));
        naiveBayes.summary();
        int prediction = naiveBayes.predict(List.of(5.6, 3.8, 1.4, 0.2));
        System.out.println(prediction);
    }
}
