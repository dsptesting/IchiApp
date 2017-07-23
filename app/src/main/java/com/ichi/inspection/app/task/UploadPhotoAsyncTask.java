package com.ichi.inspection.app.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.models.Photo;
import com.ichi.inspection.app.models.UploadPhoto;
import com.ichi.inspection.app.models.UploadPhotoList;
import com.ichi.inspection.app.models.UploadPhotoResponse;
import com.ichi.inspection.app.rest.ApiService;
import com.ichi.inspection.app.rest.ServiceGeneratorMultipart;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.Events;
import com.ichi.inspection.app.utils.PreferencesHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mayank on 08-07-2017.
 */

public class UploadPhotoAsyncTask extends AsyncTask<Photo,Void,UploadPhotoResponse> {
    private static final String TAG = UploadPhotoAsyncTask.class.getSimpleName();
    private final Context context;
    private final PreferencesHelper prefs;
    private Photo photo;
    private OnApiCallbackListener onApiCallbackListener;

    public UploadPhotoAsyncTask(Context context, OnApiCallbackListener onApiCallbackListener) {
        this.context=context;
        this.onApiCallbackListener = onApiCallbackListener;
        prefs = PreferencesHelper.getInstance(this.context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected UploadPhotoResponse doInBackground(Photo... params) {
        UploadPhotoResponse response = null;

        photo = params[0];
        EventBus.getDefault().post(new Events.UploadPhotoStartedUploading(photo));
        File imageFile = new File(photo.getPhotoPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part imageFileBody = MultipartBody.Part.createFormData("file", imageFile.getName().replace("\n", "").replace("\r", ""), requestBody);

        try{
            ApiService apiService = ServiceGeneratorMultipart.getGeneralApiService(context);
            Call<UploadPhotoResponse> uploadResponseCall = null;
            uploadResponseCall=apiService.uploadVideoToServer(Constants.URL_UPLOAD_PHOTO.replace("inspectionId",photo.getInspectionId()),
                    "bearer "+prefs.getSavedTokenResponse(context).getAccessToken(), imageFileBody);

            Response<UploadPhotoResponse> res = uploadResponseCall.execute();
            if(res.isSuccessful()){
                response = res.body();

                if(response.isCompleted()){
                    Log.v(TAG,"1234 response :: "+ response + ", "+ response);
                    removePhotoFromPendingList();
                    EventBus.getDefault().post(new Events.UploadPhotoRemoved(photo));
                }
            }

            /*if(response != null && response.getMessage() != null && response.getMessage().equalsIgnoreCase("SUCCESS")){
            }*/

        }
        catch (Exception e){
            if(Constants.showStackTrace) e.printStackTrace();
        }

        return response;
    }

    private void removePhotoFromPendingList() {

        UploadPhotoList uploadPhotoList = (UploadPhotoList) prefs.getObject(Constants.PREF_PHOTO_TO_BE_UPLOADED,UploadPhotoList.class);

        if(uploadPhotoList != null && uploadPhotoList.getPhotos() != null && !uploadPhotoList.getPhotos().isEmpty()){

            List<Photo> photos = uploadPhotoList.getPhotos();

            Log.v(TAG,"1234 size before " + photos.size());

            Iterator<Photo> photoIterator = photos.iterator();

            while(photoIterator.hasNext()){
                Photo ph = photoIterator.next();
                if(ph!= null && ph.getLineId().equals(photo.getLineId()) && ph.getPhotoName().equals(photo.getPhotoName())){
                    photoIterator.remove();
                }
            }

            uploadPhotoList.setPhotos(photos);
            Log.v(TAG,"1234 size after " + photos.size());
            prefs.putObject(Constants.PREF_PHOTO_TO_BE_UPLOADED,uploadPhotoList);
        }
    }

    @Override
    protected void onPostExecute(UploadPhotoResponse uploadPhotoResponse) {
        super.onPostExecute(uploadPhotoResponse);

        Log.v(TAG,"1234 onPostExecute");
        onApiCallbackListener.onApiPostExecute(uploadPhotoResponse,this);

    }
/*
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }*/

}
