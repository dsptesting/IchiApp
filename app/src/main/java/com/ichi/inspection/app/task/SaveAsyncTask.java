package com.ichi.inspection.app.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.GetTokenResponse;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.models.Payment;
import com.ichi.inspection.app.models.SelectSection;
import com.ichi.inspection.app.models.SubSectionsItem;
import com.ichi.inspection.app.rest.ApiService;
import com.ichi.inspection.app.rest.ServiceGenerator;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.PreferencesHelper;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Palak on 05-03-2017.
 */

public class SaveAsyncTask extends AsyncTask<Void,Void,BaseResponse> {

    private static final String TAG = SaveAsyncTask.class.getSimpleName();
    private Context context;
    private OnApiCallbackListener onApiCallbackListener;
    private PreferencesHelper prefs;
    private JSONObject jsonObject;
    private OrderListItem orderListItem;


    public SaveAsyncTask(Context context, OnApiCallbackListener onApiCallbackListener, JSONObject jsonObject, OrderListItem orderListItem) {
        this.context = context;
        this.orderListItem = orderListItem;
        this.onApiCallbackListener = onApiCallbackListener;
        prefs = PreferencesHelper.getInstance(this.context);
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected BaseResponse doInBackground(Void... params) {

        BaseResponse response = null;

        try{
            ApiService apiService = ServiceGenerator.getApiService(context);
            response = callApi(apiService,response);

            //TODO check below line
            if(response != null && response.getMessage() != null && response.getMessage().equalsIgnoreCase("SUCCESS")){
                //TODO success, delete data from pref regarding this order
                removeOrder();
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
        onApiCallbackListener.onApiPostExecute(baseResponse,this);
    }

    private BaseResponse callApi(ApiService apiService,BaseResponse orderResponse) throws Exception {

        Call<BaseResponse> orderResponseCall = null;

        RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        orderResponseCall = apiService.executeSaveList("bearer "+prefs.getSavedTokenResponse(context).getAccessToken(),body);

        Response<BaseResponse> response = orderResponseCall.execute();
        if(response.isSuccessful()){
            if(response.body() != null){
                orderResponse = response.body();
                orderResponse.setAction(Constants.ACTION_DO_NOTHING);
                return orderResponse;
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
                            return callApi(apiService,orderResponse);
                        }
                    }
                    else{
                        if(response.errorBody() != null){
                            responseBody = response.errorBody();
                            try{
                                getTokenResponse = new Gson().fromJson(new String(responseBody.bytes()), GetTokenResponse.class);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            orderResponse.setAction(Constants.ACTION_LOGIN_AGAIN);
                            return orderResponse;
                        }
                    }
                }
                return orderResponse;
            }
        }

        return orderResponse;
    }

    private void removeOrder(){

        //Remove sections
        SelectSection selectSectionReport = ((SelectSection) prefs.getObject(Constants.PREF_SELECT_SECTION, SelectSection.class));
        List<SubSectionsItem> subSectionsItems=selectSectionReport.getSubSections();

        Iterator<SubSectionsItem> iterator=subSectionsItems.iterator();
        while (iterator.hasNext()) {
            SubSectionsItem item=  iterator.next();
            if (item.getInspectionId().equals(orderListItem.getSequence())){
                iterator.remove();
            }
        }
        selectSectionReport.setSubSections(subSectionsItems);
        prefs.putObject(Constants.PREF_SELECT_SECTION,selectSectionReport);

        //Remove order
        OrderResponse orderResponse = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER, OrderResponse.class));
        List<OrderListItem> orders = orderResponse.getOrderList();
        for (OrderListItem order:orders){
            if (order.getSequence()==orderListItem.getSequence()){
                orders.remove(order);
                break;
            }
        }
        orderResponse.setOrderList(orders);
        prefs.putObject(Constants.PREF_ORDER,orderResponse);

    }

}
