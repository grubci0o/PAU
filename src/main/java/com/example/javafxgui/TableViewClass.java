package com.example.javafxgui;

import java.util.ArrayList;

public class TableViewClass {
    @Export
    String name;

    @Export
    ArrayList<Double> grades;
    @Export
    Double average;

    public TableViewClass(String name, ArrayList<Double> grades, Double average) {
        this.name = name;
        this.grades = grades;
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Double> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Double> grades) {
        this.grades = grades;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
