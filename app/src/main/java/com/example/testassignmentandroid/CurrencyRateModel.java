package com.example.testassignmentandroid;

import androidx.annotation.NonNull;

public class CurrencyRateModel {
    private String id;
    private String numCode;
    private String charCode;
    private int nominal;
    private String name;
    private double value;
    private double previous;

    public CurrencyRateModel(String id, String nameCode, String charCode, int nominal, String name, double value, double previous) {
        this.id = id;
        this.numCode = nameCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previous = previous;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getPrevious() {
        return previous;
    }

    public void setPrevious(double previous) {
        this.previous = previous;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder result  = new StringBuilder();
        result.append("ID: " + id + "/n");
        result.append("NumCode: " + numCode + "/n");
        result.append("CharCode: " + charCode + "/n");
        result.append("Nominal: " + nominal + "/n");
        result.append("Name: " + name + "/n");
        result.append("Value: " + value + "/n");
        result.append("Previous: " + previous + "/n");

        return result.toString();
    }
}
