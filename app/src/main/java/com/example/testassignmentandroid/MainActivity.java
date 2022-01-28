package com.example.testassignmentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<CurrencyRateModel> listOfCurrency = new ArrayList<>();
    private String URI = "https://www.cbr-xml-daily.ru/daily_json.js";
    private TextView tv;
    private String myResponse;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfCurrency  = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listview);
        tv = (TextView) findViewById(R.id.textViewTest);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URI)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject reader = new JSONObject(myResponse);
                                tv.setText("valute: START");
                                JSONObject rbcGETRates = new JSONObject(reader.toString());
                                JSONObject jSonValute = rbcGETRates.getJSONObject("Valute");
                                Iterator<String> arrayKey = jSonValute.keys();

                                while (arrayKey.hasNext()) {
                                    String key = arrayKey.next();
                                    JSONObject jSonItem = jSonValute.getJSONObject(key);
                                    CurrencyRateModel currencyitems = new CurrencyRateModel(
                                            jSonItem.getString("ID"),
                                            jSonItem.getString("NumCode"),
                                            jSonItem.getString("CharCode"),
                                            jSonItem.getInt("Nominal"),
                                            jSonItem.getString("Name"),
                                            jSonItem.getDouble("Value"),
                                            jSonItem.getDouble("Previous")
                                    );
                                    listOfCurrency.add(currencyitems);
                                }
                                for (CurrencyRateModel m : listOfCurrency) {
                                    Log.d(TAG, "CurrencyRateModel:" + m);
                                }
                            } catch (JSONException e) {
                                tv.setText("ОШИБКА");
                            }
                        }
                    });
                }
            }
        });
    }
}