package com.example.testassignmentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class CurrencyConverterActivity extends AppCompatActivity {
    private Currency currency;
    private TextView headerTV;
    private TextView resultTV;
    private EditText amountET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            formLayout(args);
        }
    }

    private void formLayout(Bundle args) {
        currency = (Currency) args.getSerializable(Currency.class.getSimpleName());
        headerTV = findViewById(R.id.conversion_header_tv);
        resultTV = findViewById(R.id.conversion_result_tv);
        amountET = findViewById(R.id.conversion_et);

        headerTV.setText("Конвертер валют: RUB -> " + currency.getCharCode());
        resultTV.setText(0 + " " + currency.getCharCode());
        amountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertRubToTargetCurrency(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO fix output
            }
        });
    }

    private void convertRubToTargetCurrency(String amount) {
        Double rubsToConvert;
        Double targetCurrencyValue;
        Double targetCurrencyNominal;
        try {
            rubsToConvert = Double.parseDouble(amount);
            targetCurrencyValue = Double.parseDouble(currency.getValue());
            targetCurrencyNominal = Double.parseDouble(currency.getNominal());
            Double rateRub = targetCurrencyValue / targetCurrencyNominal;
            Double result = (double) Math.round(rubsToConvert / rateRub * 1000d) / 1000d;

            resultTV.setText(result + " " + currency.getCharCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}