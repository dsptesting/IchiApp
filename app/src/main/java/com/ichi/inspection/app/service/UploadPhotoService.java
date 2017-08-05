package com.ichi.inspection.app.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.ichi.inspection.app.activities.StartActivity;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.Photo;
import com.ichi.inspection.app.models.UploadPhotoList;
import com.ichi.inspection.app.task.UploadPhotoAsyncTask;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.PreferencesHelper;
import com.ichi.inspection.app.utils.Utils;

import java.util.List;

public class UploadPhotoService extends Service implements OnApiCallbackListener {

    private static final String TAG = UploadPhotoService.class.getSimpleName();
    private PreferencesHelper prefs;
    boolean isNetworkConnected;
    private boolean isRunning;

    public UploadPhotoService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(this != null) prefs = PreferencesHelper.getInstance(this);

        //Log.v(TAG,"onStartCommand called");
        if(intent != null){
            Bundle extras = intent.getExtras();
            if(extras != null){
                isNetworkConnected = extras.getBoolean("isNetworkConnected");
            }
        }

        isNetworkConnected = Utils.isNetworkAvailable(this);
        if(isNetworkConnected){

            //Log.v(TAG,"1234 Pfirst time");
            fireNextCall();
        }

        return START_STICKY;

    }

    private void fireNextCall(){
        Photo photo = getFirst();
        if(photo != null){
            if(!isRunning){
                isRunning = true;
                //Log.v(TAG,"1234 Photo uploading start: " + photo.getPhotoName());
                UploadPhotoAsyncTask uploadPhotoService = new UploadPhotoAsyncTask(this, this);
                uploadPhotoService.execute(photo);
            }
        }
        else{
            stopSelf();
        }
    }

    private Photo getFirst(){
        UploadPhotoList uploadPhotoList = (UploadPhotoList) prefs.getObject(Constants.PREF_PHOTO_TO_BE_UPLOADED,UploadPhotoList.class);
        if(uploadPhotoList == null) return null;
        List<Photo> photos = uploadPhotoList.getPhotos();
        if(photos != null && !photos.isEmpty()){
            return photos.get(0);
        }
        return null;
    }

    @Override
    public void onApiPreExecute(AsyncTask asyncTask) {
        isRunning = true;
    }

    @Override
    public void onApiPostExecute(BaseResponse baseResponse, AsyncTask asyncTask) {
        if(asyncTask instanceof UploadPhotoAsyncTask){
            isRunning = false;
            //if(baseResponse.getError() != null && !baseResponse.getError().isEmpty()){

            fireNextCall();
            //}
        }
    }
}
