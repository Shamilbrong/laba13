package com.example.laba13;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Launch",  this.getLocalClassName() + " launched");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        SharedPreferences preferences = getSharedPreferences("record", MODE_PRIVATE);
        int bestStreak = preferences.getInt("bestStreak", 0);
        TextView bestStreakTextView = (TextView) findViewById(R.id.best_streak);
        bestStreakTextView.setText("Лучшая серия: " + bestStreak);
    }
}