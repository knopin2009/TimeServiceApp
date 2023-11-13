package com.example.timeserviceapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "AppServiceTime";

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted->{
                if (isGranted){
                    startService(new Intent(this, TimeCheckService.class));
                }
                else {
                    Log.e(TAG, "ERROR: PERMISSION DENIED");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tvDate);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy",  Locale.getDefault()).format(new Date());
        tv.setText(currentDate);

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            startService(new Intent(this, TimeCheckService.class));
        }
        else{
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }


    }
}