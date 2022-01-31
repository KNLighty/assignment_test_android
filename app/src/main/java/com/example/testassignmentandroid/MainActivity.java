package com.example.testassignmentandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String URI = "https://www.cbr-xml-daily.ru/daily_json.js";
    private final String FILE_NAME = "data.txt";
    private boolean hasSavedData;
    private boolean isUpdating = true;
    Intent updateService;
    private String response;
    private ListView listView;
    private ArrayList<Currency> currencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNetworkConnection();

        currencies = new ArrayList<>();
        listView = findViewById(R.id.listview);
        OkHttpClient client = new OkHttpClient();
        if (hasSavedData) load();
        else {
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
                                        BuilderCurrency builderCurrency = new BuilderCurrency();

                                        builderCurrency.setProperties(jSonItem);
                                        currencies.add(builderCurrency.getCurrency());
                                    }

                                    CurrencyAdapter adapter = new CurrencyAdapter(
                                            MainActivity.this, R.layout.listview_layout, currencies);
                                    listView.setAdapter(adapter);
                                    save();
                                    update();
                                } catch (JSONException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        restartActivity();
        return super.onOptionsItemSelected(item);
    }

    public void restartActivity() {
        recreate();
    }

    private void update() {
        updateService = new Intent(this, UpdateService.class);
        if (isUpdating) startService(updateService);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(updateService);
    }

    private void checkNetworkConnection() {
        if (!isNetworkConnected()) {
            Log.e(TAG, "Internet is not available");
            TextView tv = findViewById(R.id.error_tv);
            tv.setText(getString(R.string.network_unavailable_error));
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void save() {
        if (currencies != null && !currencies.isEmpty()) {
            FileOutputStream out;
            try {
                out = openFileOutput(FILE_NAME, MODE_PRIVATE);
                out.write(convertToByteArray(currencies));
                out.close();
                System.out.println("Data saved in file");
                hasSavedData = true;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private byte[] convertToByteArray(ArrayList<Currency> list) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(list);
        baos.close();

        return baos.toByteArray();
    }

    private void load() {
            FileInputStream in;
            try {
                in = openFileInput(FILE_NAME);
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                in.close();
                currencies = convertToArrayList(buffer);
                System.out.println("Data loaded from file");
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
    }

    private ArrayList<Currency> convertToArrayList(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        @SuppressWarnings("unchecked")
        ArrayList<Currency> list = (ArrayList<Currency>) ois.readObject();
        ois.close();

        return list;
    }
}