package com.example.android.emotion;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.android.utilities.NumUtilities;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okio.Okio;

/**
 * Created by wajiha on 2017-04-18.
 */



public abstract class EmotionBaseActivity extends Activity {

    Animation in;
    Animation out;
    protected List<Quote> quoteList = new ArrayList<Quote>();
    private static final String TAG = "SM_ErrorLog";
    //variables for storing pointer location
    float x1 = 0;
    float x2 = 0;

    private int previousQuoteIndex = 0;


    public boolean populateQuotes(Context context, String emotion) {
        InputStream assetInputStream = null;

        try{
            //assetInputStream = getApplicationContext().getAssets().open(emotion);
            assetInputStream = getAssets().open(emotion);
        }
        catch(IOException e) {
            Log.e(TAG, "Could not open quotes JSON file", e);
            return false;
        }
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Quote.class);
        JsonAdapter<List> adapter = moshi.adapter(type);
        try {
            quoteList = adapter.fromJson(Okio.buffer(Okio.source(assetInputStream)));
            return true;
        }
        catch(IOException e) {
            Log.e(TAG, "Could not parse JSON file", e);
            return false;
        }
    }


    /**
     * Displays the quote and its author in the textViews of the current emotion activity.
     * Quotes are displayed in a random sequence each time the activity starts.
     *
     */
    public void displayQuote() {
        final TextView quoteTextView = (TextView) this.findViewById(R.id.quote_text_view);
        final TextView authorTextView = (TextView) this.findViewById(R.id.author_text_view);

        int rndIndex;
        do {
            rndIndex = NumUtilities.genRandomIndex(quoteList.size());
        } while (rndIndex == previousQuoteIndex);
        previousQuoteIndex = rndIndex;

        quoteTextView.setText(quoteList.get(rndIndex).getQuote());
        authorTextView.setText("~" + quoteList.get(rndIndex).getAuthor() + "~");

    }


    /**
     * onTouchEvent determines if a right to left swipe has been performed and changes quote displayed accordingly
     *
     * @param event
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        float deltaX = 0; ;


        switch (action) {

            case MotionEvent.ACTION_DOWN:
                resetCoordinates();

                x1 = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                deltaX = x2 - x1;

                if (Math.abs(deltaX)>150) {
                    if (x2 < x1) {
                        final TextView quoteTextView = (TextView) this.findViewById(R.id.quote_text_view);
                        final TextView authorTextView = (TextView) this.findViewById(R.id.author_text_view);

                        quoteTextView.startAnimation(out);
                        authorTextView.startAnimation(out);

                        out.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                displayQuote();
                                quoteTextView.startAnimation(in);
                                authorTextView.startAnimation(in);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                }

                resetCoordinates();
                break;

        }

    return super.onTouchEvent(event);
    }

    private void resetCoordinates() {
        x1 = 0;
        x2 = 0;
    }

    protected void initAnimation() {
        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(300);

        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);


    }

}


