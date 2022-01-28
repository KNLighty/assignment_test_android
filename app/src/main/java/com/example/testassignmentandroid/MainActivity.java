package com.example.testassignmentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private static final String URI = "https://www.cbr-xml-daily.ru/daily_json.js";
    private String response;
    private ListView listView;
    ArrayList<Currency> currencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencies = new ArrayList<>();
        listView = findViewById(R.id.listview);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URI)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainActivity.this.response = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, String> data;
                            try {
                                JSONObject reader = new JSONObject(MainActivity.this.response);
                                JSONObject rbcGETRates = new JSONObject(reader.toString());
                                JSONObject jSonValute = rbcGETRates.getJSONObject("Valute");
                                Iterator<String> arrayKey = jSonValute.keys();

                                while (arrayKey.hasNext()) {
                                    String key = arrayKey.next();
                                    JSONObject jSonItem = jSonValute.getJSONObject(key);
                                    data = formDataMap(jSonItem);
                                    currencies.add(new Currency(data.get("id"), data.get("numcode"),
                                            data.get("charcode"), data.get("nominal"),
                                            data.get("name"), data.get("value"), data.get("previous"))
                                    );
                                }

                                CurrencyAdapter adapter = new CurrencyAdapter(MainActivity.this, R.layout.listview_layout, currencies);
                                listView.setAdapter(adapter);
                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private HashMap<String, String> formDataMap(JSONObject jSonItem) throws JSONException {
        HashMap<String, String> data = new HashMap<>();

        data.put("id", jSonItem.getString("ID"));
        data.put("numcode", jSonItem.getString("NumCode"));
        data.put("charcode", jSonItem.getString("CharCode"));
        data.put("nominal", jSonItem.getString("Nominal"));
        data.put("name", jSonItem.getString("Name"));
        data.put("value", jSonItem.getString("Value"));
        data.put("previous", jSonItem.getString("Previous"));

        return data;
    }
}