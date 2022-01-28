package com.example.testassignmentandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<Currency> {

    private LayoutInflater inflater;
    private int resource;
    private List<Currency> currencies;

    public CurrencyAdapter(@NonNull Context context, int resource, List<Currency> currencies) {
        super(context, resource, currencies);
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.currencies = currencies;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(this.resource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        final Currency currency = currencies.get(position);
        viewHolder.idView.setText("ID: " + currency.getId());
        viewHolder.numcodeView.setText("NumCode: " + currency.getNumCode());
        viewHolder.charcodeView.setText("CharCode: " + currency.getCharCode());
        viewHolder.nominalView.setText("Nominal: " + currency.getNominal());
        viewHolder.nameView.setText("Name: " + currency.getName());
        viewHolder.valueView.setText("Value: " + currency.getValue());
        viewHolder.previousView.setText("Previous: " + currency.getPrevious());

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertRubToCurrency(currency);
            }
        });

        return convertView;
    }

    private class ViewHolder {

        final TextView idView, numcodeView, charcodeView, nominalView;
        final TextView nameView, valueView, previousView;
        final Button button;
        public ViewHolder(View view) {
            idView = view.findViewById(R.id.id);
            numcodeView = view.findViewById(R.id.numcode);
            charcodeView = view.findViewById(R.id.charcode);
            nominalView = view.findViewById(R.id.nominal);
            nameView = view.findViewById(R.id.name);
            valueView = view.findViewById(R.id.value);
            previousView = view.findViewById(R.id.previous);
            button = view.findViewById(R.id.convert_button);
        }

    }

    private void convertRubToCurrency(Currency currency) {
        Intent intent = new Intent(getContext(), CurrencyConverterActivity.class);
        getContext().startActivity(intent);
    }
}
