package com.smartfarming.screenrender.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.smartfarming.screenrender.R;


public class CustomFontTextView extends AppCompatTextView {

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomFontTextView(Context context) {
        super(context);

        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFont);
            String fontName = a.getString(R.styleable.CustomFont_fontName);
           /* if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/"
                        + fontName);
                setTypeface(myTypeface);
            }else{
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/"
                        + "mangal.ttf");
                setTypeface(myTypeface);
            }*/
            a.recycle();
        }

        //setMovementMethod(LinkMovementMethod.getInstance());
    }

}
