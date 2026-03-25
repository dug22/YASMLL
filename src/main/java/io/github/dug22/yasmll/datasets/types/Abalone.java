package io.github.dug22.yasmll.datasets.types;

import io.github.dug22.yasmll.datasets.AbstractDataset;

public class Abalone<I, O> extends AbstractDataset<I, O> {

    public Abalone() {
        super("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/abalone.csv");
    }

    public static <I, O> Abalone<I, O> loadDataset(){
        return new Abalone<>();
    }
}
