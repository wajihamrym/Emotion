package com.example.android.emotion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SadnessActivity extends EmotionBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_sadness);

        initAnimation();
        loadJsonFromAsset(this.getApplicationContext(), "sadnessQuotes.json");

        displayQuote();
    }
}
