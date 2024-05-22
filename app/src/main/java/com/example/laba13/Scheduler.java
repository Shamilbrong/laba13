package com.example.laba13;

import android.os.Handler;

import java.util.TimerTask;


public class Scheduler extends TimerTask{

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private GameActivity activity;



    public void setActivity(GameActivity activity) {
        this.activity = activity;

    }
    @Override
    public void run(){

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    activity.startGame();
                    activity.timer();
                }
            });
        }
    }
}
