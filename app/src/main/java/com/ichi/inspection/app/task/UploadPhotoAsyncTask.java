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
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.Photo;
import com.ichi.inspection.app.models.UploadPhoto;
import com.ichi.inspection.app.rest.ApiService;
import com.ichi.inspection.app.rest.ServiceGeneratorMultipart;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.PreferencesHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
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

public class UploadPhotoAsyncTask extends AsyncTask<Void,Void,BaseResponse> {
    private static final String TAG = UploadPhotoAsyncTask.class.getSimpleName();
    private final Context context;
    private final PreferencesHelper prefs;
    private String uri;
    private String inspectionId;

    public UploadPhotoAsyncTask(Context context, String uri, String inspectionId) {
        this.context=context;
        this.uri = uri;
        this.inspectionId = inspectionId;
        prefs = PreferencesHelper.getInstance(this.context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public void extractBytes(File ImageName) throws IOException {
        // open image
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(ImageName);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        encImage.replace("\n", "").replace("\r", "");

        byte[] decodedString = Base64.decode(encImage, Base64.DEFAULT);
        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(ImageName);
        fos.write(decodedString);
        fos.flush();
        fos.close();
    }

    @Override
    protected BaseResponse doInBackground(Void... params) {
        BaseResponse response = null;

        String json=prefs.getString(Constants.PREF_IMAGE_URL,"");
        Type type = new TypeToken<List<Photo>>() {}.getType();
        Gson gson=new Gson();
        List<Photo> photos = gson.fromJson(json, type);


        //File imageFile = new File(photos.get(1).getPhotoPath());

        File imageFile = new File(uri);
       /* try {
            extractBytes(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        RequestBody requestFile = RequestBody.create(MediaType.parse(context.getContentResolver().getType(getImageContentUri(context,imageFile))), imageFile);

        RequestBody inspectionIdRequest = RequestBody.create(okhttp3.MultipartBody.FORM, inspectionId);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part imageFileBody = MultipartBody.Part.createFormData("file", imageFile.getName().replace("\n", "").replace("\r", ""), requestBody);


        try{
            ApiService apiService = ServiceGeneratorMultipart.getGeneralApiService(context);
            Call<UploadPhoto> uploadResponseCall = null;
            uploadResponseCall=apiService.uploadVideoToServer(Constants.URL_UPLOAD_PHOTO.replace("inspectionId",inspectionId), "bearer "+prefs.getSavedTokenResponse(context).getAccessToken(), imageFileBody);

            uploadResponseCall.enqueue(new Callback<UploadPhoto>() {
                @Override
                public void onResponse(Call<UploadPhoto> call, Response<UploadPhoto> response) {

                    Log.v(TAG,"resp upload photo: " + response.body());
                    Log.v(TAG,"resp upload photo: " + response.errorBody());
                    Log.v(TAG,"resp upload photo: " + response.message());
                    Log.v(TAG,"resp upload photo: " + response.headers());
                    Log.v(TAG,"resp upload photo: " + response.raw());

                }

                @Override
                public void onFailure(Call<UploadPhoto> call, Throwable t) {

                    Log.v(TAG,"resp upload photo error: " + t.getMessage());
                }
            });
           /* //TODO check below line
            if(response != null && response.getMessage() != null && response.getMessage().equalsIgnoreCase("SUCCESS")){
                //TODO success, delete data from pref regarding this order

            }
*/
            //Log.v(TAG,"Response : " + response);
        }
        catch (Exception e){
            if(Constants.showStackTrace) e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(BaseResponse baseResponse) {
        super.onPostExecute(baseResponse);

        /*String json=prefs.getString(Constants.PREF_IMAGE_URL,"");
        Type type = new TypeToken<List<Photo>>() {}.getType();
        Gson gson=new Gson();
        List<Photo> photos = gson.fromJson(json, type);
        photos.remove(1);
        prefs.putString(Constants.PREF_IMAGE_URL,gson.toJson(photos));*/
    }

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
    }
}
