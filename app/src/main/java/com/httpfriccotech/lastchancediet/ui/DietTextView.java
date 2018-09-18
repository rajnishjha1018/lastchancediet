package com.httpfriccotech.lastchancediet.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.httpfriccotech.lastchancediet.R;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class DietTextView extends AppCompatTextView {


    public DietTextView(Context context) {
        super(context);
        initViewStyle(null);
    }

    public DietTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewStyle(attrs);
    }

    public DietTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewStyle(attrs);
    }

    private void initViewStyle(AttributeSet attributeSet) {
        if (attributeSet == null)
            return;
        TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.customFont);
        int font_type = a.getInt(R.styleable.customFont_s_font_type, 0);
        int font_style = a.getInt(R.styleable.customFont_s_font_style, 0);
        Typeface typeface = FontFactory.getInstance().getTypeFace(font_type);
        if (typeface != null) {
            setTypeface(typeface, font_style);
        }
    }
}
