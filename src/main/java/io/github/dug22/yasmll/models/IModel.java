package io.github.dug22.yasmll.models;

import io.github.dug22.yasmll.data.DataPoint;
import io.github.dug22.yasmll.data.Dataset;
import io.github.dug22.yasmll.metric.Accuracy;
import io.github.dug22.yasmll.metric.MetricType;
import io.github.dug22.yasmll.metric.Precision;

import java.util.List;
import java.util.Map;

public interface IModel<I, O> {

    Map<String, String> summaryMap();

    void train(Dataset<I, O> dataset);

    List<O> test(Dataset<I, O> dataset);

    default double evaluate(MetricType type, List<DataPoint<I, O>> dataPoints, List<O> predictions) {
        double score;
        if (type == MetricType.ACCURACY) {
            Accuracy accuracy = new Accuracy();
            score = accuracy.evaluate(dataPoints, predictions);
        } else if (type == MetricType.PRECISION) {
            Precision precision = new Precision();
            score = precision.evaluate(dataPoints, predictions);
        } else {
            throw new IllegalArgumentException("Invalid Metric Type!");
        }

        summaryMap().put("Metric", type.toString());
        summaryMap().put("Score", String.valueOf(score));
        return score;
    }

    void summary();
}
