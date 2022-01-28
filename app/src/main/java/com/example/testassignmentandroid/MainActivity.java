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
    ArrayList<HashMap<String,String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        listOfCurrency  = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listview);
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
                            HashMap<String,String> data;
                            try {
                                JSONObject reader = new JSONObject(myResponse);
                                JSONObject rbcGETRates = new JSONObject(reader.toString());
                                JSONObject jSonValute = rbcGETRates.getJSONObject("Valute");
                                Iterator<String> arrayKey = jSonValute.keys();

                                while (arrayKey.hasNext()) {
                                    String key = arrayKey.next();
                                    JSONObject jSonItem = jSonValute.getJSONObject(key);
                                    data = new HashMap<>();

                                    data.put("id", jSonItem.getString("ID"));
                                    data.put("numcode", jSonItem.getString("NumCode"));
                                    data.put("charcode", jSonItem.getString("CharCode"));
                                    data.put("nominal", jSonItem.getString("Nominal"));
                                    data.put("name", jSonItem.getString("Name"));
                                    data.put("value", jSonItem.getString("Value"));
                                    data.put("previous", jSonItem.getString("Previous"));

                                    arrayList.add(data);

                                    ListAdapter adapter = new SimpleAdapter(
                                            MainActivity.this,
                                            arrayList,
                                            R.layout.listview_layout,
                                            new String[]{"id", "numcode", "charcode", "nominal", "name", "value", "previous"},
                                            new int[]{R.id.id, R.id.numcode, R.id.charcode, R.id.nominal, R.id.name, R.id.value, R.id.previous});
                                    lv.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "ОШИБКА");
                            }
                        }
                    });
                }
            }
        });
    }
}