package com.example.laba13;

import android.os.Handler;
import android.widget.ProgressBar;

import java.util.TimerTask;


public class ProgressBarScheduler extends TimerTask{
    ProgressBar progressBar;
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private GameActivity activity;
    public long startTime;
    public void setActivity(GameActivity activity) {
        this.activity = activity;
    }
    @Override
    public void run(){

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress((int) ((System.currentTimeMillis() - startTime) % 500) / 5);
                    activity.progressBar();
                }
            });
        }
    }
}
