package io.github.dug22.yasmll.datasets;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.yasmll.data.Dataset;

import java.io.File;

public abstract class AbstractDataset<I, O> {


    private Dataset<I, O> dataset;
    private final File file;
    private final String url;
    private String[] inputs;
    private String output;


    public AbstractDataset(String url) {
        this.url = url;
        this.file = null;
    }

    public AbstractDataset(File file) {
        this.url = null;
        this.file = file;
    }

    private AbstractDataset(String url, File file) {
        this.url = url;
        this.file = file;
    }


    public String[] getInputs() {
        return inputs;
    }

    public AbstractDataset<I, O> setInputs(String... inputs) {
        this.inputs = inputs;
        return this;
    }

    public String getOutput() {
        return output;
    }

    public AbstractDataset<I, O> setOutput(String output) {
        this.output = output;
        return this;
    }


    public AbstractDataset<I, O> build() {

        if (url != null) {
            this.dataset = new Dataset<I, O>()
                    .of(url)
                    .inputs(getInputs())
                    .output(getOutput())
                    .build();
        } else if (file != null) {
            this.dataset = new Dataset<I, O>()
                    .of(file)
                    .inputs(getInputs())
                    .output(getOutput())
                    .build();
        }
        return this;
    }

    public Dataset<I, O> getDataset() {
        return dataset;
    }

    public DataFrame getDataFrame() {
        return dataset.getDataFrame();
    }
}
