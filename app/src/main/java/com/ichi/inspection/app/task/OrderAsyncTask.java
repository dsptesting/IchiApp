package com.ichi.inspection.app.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.GetTokenResponse;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.models.SignInRequest;
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

public class OrderAsyncTask extends AsyncTask<Void,Void,OrderResponse> {

    private static final String TAG = OrderAsyncTask.class.getSimpleName();
    private Context context;
    private OnApiCallbackListener onApiCallbackListener;
    private PreferencesHelper prefs;

    public OrderAsyncTask(Context context, OnApiCallbackListener onApiCallbackListener) {
        this.context = context;
        this.onApiCallbackListener = onApiCallbackListener;
        prefs = PreferencesHelper.getInstance(this.context);
    }

    @Override
    protected void onPreExecute() {
        onApiCallbackListener.onApiPreExecute(this);
    }

    @Override
    protected OrderResponse doInBackground(Void... params) {

        OrderResponse orderResponse = null;

        try{
            ApiService apiService = ServiceGenerator.getApiService(context);
            orderResponse = callApi(apiService,orderResponse);
            Log.v(TAG,"OrderResponse : " + orderResponse);
        }
        catch (Exception e){
            if(Constants.showStackTrace) e.printStackTrace();
        }

        return orderResponse;
    }

    private OrderResponse callApi(ApiService apiService,OrderResponse orderResponse) throws Exception {

        Call<OrderResponse> orderResponseCall = null;

        orderResponseCall = apiService.executeOrderList("bearer "+prefs.getSavedTokenResponse(context).getAccessToken());

        Response<OrderResponse> response = orderResponseCall.execute();
        if(response.isSuccessful()){
            if(response.body() != null){
                orderResponse = response.body();
                orderResponse.setAction(Constants.ACTION_DO_NOTHING);
            }
        }
        else{
            if(response.errorBody() != null){
                ResponseBody responseBody = response.errorBody();
                orderResponse = new Gson().fromJson(new String(responseBody.bytes()), OrderResponse.class);

                if(orderResponse != null && orderResponse.getMessage() != null
                        && !orderResponse.getMessage().isEmpty() && orderResponse.getMessage().startsWith("Authorization has been denied")){

                    //As we dont have any error code, we will directly compare error string.
                    Call<GetTokenResponse> getTokenResponseCall = apiService.executeRefreshToken(prefs.getSavedTokenResponse(context).getRefreshToken(),
                            "refresh_token",Constants.CLIENT_ID);

                    GetTokenResponse getTokenResponse;

                    Response<GetTokenResponse> getTokenResp = getTokenResponseCall.execute();
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            getTokenResponse = getTokenResp.body();
                            prefs.putGetTokenResponse(context,getTokenResponse);
                            orderResponse.setAction(Constants.ACTION_DO_NOTHING);
                            callApi(apiService,orderResponse);
                        }
                    }
                    else{
                        if(response.errorBody() != null){
                            responseBody = response.errorBody();
                            getTokenResponse = new Gson().fromJson(new String(responseBody.bytes()), GetTokenResponse.class);
                            orderResponse.setAction(Constants.ACTION_LOGIN_AGAIN);
                        }
                    }
                }
            }
        }

        return orderResponse;
    }

    @Override
    protected void onPostExecute(OrderResponse orderResponse) {

        onApiCallbackListener.onApiPostExecute(orderResponse,this);
        prefs.putObject(Constants.PREF_ORDER,orderResponse);

    }
}
