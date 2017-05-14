package com.ichi.inspection.app.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ichi.inspection.app.MyApp;
import com.ichi.inspection.app.R;

public class CustomEditText extends AppCompatEditText {

    public CustomEditText(Context context) {
        super(context);
        setTypeface(context,null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context,attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context,attrs);
    }

    private void setTypeface(Context context, AttributeSet attributeSet) {
        if (context != null && !isInEditMode() && attributeSet != null) {

            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextView,0,0);
            try{
                String customFont = typedArray.getString(R.styleable.CustomTextView_customFont);
                if(customFont != null && !customFont.isEmpty()){
                    setTypeface(MyApp.getTypeface(context,customFont));
                }
                else{
                    setTypeface(MyApp.getTypeface(context,context.getString(R.string.reg)));
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                typedArray.recycle();
            }
        }
    }

    public void setCustomText(String text) {
        if(text == null){
            text = "-";
        }
        else if(text.length() == 0){
            text = "-";
        }

        setText(text);
    }
}