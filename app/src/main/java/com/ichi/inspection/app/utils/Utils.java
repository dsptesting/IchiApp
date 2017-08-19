package com.ichi.inspection.app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.models.AddSection;
import com.ichi.inspection.app.models.AddSectionItem;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.OrderUpdateContainer;
import com.ichi.inspection.app.models.OrderUpdates;
import com.ichi.inspection.app.models.SelectSection;
import com.ichi.inspection.app.models.SubSectionsItem;
import com.ichi.inspection.app.models.TemplateItemsItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
        if(context == null) return false;
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
        subSectionsItem.setIOLineId(System.nanoTime() +"");
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
        //if(Boolean.parseBoolean(templateItemsItem.getIsHead())){
            //subSectionsItem.setUsedHead(""+Utils.getGlobalUniqueNumber(context,true));
            //TODO usedHead is comming in template data.. sections are having unique usedHead int, while followed lines are having same usedHead as of its section's
            //Check this logic later
            subSectionsItem.setUsedHead(""+templateItemsItem.getUsedHead());
            subSectionsItem.setNotInspected("f");
        /*}
        else{
            //subSectionsItem.setUsedHead(""+Utils.getGlobalUniqueNumber(context,false));
            subSectionsItem.setUsedHead(""+Utils.getGlobalUniqueNumber(context,false));
            subSectionsItem.setNotInspected(""+templateItemsItem.getUsedHead());
        }*/

        subSectionsItem.setNumberOfExposures("");
        subSectionsItem.setVeryPoor("f");
        //TODO TemplateId... check with Amit
        subSectionsItem.setTemplatedId(templateItemsItem.getNamedTemplateId());
        subSectionsItem.setStatus(Constants.ADDED);

        return subSectionsItem;
    }

    public static SubSectionsItem convertAddSectionToSubSection(Context context, AddSectionItem addSectionItem, String inspectionId, String templateId) {

        SubSectionsItem subSectionsItem = new SubSectionsItem();
        subSectionsItem.setIOLineId(System.nanoTime() +"");
        subSectionsItem.setSectionId(addSectionItem.getSectionId());
        subSectionsItem.setInspectionId(inspectionId);
        subSectionsItem.setGood("f");
        subSectionsItem.setFair("f");
        subSectionsItem.setPoor("f");
        subSectionsItem.setComments("");
        subSectionsItem.setLineNumber("");
        subSectionsItem.setIsHead(addSectionItem.getIsHead());
        subSectionsItem.setName(addSectionItem.getName());
        subSectionsItem.setLineOrder("");
        if(addSectionItem.getIncludeInReports().equalsIgnoreCase("True")){
            subSectionsItem.setSuppressPrint("F");
        }
        else{
            subSectionsItem.setSuppressPrint("T");
        }

        subSectionsItem.setPageBreak("");
        subSectionsItem.setUsedHead(""+Utils.getGlobalUniqueNumber(context,true));
        Log.v(TAG,"UsedHead for addSection: " + subSectionsItem.getUsedHead() + ", name : " + subSectionsItem.getName());
        subSectionsItem.setNotInspected("f");
        subSectionsItem.setNumberOfExposures("");
        subSectionsItem.setVeryPoor("f");
        subSectionsItem.setTemplatedId(templateId);
        subSectionsItem.setStatus(Constants.ADDED);

        return subSectionsItem;
    }

    public static SubSectionsItem convertAddSectionItemToSubSection(Context context, AddSectionItem addSectionItem, String inspectionId, String templateId) {

        SubSectionsItem subSectionsItem = new SubSectionsItem();
        subSectionsItem.setIOLineId(System.nanoTime() +"");
        subSectionsItem.setSectionId(addSectionItem.getSectionId());
        subSectionsItem.setInspectionId(inspectionId);
        subSectionsItem.setGood("f");
        subSectionsItem.setFair("f");
        subSectionsItem.setPoor("f");
        subSectionsItem.setComments("");
        subSectionsItem.setLineNumber("");
        subSectionsItem.setIsHead(addSectionItem.getIsHead());
        subSectionsItem.setName(addSectionItem.getName());
        subSectionsItem.setLineOrder("");
        if(addSectionItem.getIncludeInReports().equalsIgnoreCase("True")){
            subSectionsItem.setSuppressPrint("F");
        }
        else{
            subSectionsItem.setSuppressPrint("T");
        }

        subSectionsItem.setPageBreak("");
        subSectionsItem.setUsedHead(""+Utils.getGlobalUniqueNumber(context,false));
        Log.v(TAG,"UsedHead for addSection: " + subSectionsItem.getUsedHead() + ", name : " + subSectionsItem.getName());
        subSectionsItem.setNotInspected("f");
        subSectionsItem.setNumberOfExposures("");
        subSectionsItem.setVeryPoor("f");
        subSectionsItem.setTemplatedId(templateId);
        subSectionsItem.setStatus(Constants.ADDED);

        return subSectionsItem;
    }


    public static boolean hasSubSection(List<SubSectionsItem> alSubSections, SubSectionsItem subSectionsItem) {

        //dont let it to put in array, so return true, to bypass..
        if(subSectionsItem == null) return true;

        for(SubSectionsItem sub : alSubSections){
            //Log.v(TAG,"sub: " +sub.getSectionId() + " , " + sub.getIOLineId() +" , "+ sub.getName());
            if(sub.getContentType() == Constants.HEADER) continue;
            if(sub.getIOLineId().equalsIgnoreCase(subSectionsItem.getIOLineId())){
                return true;
            }
        }

        return false;
    }


    public static boolean hasSubSectionItem(List<SubSectionsItem> alSubSections, SubSectionsItem subSectionsItem) {

        //dont let it to put in array, so return true, to bypass..
        if(subSectionsItem == null) return true;

        for(SubSectionsItem sub : alSubSections){
            if(sub.getIOLineId().equalsIgnoreCase(subSectionsItem.getIOLineId())){
                return true;
            }
        }

        return false;
    }

    public static boolean hasSubSection(Context context,  AddSectionItem addSectionItem, int inspectionId) {

        //dont let it to put in array, so return true, to bypass..
        if(addSectionItem == null) return true;
        SelectSection selectSection = (SelectSection) PreferencesHelper.getInstance(context).getObject(Constants.PREF_SELECT_SECTION, SelectSection.class);
        List<SubSectionsItem> alSubSections = selectSection.getSubSections();

        boolean has = false;
        boolean deleted = false;
        int sectionIdNumberStart = Integer.parseInt(addSectionItem.getSectionId());
        int sectionIdNumberEnd = Integer.parseInt(addSectionItem.getSectionId().replace("00","99"));


        Iterator<SubSectionsItem> iter1 = alSubSections.iterator();

        while (iter1.hasNext()) {
            SubSectionsItem sub = iter1.next();

            //Log.v(TAG,"sub: " +sub.getSectionId() + " , " + sub.getIOLineId() +" , "+ sub.getName());
            if(sub.getContentType() == Constants.HEADER) continue;

            if(sub.getInspectionId().equalsIgnoreCase(""+inspectionId) && sub.getSectionId().equalsIgnoreCase(addSectionItem.getSectionId()) &&
                    sub.getName().equalsIgnoreCase(addSectionItem.getName())){

                has = true;
                Log.v(TAG,"matched  "+ has + ", " + sub);
                if(sub.getStatus() == Constants.DELETED){
                    //find its lines, delete from list, and add fresh again
                    deleted = true;
                    Log.v(TAG,"matched in");
                    iter1.remove();
                    has = false;
                }

            }
        }

        if(deleted) {
            Iterator<SubSectionsItem> iter2 = alSubSections.iterator();

            while (iter2.hasNext()) {
                SubSectionsItem sub = iter2.next();
                if (sub.getInspectionId().equalsIgnoreCase(""+inspectionId) && Integer.parseInt(sub.getSectionId()) > sectionIdNumberStart &&
                        Integer.parseInt(sub.getSectionId()) <= sectionIdNumberEnd && sub.getStatus() == Constants.DELETED) {
                    iter2.remove();
                }
            }

            for(SubSectionsItem subSectionsItem : alSubSections){
                if(subSectionsItem.getInspectionId().equalsIgnoreCase(""+inspectionId) && subSectionsItem.getSectionId().equalsIgnoreCase(addSectionItem.getSectionId())
                        && subSectionsItem.getName().equalsIgnoreCase(addSectionItem.getName())){

                    Log.v(TAG,"matched in found " + subSectionsItem);
                }
            }
            selectSection.setSubSections(alSubSections);
            PreferencesHelper.getInstance(context).putObject(Constants.PREF_SELECT_SECTION,selectSection);
        }

        return has;
    }



    public static void updateThisSubSection(Context context, SubSectionsItem subSectionsItem) {
        if(subSectionsItem == null) return;
        PreferencesHelper prefs = PreferencesHelper.getInstance(context);
        boolean found = false;
        List<String> orderUpdates = null;
        OrderUpdateContainer orderUpdateContainer = null;

        if(!prefs.contains(Constants.PREF_ORDER_UPDATE)){
            orderUpdateContainer = new OrderUpdateContainer();
            orderUpdates = orderUpdateContainer.getOrderUpdatesList();
        }
        else{
            orderUpdateContainer = (OrderUpdateContainer) prefs.getObject(Constants.PREF_ORDER_UPDATE, OrderUpdateContainer.class);
            if(orderUpdateContainer != null && orderUpdateContainer.getOrderUpdatesList() != null && !orderUpdateContainer.getOrderUpdatesList().isEmpty()){
                orderUpdates = orderUpdateContainer.getOrderUpdatesList();

                for(String inspectionId : orderUpdates){
                    if(subSectionsItem.getInspectionId().equalsIgnoreCase(inspectionId)){
                        found = true;
                    }
                }
            }
        }


        if(!found){
            orderUpdates.add(subSectionsItem.getInspectionId());
            prefs.putObject(Constants.PREF_ORDER_UPDATE,orderUpdateContainer);
            Log.v(TAG,"inspection updated");
        }
    }

    /**
     * Display progressbar dialog
     * @param activity
     */
    public static void showProgressBar(Activity activity) {
        try {
            if (activity != null) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                View contentView = inflater.inflate(R.layout.loading_layout, null);

                popupDialog = new Dialog(activity);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(popupDialog.getWindow().getAttributes());
                popupDialog.getWindow().setAttributes(lp);
                popupDialog.setCancelable(false);
                popupDialog.setCanceledOnTouchOutside(false);
                popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                popupDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.cancel();
                    }
                });
                popupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                popupDialog.setContentView(contentView);

                popupDialog.show();
            }
            //}
        } catch (Exception e) {
            if (Constants.showErrorMessages) Log.e(TAG, e.toString());
            if (Constants.showStackTrace) e.printStackTrace();
        }
    }

    /**
     * Remove progressbar dialog
     *
     * @param activity
     */
    public static void hideProgressBar(Activity activity) {
        if (activity != null && !activity.isFinishing() && popupDialog != null) {
            popupDialog.cancel();
        }
    }

}
