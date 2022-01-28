package com.example.testassignmentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String URL_PATH = "https://www.cbr-xml-daily.ru/daily_json.js";
    Button get;
    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = findViewById(R.id.get);
        answer = findViewById(R.id.answer);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getHttpResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getHttpResponse() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(URL_PATH)
                .method("GET", null)
                .addHeader("Cookie", "__cfduid=dc5403bef7ac2ab2cb8ead288d39f653e1586600122")
                .build();
        client.newCall(request).enqueue(new Callback() {
            // В случае неудачного запроса
            @Override
            public void onFailure(Call call, IOException e) {
                // в переменную mMessage получаем ответ
                final String mMessage = e.getMessage().toString();
                // Оборачиваем с помощью Runnable textView, т.к. okhttp вызывается в фоновом режиме, а изменение UI возможно только в UI-потоке
                answer.post(new Runnable() {
                    @Override
                    public void run() {
                        // Выводим текст в textView
                        answer.setText(mMessage);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            answer.setTextColor(getColor(R.color.red));
                        }
                    }
                });

            }

            // В случае успешного запроса
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // в mMessage выводится полученный текст от сервера
                String mMessage = response.body().string();

                try {
                    JSONObject json = new JSONObject(mMessage);
                    JSONObject json2 = json.getJSONObject("Valute");
                    JSONObject json3 = json2.getJSONObject("AUD");

                    final String TranslationE = json3.getString("Value");

                    // Выводим значение
                    answer.post(new Runnable() {
                        @Override
                        public void run() {
                            answer.setText(TranslationE);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}