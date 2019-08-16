package com.deepak.task.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by deepakpurohit on 16,August,2019
 */
public class Utils {
    // Method for converting DP/DIP value to pixels
    private static int getPixelsFromDPs(Activity activity, int dps) {

        Resources r = activity.getResources();

        int px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }
    public static void getPixelsFromDPsTextView(Activity activity, int dps, AppCompatTextView textView) {

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getPixelsFromDPs(activity, dps));
    }

    public static void getPixelsFromDPsHeight(Activity activity, int dps, View view) {

        view.getLayoutParams().height = getPixelsFromDPs(activity, dps);

        view.requestLayout();
    }

    public static void getPixelsFromDPsWidth(Activity activity, int dps, View view) {

        view.getLayoutParams().width = getPixelsFromDPs(activity, dps);

        view.requestLayout();
    }

}
