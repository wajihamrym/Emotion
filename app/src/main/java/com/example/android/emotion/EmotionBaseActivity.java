package com.example.android.emotion;
import android.app.Activity;
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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okio.Okio;

public abstract class EmotionBaseActivity extends Activity {

    private static final String TAG = "SM_ErrorLog";
    // Arbitrarily determined the appropriate threshold
    // for detecting user swipe on the screen
    private static final int SWIPE_THRESHOLD = 150;
    private static final int ANIMATION_DURATION_MS = 300;

    private Animation in;
    private Animation out;
    // Variables for storing pointer location
    private float fingerDownCoordinates = 0;
    private float fingerUpCoordinates = 0;
    private int previousQuoteIndex = 0;

    protected List<Quote> quoteList;

    /**
     * Reads quotes from a JSON file, deserializes it and populates
     * a list of Quote objects.
     * @param emotionFile JSON filename containing list of quotes
     * @return indication of whether quotes were processed successfully
     */
    public boolean populateQuotes(String emotionFile) {
        InputStream assetInputStream = null;

        try {
            assetInputStream = getAssets().open(emotionFile);
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
     * onTouchEvent determines if a right to left swipe has been performed
     * and changes quote displayed accordingly
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

                // Record the coordinates of the location where the user has started the swipe
                fingerDownCoordinates = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                // Detect the coordinates of the location where the swipe has ended and calculate
                // the distance covered by the swipe. If it exceeds a certain threshold, a new quote
                // will be displayed.
                fingerUpCoordinates = event.getX();
                deltaX = fingerUpCoordinates - fingerDownCoordinates;

                if (Math.abs(deltaX) > SWIPE_THRESHOLD) {
                    // Only allow swipes from right to left
                    if (fingerUpCoordinates < fingerDownCoordinates) {
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
        fingerDownCoordinates = 0;
        fingerUpCoordinates = 0;
    }

    protected void initAnimation() {
        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(ANIMATION_DURATION_MS);

        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(ANIMATION_DURATION_MS);


    }

}


