package com.example.exe61;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    
    private static final String APP_STARTUP_TIME_KEY = "APP_STARTUP_TIME";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

        SharedPreferences preferences = getSharedPreferences("exe1_preferences", Activity.MODE_PRIVATE);

        int appStartupTime = preferences.getInt(APP_STARTUP_TIME_KEY, 0);

        int increaseAppStartupTime = appStartupTime + 1;

        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(APP_STARTUP_TIME_KEY, increaseAppStartupTime);
        edit.apply();

        tv.setText(String.valueOf(increaseAppStartupTime));
    }
}