package com.miami.moveforless.customviews.Helvetica;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.CustomFontStyle;
import com.miami.moveforless.utils.TypefaceManager;

/**
 * Created by klim on 28.10.15.
 */
public class HelveticaButton extends Button {
    public HelveticaButton(Context context) {
        super(context);
    }

    public HelveticaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        defineTypeface(context, attrs);
    }

    public HelveticaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defineTypeface(context, attrs);
    }

    private void defineTypeface(Context context, AttributeSet attrs){
        if(isInEditMode()){
            return;
        }

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomEditText,
                0, 0);

        try {
            int font = a.getInteger(R.styleable.CustomEditText_customFont, 0);
            setTypefaceBasedOnInt(font);
        }
        finally {
            a.recycle();
        }
    }

    public void setFontStyle(CustomFontStyle style){
        setTypefaceBasedOnInt(style.ordinal());
    }

    private void setTypefaceBasedOnInt(int fontEnum){
        Typeface typeface = null;

        switch (fontEnum){
            default:
            case 0:
                typeface = TypefaceManager.getInstance().getHelveticaBlack();
                break;
            case 1:
                typeface = TypefaceManager.getInstance().getHelveticaBlacItalic();
                break;
            case 2:
                typeface = TypefaceManager.getInstance().getHelveticaBold();
                break;
            case 3:
                typeface = TypefaceManager.getInstance().getHelveticaBoldItalic();
                break;
            case 4:
                typeface = TypefaceManager.getInstance().getHelveticaItalic();
                break;
            case 5:
                typeface = TypefaceManager.getInstance().getHelveticaLight();
                break;
            case 6:
                typeface = TypefaceManager.getInstance().getHelveticaLightItalic();
                break;
            case 7:
                typeface = TypefaceManager.getInstance().getHelveticaMedium();
                break;
            case 8:
                typeface = TypefaceManager.getInstance().getHelveticaRoman();
                break;
            case 9:
                typeface = TypefaceManager.getInstance().getHelveticaThin();
                break;
            case 10:
                typeface = TypefaceManager.getInstance().getHelveticaThinItalic();
                break;
            case 11:
                typeface = TypefaceManager.getInstance().getHelveticaUltraLight();
                break;
            case 12:
                typeface = TypefaceManager.getInstance().getHelveticaUltraLightItalic();
                break;

        }
        setTypeface(typeface);
    }

}