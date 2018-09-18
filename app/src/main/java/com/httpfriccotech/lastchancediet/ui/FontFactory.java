package com.httpfriccotech.lastchancediet.ui;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by pradeep on 17-11-2016.
 */

public class FontFactory {

    private static FontFactory fontFactoryInstance;
    private HashMap<String, Typeface> fontMap;
    private Context context;

    private FontFactory() {

    }

    public static FontFactory getInstance() {
        if (fontFactoryInstance == null) {
            fontFactoryInstance = new FontFactory();
        }
        return fontFactoryInstance;
    }

    public static void setUpFontFactory(Context context) {
        getInstance().context = context;
        getInstance().fontMap = new HashMap<>();
    }

    public Typeface getTypeFace(int fontType) {
        String fontName = "MontserratAlternates-Regular.otf";
        switch (fontType) {
            case 1:
                fontName = "MontserratAlternates-Regular.otf";
                break;
            case 2:
                fontName = "MontserratAlternates-Regular.otf";
                break;
            case 3:
                fontName = "MontserratAlternates-Regular.ttf";
                break;
            case 4:
                fontName = "MontserratAlternates-Regular.ttf";
                break;
            case 5:
                fontName = "MontserratAlternates-Regular.ttf";
                break;
            case 6:
                fontName = "MontserratAlternates-Regular.ttf";
                break;
            default:
                fontName = "MontserratAlternates-Regular.ttf";
                break;
        }
        if (fontMap == null)
            fontMap = new HashMap<>();
        if (!fontMap.containsKey(fontName)) {
            if (context != null) {
                fontMap.put(fontName, Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName));
            }
        }
        return fontMap.get(fontName);
    }
}