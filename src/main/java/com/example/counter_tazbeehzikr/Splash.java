package com.example.counter_tazbeehzikr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById(R.id.splash); //Load Image in ImageView
       Animation animationUtils= new AnimationUtils().loadAnimation(this, R.anim.fade_splash); //settings animation
       imageView.startAnimation(animationUtils); //assigning animation to image
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(Splash.this, MainActivity.class); // loading mainActivity
                startActivity(intent);
                finish(); //finishing splashActivity so user can't go back to splashActivity!
            }
        },1000); //delay of Splashscreen incrementing will increase splash screen visibility
    }
}