package com.example.testassignmentandroid;

import org.json.JSONException;
import org.json.JSONObject;

public class BuilderCurrency {
    private Currency currency;

    public BuilderCurrency() {
        reset();
    }

    private void reset() {
        currency = new Currency();
    }

    public void setProperties(JSONObject currencyData) throws JSONException {
        setId(currencyData.getString("ID"));
        setNumCode(currencyData.getString("NumCode"));
        setCharCode(currencyData.getString("CharCode"));
        setNominal(currencyData.getString("Nominal"));
        setName(currencyData.getString("Name"));
        setValue(currencyData.getString("Value"));
        setPrevious(currencyData.getString("Previous"));
    }

    private void setId(String id) {
        currency.id = id;
    }

    private void setNumCode(String numCode) {
        currency.numCode = numCode;
    }

    private void setCharCode(String charCode) {
        currency.charCode = charCode;
    }

    private void setNominal(String nominal) {
        currency.nominal = nominal;
    }

    private void setName(String name) {
        currency.name = name;
    }

    private void setValue(String value) {
        currency.value = value;
    }

    private void setPrevious(String previous) {
        currency.previous = previous;
    }

    public Currency getCurrency() {
        return currency;
    }
}
