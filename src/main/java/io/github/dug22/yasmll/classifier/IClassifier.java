package io.github.dug22.yasmll.classifier;

import io.github.dug22.yasmll.data.Dataset;

import java.util.List;

public interface IClassifier<I, O> {

    void train(Dataset<I, O> dataset);

    List<O> test(Dataset<I, O> dataset);

}
