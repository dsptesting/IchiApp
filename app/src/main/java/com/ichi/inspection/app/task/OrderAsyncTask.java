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

        Call<OrderResponse> orderResponseCall = null;
        OrderResponse orderResponse = new OrderResponse();

        try{
            ApiService apiService = ServiceGenerator.getApiService(context);
            orderResponseCall = apiService.executeOrderList();

            Response<OrderResponse> response = orderResponseCall.execute();
            if(response.isSuccessful()){
                if(response.body() != null) orderResponse = response.body();

            }
            else{
                if(response.errorBody() != null){
                    ResponseBody responseBody = response.errorBody();
                    orderResponse = new Gson().fromJson(new String(responseBody.bytes()), OrderResponse.class);
                }
            }
        }
        catch (Exception e){
            if(Constants.showStackTrace) e.printStackTrace();
        }

        Log.v(TAG,"OrderResponse : " + orderResponse);
        return orderResponse;
    }

    @Override
    protected void onPostExecute(OrderResponse orderResponse) {
        onApiCallbackListener.onApiPostExecute(orderResponse,this);


    }
}
