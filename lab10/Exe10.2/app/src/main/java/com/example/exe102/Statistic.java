package com.example.exe102;

import java.io.Serializable;

public class Statistic implements Serializable {
    Label label;
    String data;

    public Statistic() {
    }

    public Statistic(Label aCase, String data) {
        this.label = aCase;
        this.data = data;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label aCase) {
        this.label = aCase;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "label=" + label +
                ", data='" + data + '\'' +
                '}';
    }
}
