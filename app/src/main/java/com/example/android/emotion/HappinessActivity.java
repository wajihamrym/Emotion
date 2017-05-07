package com.example.android.emotion;

import android.os.Bundle;

/**
 * Created by wajiha on 2017-05-02.
 */

public class HappinessActivity extends EmotionBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super refers to parent class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_happiness);

        initTextSwitchers(this);
        loadJsonFromAsset(this.getApplicationContext(), "happinessQuotes.json");

        displayQuote();
    }
}
