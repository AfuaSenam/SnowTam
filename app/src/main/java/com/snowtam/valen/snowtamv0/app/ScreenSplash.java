package com.snowtam.valen.snowtamv0.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.snowtam.valen.snowtamv0.R;

public class ScreenSplash extends Activity {

    private final static int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(ScreenSplash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME);
    }
}
