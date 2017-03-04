package com.ichi.inspection.app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.models.BaseResponse;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static Dialog popupDialog;
    private static AlertDialog alertDialog;

    public static void openGeneralUrl(Activity activity, String link){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(link));
        activity.startActivity(i);
    }

    public static boolean hasCallErrorInBackground(BaseResponse baseResponse){

        if (baseResponse == null) return true;
        if(baseResponse.resultCode != 1) return true;

        return false;
    }

    public static boolean showCallError(CoordinatorLayout coordinatorLayout, BaseResponse baseResponse){

        if (baseResponse == null){
            Snackbar.make(coordinatorLayout, R.string.str_something_went_wrong, Snackbar.LENGTH_SHORT).show();
            return true;
        }

        if(baseResponse.resultCode != 1) {
            Snackbar.make(coordinatorLayout,baseResponse.msg, Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    public static void showSnackBar(CoordinatorLayout coordinatorLayout, String msg){
        Snackbar.make(coordinatorLayout,msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showAskAlertDialog(final Activity activity, int iconRes, int title, int msg, int posStr, int nagStr,
                                          DialogInterface.OnClickListener onPosClickListener,DialogInterface.OnClickListener onNagClickListener) {

        try {
            //TODO dont delete this now
            /*if (activity != null) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                View contentView = inflater.inflate(R.layout.item_become_cleanr, null);

                contentView.findViewById(R.id.tvGetStarted).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideBecomeCleanrBar(activity);
                    }
                });

                contentView.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideBecomeCleanrBar(activity);
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(activity).setMessage(msg);
                if(title != 0) builder.setTitle(title);
                if(posStr != 0) builder.setPositiveButton(posStr, onPosClickListener);
                if(nagStr != 0) builder.setNegativeButton(nagStr, onNagClickListener);
                if(iconRes != 0) builder.setIcon(iconRes);

                alertDialog = builder.show();
            }*/
        }
        catch (Exception e) {
            if (Constants.showErrorMessages) Log.e(TAG, e.toString());
            if (Constants.showStackTrace) e.printStackTrace();
        }
    }

    /**
     * get capital string
     *
     * @param s string here
     * @return
     */
    public static String Capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * Remove progressbar dialog
     *
     * @param activity
     */
    public static void hideAskAlertDialog(Activity activity) {

        if (activity != null && !activity.isFinishing() && alertDialog != null && alertDialog.isShowing()) {
            alertDialog.cancel();
        }
    }

    public static boolean isNumeric(String str) {

        if(str == null) return false;
        if(str.trim().isEmpty()) return false;
        
        try{
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}