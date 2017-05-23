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
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.SubSectionsItem;
import com.ichi.inspection.app.models.TemplateItemsItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        if(baseResponse.getError() != null && !baseResponse.getError().isEmpty()) return true;

        return false;
    }

    public static boolean showCallError(CoordinatorLayout coordinatorLayout, BaseResponse baseResponse){

        if (baseResponse == null){
            Snackbar.make(coordinatorLayout, R.string.str_something_went_wrong, Snackbar.LENGTH_SHORT).show();
            return true;
        }
        if(baseResponse.getError() != null && !baseResponse.getError().isEmpty()) {
            Snackbar.make(coordinatorLayout,baseResponse.getError(), Snackbar.LENGTH_SHORT).show();
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

    public static String getTimeFromTimeDateString(String timeData) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_LOCALTIME);
            Date date = simpleDateFormat.parse(timeData);
            simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_LOCALTIME_TIME);
            return simpleDateFormat.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isCurrentDay(long dateTime){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);

        if(dateTime >= calendar.getTimeInMillis()) return true;

        return false;
    }

    public static boolean isOlderThanThreeDay(long dateTime){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);

        if(dateTime >= calendar.getTimeInMillis()) return false;

        return true;
    }

    public static String getDateFromTimeDateString(String timeData) {

        try {
            Log.v(TAG,"timeData : " + timeData);
            if(timeData == null) return "";
            if(timeData.isEmpty()) return "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_LOCALTIME);
            Date date = simpleDateFormat.parse(timeData);
            simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_LOCALTIME_DATE);
            return simpleDateFormat.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getGlobalUniqueNumber(Context context, boolean shouldIncrement){

        int number = 0;
        PreferencesHelper prefs = PreferencesHelper.getInstance(context);
        if(prefs.contains(Constants.PREF_GLOBAL_NUMBER)){
            number = prefs.getInt(Constants.PREF_GLOBAL_NUMBER,number);
        }
        if(number == 0) shouldIncrement = true;
        if(shouldIncrement){
            number = number + 1;
            prefs.putInt(Constants.PREF_GLOBAL_NUMBER,number);
        }
        return number;
    }

    public static SubSectionsItem convertTemplateToSubSection(Context context, TemplateItemsItem templateItemsItem, String inspectionId) {

        SubSectionsItem subSectionsItem = new SubSectionsItem();
        subSectionsItem.setIOLineId(new Date().getTime() +"");
        subSectionsItem.setSectionId(templateItemsItem.getSectionId());
        subSectionsItem.setInspectionId(inspectionId);
        subSectionsItem.setGood("f");
        subSectionsItem.setFair("f");
        subSectionsItem.setPoor("f");
        subSectionsItem.setComments(""+templateItemsItem.getComments());
        subSectionsItem.setLineNumber("");
        subSectionsItem.setIsHead(templateItemsItem.getIsHead());
        subSectionsItem.setName(templateItemsItem.getName());
        subSectionsItem.setLineOrder("");
        subSectionsItem.setSuppressPrint("f");
        subSectionsItem.setPageBreak("");

        //if true, means its section, if not, means its line
        if(Boolean.parseBoolean(templateItemsItem.getIsHead())){
            //subSectionsItem.setUsedHead(""+Utils.getGlobalUniqueNumber(context,true));
            //TODO usedHead is comming in template data.. sections are having unique usedHead int, while followed lines are having same usedHead as of its section's
            //Check this logic later
            subSectionsItem.setUsedHead(""+templateItemsItem.getUsedHead());
            subSectionsItem.setNotInspected("f");
        }
        else{
            //subSectionsItem.setUsedHead(""+Utils.getGlobalUniqueNumber(context,false));
            subSectionsItem.setUsedHead(""+Utils.getGlobalUniqueNumber(context,false));
            subSectionsItem.setNotInspected(""+templateItemsItem.getUsedHead());
        }

        subSectionsItem.setNumberOfExposures("");
        subSectionsItem.setVeryPoor("f");
        subSectionsItem.setTemplatedId(templateItemsItem.getNamedTemplateId());

        return subSectionsItem;
    }

    public static boolean hasSubSection(List<SubSectionsItem> alSubSections, SubSectionsItem subSectionsItem) {

        //dont let it to put in array, so return true, to bypass..
        if(subSectionsItem == null) return true;

        for(SubSectionsItem sub : alSubSections){
            if(sub.getSectionId().equalsIgnoreCase(subSectionsItem.getSectionId())){
                return true;
            }
        }

        return false;
    }
}
