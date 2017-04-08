package com.ichi.inspection.app.task;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.GetTokenResponse;
import com.ichi.inspection.app.rest.ApiService;
import com.ichi.inspection.app.rest.ServiceGenerator;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.PreferencesHelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Palak on 05-03-2017.
 */

public class RefreshTokenAsyncTask extends AsyncTask<Void,Void,GetTokenResponse> {

    private static final String TAG = RefreshTokenAsyncTask.class.getSimpleName();
    private Context context;
    private OnApiCallbackListener onApiCallbackListener;
    private PreferencesHelper prefs;

    public RefreshTokenAsyncTask(Context context, OnApiCallbackListener onApiCallbackListener) {
        this.context = context;
        this.onApiCallbackListener = onApiCallbackListener;
        prefs = PreferencesHelper.getInstance(this.context);
    }

    @Override
    protected void onPreExecute() {
        onApiCallbackListener.onApiPreExecute(this);
    }

    @Override
    protected GetTokenResponse doInBackground(Void... params) {

        Call<GetTokenResponse> getTokenResponseCall = null;
        GetTokenResponse getTokenResponse = new GetTokenResponse();

        try{
            ApiService apiService = ServiceGenerator.getApiService(context);
            getTokenResponseCall = apiService.executeRefreshToken(prefs.getSavedTokenResponse(context).getRefreshToken(),
                    "refresh_token",Constants.CLIENT_ID);

            Response<GetTokenResponse> response = getTokenResponseCall.execute();
            if(response.isSuccessful()){
                if(response.body() != null) getTokenResponse = response.body();

            }
            else{
                if(response.errorBody() != null){
                    ResponseBody responseBody = response.errorBody();
                    getTokenResponse = new Gson().fromJson(new String(responseBody.bytes()), GetTokenResponse.class);
                }
            }
        }
        catch (Exception e){
            if(Constants.showStackTrace) e.printStackTrace();
        }

        return getTokenResponse;
    }

    @Override
    protected void onPostExecute(GetTokenResponse getTokenResponse) {
        onApiCallbackListener.onApiPostExecute(getTokenResponse,this);


    }
}
