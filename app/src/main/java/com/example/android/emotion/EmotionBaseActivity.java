package com.example.android.emotion;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.android.utilities.NumUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.author;
import static android.R.attr.x;
import static java.sql.Types.FLOAT;

/**
 * Created by wajiha on 2017-04-18.
 */



public abstract class EmotionBaseActivity extends Activity {


    //protected Context context;
    //protected Activity activity;
    protected TextSwitcher authorTextSwitcher;
    protected TextSwitcher quoteTextSwitcher;
    String json = null;
    final ArrayList<Quote> quoteList = new ArrayList<Quote>();

    //variables for storing pointer location
    float x1 = 0;
    float x2 = 0;
    /**
     * Reads JSON file of quotes/authors and stores them into an ArrayList
     *
     * @param emotion type
     */
    public void loadJsonFromAsset(Context context, String emotion) {
        String json = null;
        //creates a variable-length string
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(emotion);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            json = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray mainNode = new JSONArray(json);

            for (int i = 0; i < mainNode.length(); i++) {

                JSONObject quote = mainNode.getJSONObject(i);
                //appending a new row to arraylist
                Quote temp = new Quote();
                temp.setAuthor(quote.getString("author"));
                temp.setQuote(quote.getString("quote"));
                quoteList.add(temp);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Displays the quote and its author in the textViews of the current emotion activity.
     * Quotes are displayed in a random sequence each time the activity starts.
     *
     */
    public void displayQuote() {

        //initialize variables for author and quote textviews
        //TextView quote = (TextView) activity.findViewById(R.id.quote_text_view);
        //TextView author = (TextView) activity.findViewById(R.id.author_text_view);

        int rndIndex = NumUtilities.genRandomIndex(quoteList.size());
        quoteTextSwitcher.setText(quoteList.get(rndIndex).getQuote());
        authorTextSwitcher.setText("~" + quoteList.get(rndIndex).getAuthor() + "~");
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
                        Toast.makeText(this, "It worked.", Toast.LENGTH_SHORT).show();
                        displayQuote();
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

    protected void initTextSwitchers(final EmotionBaseActivity activity) {
        //create a new quote and author TextView for TextSwitcher to use
        authorTextSwitcher = (TextSwitcher) this.findViewById(R.id.author_text_switcher);
        authorTextSwitcher.setFactory(new ViewSwitcher.ViewFactory(){
            @Override
            public View makeView() {
                return new TextView(activity);
            }

        });

        quoteTextSwitcher = (TextSwitcher) this.findViewById(R.id.quote_text_switcher);
        quoteTextSwitcher.setFactory(new ViewSwitcher.ViewFactory(){
            @Override
            public View makeView() {
                return new TextView(activity);
            }

        });

        //declare and initialize animations for TextSwitcher
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        //set animation type for TextSwitcher
        authorTextSwitcher.setInAnimation(in);
        authorTextSwitcher.setOutAnimation(out);

        quoteTextSwitcher.setInAnimation(in);
        quoteTextSwitcher.setOutAnimation(out);
    }


}


