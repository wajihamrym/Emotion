package com.example.android.emotion;

import android.os.Bundle;

public class HappinessActivity extends EmotionBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super refers to parent class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_happiness);

        initAnimation();
        populateQuotes("happinessQuotes.json");
        displayQuote();
    }
}
