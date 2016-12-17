package com.anafund.gameofcardscolors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.Button;
import android.widget.GridLayout;

/**
 * Created by Ana on 11/12/2016.
 */

public class GridMemoryButtons extends Button {


    protected int row;
    protected int column;
    protected int frontDrawableId;

    protected boolean isFliped = false;
    protected boolean isMatched = false;
    protected boolean allMatched = true;

    protected Drawable front;
    protected Drawable back;


    public GridMemoryButtons (Context context, int row, int column, int frontDrawableId) {
        super(context);


        front = AppCompatDrawableManager.get().getDrawable(context, frontDrawableId);
        back = AppCompatDrawableManager.get().getDrawable(context, R.drawable.ic_back);

        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(column));

        tempParams.width = (int) getResources().getDisplayMetrics().density * 80;
        tempParams.height = (int) getResources().getDisplayMetrics().density * 80;

        setLayoutParams(tempParams);

    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }


    public int getFrontDrawableId() {
        return frontDrawableId;
    }

    public void flip() {

        if (isMatched())
            return;

        if (isFliped) {
            setBackground(back);
            isFliped = false;

        } else {
            setBackground(front);
            isFliped = true;
        }
    }

}