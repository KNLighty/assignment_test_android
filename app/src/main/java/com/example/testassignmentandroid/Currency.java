package com.example.testassignmentandroid;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Currency implements Serializable {
    public String id;
    public String numCode;
    public String charCode;
    public String nominal;
    public String name;
    public String value;
    public String previous;

    public String getId() {
        return id;
    }

    public String getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPrevious() {
        return previous;
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
        result.append("Previous: " + previous);

        return result.toString();
    }
}
