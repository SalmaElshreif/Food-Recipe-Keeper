package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Introduction extends AppCompatActivity {

//    TextView appName;
    ImageView logo,splashImg;
    LottieAnimationView lottieAnimationView,appName;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction);

        appName = findViewById(R.id.login_app_name);
        splashImg = findViewById(R.id.background);
        lottieAnimationView = findViewById(R.id.lottie);

        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(-120).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1600).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.setRepeatCount(Animation.INFINITE);
        appName.setRepeatCount(Animation.INFINITE);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Introduction.this, CuisineList.class);
                startActivity(intent);
            }
        });

    }

}