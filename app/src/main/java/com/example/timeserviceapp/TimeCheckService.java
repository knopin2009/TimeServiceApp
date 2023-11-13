package com.example.timeserviceapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.NoCopySpan;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.security.Provider;


public class TimeCheckService extends Service {

    NotificationManager notificationManager;

    Notification notification;
    private final Handler handler = new Handler();

    public static final String TAG = "TimeCheckService";
    private boolean firstStart = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: started");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        //super.onCreate();
        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showNotification();
            }
        }, 60 * 1000);*/
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 60*1000);
                showNotification();
            }
        };
        handler.post(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(){

        String channelId = "default_channel_id";
        String channelName = "Default channel";

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT);
            //notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Time check")
                .setContentText("Прошла 1 минута времени")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (!firstStart) {
            notificationManager.notify((int) System.currentTimeMillis(), notification);
        }
        else{
            firstStart = false;
        }
    }


}
