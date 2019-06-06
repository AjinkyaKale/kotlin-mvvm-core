package com.smartfarming.screenrender.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.smartfarming.screenrender.R;


public class
LargeTextView extends AppCompatTextView {

    public LargeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public LargeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public LargeTextView(Context context) {
        super(context);

        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFont);

        }

        setTextSize(22f);

        //setMovementMethod(LinkMovementMethod.getInstance());
    }

}
