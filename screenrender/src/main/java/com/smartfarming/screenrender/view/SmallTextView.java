package com.smartfarming.screenrender.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.smartfarming.screenrender.R;


public class SmallTextView extends AppCompatTextView {

    public SmallTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public SmallTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public SmallTextView(Context context) {
        super(context);

        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFont);

        }

        setTextSize(14f);

        //setMovementMethod(LinkMovementMethod.getInstance());
    }

}
