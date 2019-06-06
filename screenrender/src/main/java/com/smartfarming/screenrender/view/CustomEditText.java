package com.smartfarming.screenrender.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputEditText;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

public class CustomEditText extends TextInputEditText {

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomEditText(Context context) {
        super(context);

        init(null);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = null;
        try {
            // a = getContext().obtainStyledAttributes(attrs, R.styleable.editText);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }

        setPadding(5, 0, 0, 0);
        setMovementMethod(LinkMovementMethod.getInstance());
    }


}
