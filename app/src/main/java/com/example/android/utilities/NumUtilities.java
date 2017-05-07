package com.example.android.utilities;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wajiha on 2017-04-25.
 */

public class NumUtilities {

    //generate index to display a quote at random
    public static int genRandomIndex(int maxVal) {
        Random rnd = new Random();
        return rnd.nextInt(maxVal);
    }
}
