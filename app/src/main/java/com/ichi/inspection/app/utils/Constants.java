package com.ichi.inspection.app.utils;

/**
 * Created by Palak on 01-01-2017.
 */

public class Constants {

    //fragments
    public static final int SPLASH = 0;
    public static final int LOG_IN = 1;
    public static final int FORGET_PASSWORD = 2;
    public static final int INSPECTIONLIST = 3;
    public static final int INSPECTION_NAVIGATION = 4;
    public static final int INSPECTION_ORDER = 5;
    public static final int INSPECTION_DETAIL = 6;
    public static final int INSPECTION_CLIENT_INFO = 7;
    public static final int INSPECTION_BUYERS_AGENT = 8;
    public static final int INSPECTION_LINKING_AGENT = 9;
    public static final int INSPECTION_PROPERTY_INFO = 10;
    public static final int INSPECTION_SCHEDULER = 11;
    public static final int INSPECTION_FEES = 12;
    public static final int INSPECTION_SPECIAL_INSTRUCTION = 13;
    public static final int INSPECTION_PAYMENT = 14;

    public static final boolean debugging = true;
    public static final String USER = "USER";
    public static final boolean showStackTrace = debugging;
    public static final boolean showErrorMessages = debugging;
    public static final String INTENT_FROM = "FROM";
    public static boolean showDebugMessages = debugging;
    public static boolean showInfoMessages = debugging;
    public static boolean showWarningMessages = debugging;

    public static final int STATUS_SUCCESS_CODE = 1;
    public static final int STATUS_FAILURE_CODE = 0;

    public static final int STATUS_NOT_SYNCED = 0;
    public static final int STATUS_SYNCED = 1;

    // api
    public static final String CLIENT_ID = "98545254";
    public static final String BASE_URL = "http://www.samplegoogle.com";
    public static final String URL_LOGIN = "http://uat.ichisoftware.com:8088/token";

    //TODO sample... remove if unused. later
    public static final String DATE_FORMAT_LOCALTIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_LOCALTIME_12 = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_FORMAT_LOCALTIME_DATE = "dd/MM/yyyy";


    //Pref helper keys
    public static final String PREF_TOKEN_OBJECT = "PREF_TOKEN_OBJECT";

    // result codes
    /*public static final int CREATE_NEW_CONTACT = 100;
    public static final int PICK_CONTACT = 101;
    public static final int EDIT_CONTACT = 101;*/

    //intent preference

    public static final String INTENT_IS_FROM_SIGN_UP = "INTENT_IS_FROM_SIGN_UP";
    public static final String INTENT_CONTACT_ID = "INTENT_CONTACT_ID";
    public static final String INTENT_REGISTER_REQUEST = "INTENT_REGISTER_REQUEST";
    public static final String INTENT_KEY_FINISH_ACTIVITY_ON_SAVE_COMPLETED = "finishActivityOnSaveCompleted";


}
