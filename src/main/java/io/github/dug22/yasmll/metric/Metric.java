package io.github.dug22.yasmll.metric;

import io.github.dug22.yasmll.data.DataPoint;

import java.util.List;

public  interface Metric {

   default <I, O> double evaluate(List<DataPoint<I, O>> dataPoints, List<O> predictions){
        return evaluate(dataPoints, predictions, true);
    }

   <I, O> double evaluate(List<DataPoint<I, O>> dataPoints, List<O> predictions, boolean verbose);
}
