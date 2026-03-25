package io.github.dug22.yasmll.datasets.types;

import io.github.dug22.yasmll.datasets.AbstractDataset;

public class PalmerPenguins<I, O> extends AbstractDataset<I, O> {

    public PalmerPenguins() {
        super("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");
    }

    public static <I, O> PalmerPenguins<I, O> loadDataset() {
        return new PalmerPenguins<>();
    }
}
