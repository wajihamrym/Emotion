package com.example.android.emotion;

import android.os.Bundle;

public class LoveActivity extends EmotionBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super refers to parent class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_love);

        initAnimation();
        populateQuotes("loveQuotes.json");
        displayQuote();
    }
}