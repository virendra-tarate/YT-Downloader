package com.anonymous.v.yt_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //making full screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //running a thread for changing activity

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(splash_screen.this,MainActivity.class);

                //starting activity
                startActivity(intent1);

                //removing first activity from stack

                finish();

            }
        },4000);

        //rest of code ...

        //removing action bar
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

    }
}