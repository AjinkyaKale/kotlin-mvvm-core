package com.smartfarming.screenrender.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import com.smartfarming.screenrender.R;


public class CustomFontEditText extends TextInputEditText {

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomFontEditText(Context context) {
        super(context);

        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFont);
            String fontName = a.getString(R.styleable.CustomFont_fontName);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"
                        + fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }

        setMovementMethod(LinkMovementMethod.getInstance());
    }

}
