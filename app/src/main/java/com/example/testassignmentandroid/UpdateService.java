package com.example.testassignmentandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.TimeUnit;

public class UpdateService extends Service {
    private static final long TIME_BEFORE_UPDATE_IN_SECONDS = 300;
    private boolean stop;

    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        stop = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent dialogIntent = new Intent(UpdateService.this, MainActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                while (true) {
                    if (stop) break;
                    try {
                        TimeUnit.SECONDS.sleep(TIME_BEFORE_UPDATE_IN_SECONDS);
                        System.out.println("Restarting activity");
                        startActivity(dialogIntent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}