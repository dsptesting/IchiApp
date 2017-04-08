package com.ichi.inspection.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ichi.inspection.app.models.GetTokenResponse;

/**
 * This class handles all the values which are stored/persisted.
 * @version 1.0.
 */
public class PreferencesHelper {

    private static PreferencesHelper preferencesHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    // PREFERENCES
    public static String PREFS_NAME = PreferencesHelper.class.getName();

    public static PreferencesHelper getInstance(Context context){

        if(preferencesHelper == null){
            preferencesHelper = new PreferencesHelper(context);
        }
        return preferencesHelper;
    }

    public PreferencesHelper(Context context) {

        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        this.prefsEditor = sharedPreferences.edit();
    }

    public boolean contains(String key){

        return this.sharedPreferences.contains(key);
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key,defValue);
    }

    public void putLong(String key, long value) {
        prefsEditor.putLong(key, value);
        prefsEditor.commit();
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public void putString(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public void putFloat(String key, float value) {
        prefsEditor.putFloat(key, value);
        prefsEditor.commit();
    }

    public void putBoolean(String key, Boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public void putObject(String key, Object value) {
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public Object getObject(String key,Class<?> clazz) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        Object jsonObject = gson.fromJson(json, clazz);
        return jsonObject;
    }

    public void clear() {
        prefsEditor.clear().commit();
    }

/*
       //TODO dont delete this as we will need this once we are done with signup and login .. and when we get
       //TODO user model..
    public void clear() {

        //we dont want to clear location params,device token, device id.. so we will restore it.
        String lat = preferencesHelper.getString(Constants.PREF_LAT,"");
        String log = preferencesHelper.getString(Constants.PREF_LONG,"");
        String deviceToken = preferencesHelper.getString(Constants.PREF_DEVICE_TOKEN,"");
        String deviceId = preferencesHelper.getString(Constants.PREF_DEVICE_ID,"");

        prefsEditor.clear();
        prefsEditor.commit();

        preferencesHelper.putString(Constants.PREF_LAT,lat);
        preferencesHelper.putString(Constants.PREF_LONG,log);
        preferencesHelper.putString(Constants.PREF_DEVICE_TOKEN,deviceToken);
        preferencesHelper.putString(Constants.PREF_DEVICE_ID,deviceId);
    }
*/

    //Helper methods to store particular objects directly. This gives token object from pref
    public GetTokenResponse getSavedTokenResponse(Context context){
        PreferencesHelper prefs = getInstance(context);

        if(prefs.contains(Constants.PREF_TOKEN_OBJECT)){
            return (GetTokenResponse) prefs.getObject(Constants.PREF_TOKEN_OBJECT , GetTokenResponse.class);
        }
        return null;
    }

    public void putGetTokenResponse(Context context, GetTokenResponse getTokenResponse){
        PreferencesHelper prefs = getInstance(context);
        prefs.putObject(Constants.PREF_TOKEN_OBJECT , getTokenResponse);
    }


}
