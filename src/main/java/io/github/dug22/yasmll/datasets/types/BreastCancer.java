package io.github.dug22.yasmll.datasets.types;

import io.github.dug22.yasmll.datasets.AbstractDataset;

public class BreastCancer<I, O> extends AbstractDataset<I, O> {

    public BreastCancer() {
        super("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/breast_cancer.csv");
    }

    public static <I, O> BreastCancer<I, O> loadDataset(){
        return new BreastCancer<>();
    }
}
