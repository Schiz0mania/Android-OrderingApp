package com.example.xyz.orderingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;
import com.example.xyz.orderingapp.view.CTextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;


public class WelcomeAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);


        CTextView cTextView = (CTextView) findViewById(R.id.textView);
        cTextView.setText("    欢迎使用点餐app",  330);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        startMainActivity();
    }

    private void startMainActivity(){

        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(WelcomeAct.this, MainActivity.class);
                startActivity(mainIntent);
                WelcomeAct.this.finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(delayTask,4000);//延时两秒执行 run 里面的操作
    }
}
