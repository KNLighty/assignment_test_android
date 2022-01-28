package com.example.testassignmentandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        viewHolder.idView.setText(currency.getId());
        viewHolder.numcodeView.setText(currency.getNumCode());
        viewHolder.charcodeView.setText(currency.getCharCode());
        viewHolder.nominalView.setText(currency.getNominal());
        viewHolder.nameView.setText(currency.getName());
        viewHolder.valueView.setText(currency.getValue());
        viewHolder.previousView.setText(currency.getPrevious());

        return convertView;
    }

    private class ViewHolder {
        final TextView idView, numcodeView, charcodeView, nominalView;
        final TextView nameView, valueView, previousView;

        public ViewHolder(View view) {
            idView = view.findViewById(R.id.id);
            numcodeView = view.findViewById(R.id.numcode);
            charcodeView = view.findViewById(R.id.charcode);
            nominalView = view.findViewById(R.id.nominal);
            nameView = view.findViewById(R.id.name);
            valueView = view.findViewById(R.id.value);
            previousView = view.findViewById(R.id.previous);
        }
    }
}
