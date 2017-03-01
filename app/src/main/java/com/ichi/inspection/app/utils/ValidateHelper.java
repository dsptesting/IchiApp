package com.ichi.inspection.app.utils;

import android.widget.EditText;

public class ValidateHelper {

    public static boolean validateEditText(EditText editText){

        if(editText == null) return false;
        if(editText.getText().toString().trim().length() == 0) return false;

        return true;
    }

    public final static boolean validateEmail(EditText editText) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches();
    }

    public final static boolean validateMatchPassword(EditText editText1, EditText editText2) {

        String val1 = editText1.getText().toString().trim();
        String val2 = editText2.getText().toString().trim();

        if (!val1.equalsIgnoreCase(val2)){
            return false;
        }
        return true;
    }

    public static boolean validateValueIsNotZero(String editText){

        if(editText == null) return false;
        if(editText.toString().trim().length() == 0) return false;
        if(!Utils.isNumeric(editText.toString().trim())) return false;
        int val = Integer.parseInt(editText);
        if(val <= 0) return false;

        return true;
    }


}
