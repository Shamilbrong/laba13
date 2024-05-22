package com.example.laba13;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.view.GestureDetectorCompat;

import java.util.Random;
import java.util.Timer;


public class GameActivity extends Activity {

    private GestureDetectorCompat mDetector;
    private Handler handler = new Handler();

    private ImageView leftAnswerImageView;
    private ImageView rightAnswerImageView;

    private Random random = new Random();
    ProgressBar progressBar;
    long startTime;
    Scheduler scheduler;
    Timer timer;
    int count, bestStreak;
    boolean correctAnswer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("Запуск",  this.getLocalClassName() + " запущена");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        leftAnswerImageView = findViewById(R.id.answer1);
        rightAnswerImageView = findViewById(R.id.answer2);
        progressBar = findViewById(R.id.progress_bar);

        startGame();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        startTime = System.currentTimeMillis();

        timer();
        progressBar();

        View myView = findViewById(R.id.main);

        myView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
            float diffX = event2.getX()-event1.getX();
            if (diffX < 0){
                onSwipeLeft();
            } else if (diffX > 0){
                onSwipeRight();
            }
            return true;
        }
    }

    public void onSwipeLeft() {
        progressBar.setProgress(0);
        startTime = System.currentTimeMillis();
        checkAnswer(true);
        timer.cancel();
        timer();
        startGame();
    }

    public void onSwipeRight() {
        progressBar.setProgress(0);
        startTime = System.currentTimeMillis();
        checkAnswer(false);
        timer.cancel();
        timer();
        startGame();
    }

    public void progressBar(){
        Timer timer = new Timer();
        ProgressBarScheduler progressBarScheduler = new ProgressBarScheduler();
        progressBarScheduler.setHandler(handler);
        progressBarScheduler.setActivity(this);
        progressBarScheduler.startTime = startTime;
        progressBarScheduler.progressBar = findViewById(R.id.progress_bar);

        timer.schedule(progressBarScheduler, 100);
    }
    public void timer(){
        timer = new Timer();
        scheduler = new Scheduler();
        scheduler.setHandler(handler);
        scheduler.setActivity(this);

        timer.schedule(scheduler, 500);
        Log.i("Timer", "Таймер запущен");
    }


    public void startGame() {

        boolean leftIsCorrect = random.nextBoolean();
        correctAnswer = leftIsCorrect;
        if (leftIsCorrect) {
            leftAnswerImageView.setImageResource(R.drawable.circle);
            rightAnswerImageView.setImageResource(R.drawable.square);
        } else {
            leftAnswerImageView.setImageResource(R.drawable.square);
            rightAnswerImageView.setImageResource(R.drawable.circle);
        }

    }

    public void checkAnswer(boolean answer) {
        if (answer == correctAnswer) {
            count++;
        } else {
            if (count > bestStreak) {
                bestStreak = getBestStreak();
                saveRecord();
            }
            count = 0;
        }
        ((TextView) findViewById(R.id.count)).setText(String.valueOf(count));
    }

    private void saveRecord() {
        SharedPreferences preferences = getSharedPreferences("record", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        bestStreak = count;
        editor.putInt("bestStreak", bestStreak);
        editor.apply();
    }

    private int getBestStreak() {
        SharedPreferences preferences = getSharedPreferences("record", MODE_PRIVATE);
        return preferences.getInt("bestStreak", 0);
    }
}