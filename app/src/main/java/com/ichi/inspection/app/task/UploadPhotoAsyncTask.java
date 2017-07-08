package com.ichi.inspection.app.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.Photo;
import com.ichi.inspection.app.models.UploadPhoto;
import com.ichi.inspection.app.rest.ApiService;
import com.ichi.inspection.app.rest.ServiceGenerator;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.PreferencesHelper;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mayank on 08-07-2017.
 */

public class UploadPhotoAsyncTask extends AsyncTask<Void,Void,BaseResponse> {
    private static final String TAG = UploadPhotoAsyncTask.class.getSimpleName();
    private final Context context;
    private final PreferencesHelper prefs;

    public UploadPhotoAsyncTask(Context context) {
        this.context=context;
        prefs = PreferencesHelper.getInstance(this.context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected BaseResponse doInBackground(Void... params) {
        BaseResponse response = null;

        String json=prefs.getString(Constants.PREF_IMAGE_URL,"");
        Type type = new TypeToken<List<Photo>>() {}.getType();
        Gson gson=new Gson();
        List<Photo> photos = gson.fromJson(json, type);


        File imageFile = new File(photos.get(1).getPhotoPath());
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), imageFile);

        try{
            ApiService apiService = ServiceGenerator.getApiService(context);
            Call<UploadPhoto> uploadResponseCall = null;
            uploadResponseCall=apiService.uploadVideoToServer("bearer "+prefs.getSavedTokenResponse(context).getAccessToken(),imageBody);

            uploadResponseCall.enqueue(new Callback<UploadPhoto>() {
                @Override
                public void onResponse(Call<UploadPhoto> call, Response<UploadPhoto> response) {

                }

                @Override
                public void onFailure(Call<UploadPhoto> call, Throwable t) {

                }
            });
            //TODO check below line
            if(response != null && response.getMessage() != null && response.getMessage().equalsIgnoreCase("SUCCESS")){
                //TODO success, delete data from pref regarding this order

            }

            Log.v(TAG,"Response : " + response);
        }
        catch (Exception e){
            if(Constants.showStackTrace) e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(BaseResponse baseResponse) {
        super.onPostExecute(baseResponse);

        String json=prefs.getString(Constants.PREF_IMAGE_URL,"");
        Type type = new TypeToken<List<Photo>>() {}.getType();
        Gson gson=new Gson();
        List<Photo> photos = gson.fromJson(json, type);
        photos.remove(1);
        prefs.putString(Constants.PREF_IMAGE_URL,gson.toJson(photos));
    }
}
