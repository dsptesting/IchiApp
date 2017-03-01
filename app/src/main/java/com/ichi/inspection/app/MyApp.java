package com.ichi.inspection.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;


public class MyApp extends Application {

    public static MyApp myApp;
    public static Typeface robotoRegularTypeface;

    public static MyApp getInstance(){
        if(myApp == null) myApp = new MyApp();
        return myApp;
    }

    public static Typeface getTypeface(Context context,String customFont){

        if(customFont == context.getString(R.string.reg)){

            if(robotoRegularTypeface == null){

                robotoRegularTypeface = Typeface.createFromAsset(context.getAssets(),customFont);
            }
            return robotoRegularTypeface;
        }

        return Typeface.createFromAsset(context.getAssets(),context.getString(R.string.reg));
    }
}
