package com.example.android.emotion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class LoveActivity extends EmotionBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super refers to parent class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_love);

        initTextSwitchers(this);
        loadJsonFromAsset(this.getApplicationContext(), "loveQuotes.json");

        displayQuote();
    }
}