package com.example.android.emotion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** called when user clicks love emoticon*/
    public void showLove(View view) {
        Intent intent = new Intent(this, LoveActivity.class);
        startActivity(intent);
    }

    public void showHappiness(View view) {
        Intent intent = new Intent(this, HappinessActivity.class);
        startActivity(intent);
    }

    public void showSadness(View view) {
        Intent intent = new Intent(this, SadnessActivity.class);
        startActivity(intent);
    }
}

