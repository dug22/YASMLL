package io.github.dug22.yasmll.datasets.types;

import io.github.dug22.yasmll.datasets.AbstractDataset;

public class Iris<I, O> extends AbstractDataset<I, O> {

    public Iris() {
        super("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/iris.csv");
    }

    public static <I, O> Iris<I, O> loadDataset(){
        return new Iris<>();
    }
}
