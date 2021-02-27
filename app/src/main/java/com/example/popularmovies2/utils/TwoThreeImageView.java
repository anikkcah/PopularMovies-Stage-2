package com.example.popularmovies2.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import static com.example.popularmovies2.utils.Constants.THREE;
import static com.example.popularmovies2.utils.Constants.TWO;

/**
 * The ThreeTwoImageView class is responsible for making ImageView 3:2 aspect ratio.
 * The ThreeTwoImageView is used for movie backdrop image in the activity_detail.xml.
 */
public class TwoThreeImageView extends AppCompatImageView {

/**
 * Creates a TwoThreeImageView
 *
 * @param context used to talk to the UI and app resources
 */
public TwoThreeImageView(Context context){ super(context);}

public TwoThreeImageView(Context context, AttributeSet attrs){ super(context, attrs); }

public TwoThreeImageView(Context context, AttributeSet attrs, int defStyle){
    super(context, attrs, defStyle);
}


    /**
     * This method measures the view and its content to determine the measured width and the measured
     * height, which will make 2:3 aspect ratio.
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent
     * @param heightMeasureSpec vertical space requirements as imposed by th parent
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int twoThreeHeight = MeasureSpec.getSize(widthMeasureSpec) * THREE / TWO;
        int twoThreeHeightSpec =
                MeasureSpec.makeMeasureSpec(twoThreeHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, twoThreeHeightSpec);
    }



}
