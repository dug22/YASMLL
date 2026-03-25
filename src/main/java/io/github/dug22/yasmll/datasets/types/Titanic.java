package io.github.dug22.yasmll.datasets.types;

import io.github.dug22.yasmll.datasets.AbstractDataset;

public class Titanic<I, O> extends AbstractDataset<I, O> {

    public Titanic() {
        super("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/titanic.csv");
    }

    public static <I, O> Titanic<I, O> loadDataset() {
        return new Titanic<>();
    }
}
