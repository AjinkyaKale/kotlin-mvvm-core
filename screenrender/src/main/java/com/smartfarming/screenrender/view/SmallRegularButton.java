package com.smartfarming.screenrender.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class SmallRegularButton extends AppCompatButton {
    public SmallRegularButton(Context context) {
        super(context);
    }

    public SmallRegularButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SmallRegularButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {

        TypedArray a = null;
        try {
            // a = getContext().obtainStyledAttributes(attrs, R.styleable.smallButton);
        } finally {
            if (a != null) {
                a.recycle(); // ensure this is always called
            }
        }
        final SmallRegularButton text = SmallRegularButton.this;
        text.setTextSize(14);

    }

}
