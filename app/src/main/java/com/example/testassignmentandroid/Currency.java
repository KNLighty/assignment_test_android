package com.example.testassignmentandroid;

import androidx.annotation.NonNull;

public class Currency {
    private String id;
    private String numCode;
    private String charCode;
    private String nominal;
    private String name;
    private String value;
    private String previous;

    public Currency(String id, String nameCode, String charCode, String nominal, String name, String value, String previous) {
        this.id = id;
        this.numCode = nameCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previous = previous;
    }

    public String getId() {
        return "ID: " + id;
    }

    public String getNumCode() {
        return "NumCode: " + numCode;
    }

    public String getCharCode() {
        return "CharCode: " + charCode;
    }

    public String getNominal() {
        return "Nominal: " + nominal;
    }

    public String getName() {
        return "Name: " + name;
    }

    public String getValue() {
        return "Value: " + value;
    }

    public String getPrevious() {
        return "Previous: " + previous;
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
