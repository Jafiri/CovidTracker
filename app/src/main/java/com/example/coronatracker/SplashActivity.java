package com.example.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.coronatracker.OTPLogin.SendOPTActivity;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        lottie = (LottieAnimationView) findViewById(R.id.animation_view);
        appname = (TextView) findViewById(R.id.appname);

        appname.animate().translationY(1400).setDuration(2700).setStartDelay(3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SendOPTActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}