package com.miami.moveforless.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by klim on 28.10.15.
 */
public class TypefaceManager {

    private static TypefaceManager typefaceManager;

    private static final String fontDir = "fonts/";
    private final Typeface helveticaBlack;
    private final Typeface helveticaBlacItalic;
    private final Typeface helveticaBold;
    private final Typeface helveticaBoldItalic;
    private final Typeface helveticaItalic;
    private final Typeface helveticaLight;
    private final Typeface helveticaLightItalic;
    private final Typeface helveticaMedium;
    private final Typeface helveticaRoman;
    private final Typeface helveticaThin;
    private final Typeface helveticaThinItalic;
    private final Typeface helveticaUltraLight;
    private final Typeface helveticaUltraLightItalic;

    public TypefaceManager(Context context) {
        final AssetManager am = context.getAssets();
        helveticaBlack = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-Black.otf");
        helveticaBlacItalic = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-BlackItalic.otf");
        helveticaBold = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-Bold.otf");
        helveticaBoldItalic = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-BoldItalic.otf");
        helveticaItalic = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-Italic.otf");
        helveticaLight = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-Light.otf");
        helveticaLightItalic = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-LightItalic.otf");
        helveticaMedium = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-Medium.otf");
        helveticaRoman = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-Roman.otf");
        helveticaThin = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-Thin.otf");
        helveticaThinItalic = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-ThinItalic.otf");
        helveticaUltraLight = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-UltraLight.otf");
        helveticaUltraLightItalic = Typeface.createFromAsset(am, fontDir + "HelveticaNeueCyr-UltraLightItalic.otf");


    }

    public static TypefaceManager getInstance() {

        return typefaceManager;
    }

    public static TypefaceManager init(Context context) {
        typefaceManager = new TypefaceManager(context);
        return typefaceManager;
    }

    public Typeface getHelveticaBlacItalic() {
        return helveticaBlacItalic;
    }

    public Typeface getHelveticaBlack() {
        return helveticaBlack;
    }

    public Typeface getHelveticaBold() {
        return helveticaBold;
    }

    public Typeface getHelveticaBoldItalic() {
        return helveticaBoldItalic;
    }

    public Typeface getHelveticaItalic() {
        return helveticaItalic;
    }

    public Typeface getHelveticaLight() {
        return helveticaLight;
    }

    public Typeface getHelveticaLightItalic() {
        return helveticaLightItalic;
    }

    public Typeface getHelveticaMedium() {
        return helveticaMedium;
    }

    public Typeface getHelveticaRoman() {
        return helveticaRoman;
    }

    public Typeface getHelveticaThin() {
        return helveticaThin;
    }

    public Typeface getHelveticaThinItalic() {
        return helveticaThinItalic;
    }

    public Typeface getHelveticaUltraLight() {
        return helveticaUltraLight;
    }

    public Typeface getHelveticaUltraLightItalic() {
        return helveticaUltraLightItalic;
    }
}
