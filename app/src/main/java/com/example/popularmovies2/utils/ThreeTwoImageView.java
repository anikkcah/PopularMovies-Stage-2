package com.example.popularmovies2.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import static com.example.popularmovies2.utils.Constants.THREE;
import static com.example.popularmovies2.utils.Constants.TWO;

public class ThreeTwoImageView extends AppCompatImageView {
    /**
     * Creates ThreeTwoImageView
     *
     * @param context used to talk to UI and app resources
     */
    public ThreeTwoImageView(Context context){ super(context); }

    public ThreeTwoImageView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public ThreeTwoImageView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    /**
     * This method measures the view and its content to determine the measured
     * height, which will make 3:2 aspect ratio.
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent
     * @param heightMeasureSpec vertical space requirements as imposed by the parent
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int threeTwoHeight = View.MeasureSpec.getSize(widthMeasureSpec) * TWO / THREE;
        int threeTwoHeightSpec =
                View.MeasureSpec.makeMeasureSpec(threeTwoHeight, View.MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, threeTwoHeightSpec);
    }



}
