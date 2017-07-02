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
import com.ichi.inspection.app.rest.ApiService;
import com.ichi.inspection.app.rest.ServiceGenerator;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.PreferencesHelper;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Palak on 05-03-2017.
 */

public class SaveAsyncTask /*extends AsyncTask<Void,Void,BaseResponse> */{

   /* private static final String TAG = SaveAsyncTask.class.getSimpleName();
    private Context context;
    private OnApiCallbackListener onApiCallbackListener;
    private PreferencesHelper prefs;
    private JSONObject jsonObject;

    public SaveAsyncTask(Context context, OnApiCallbackListener onApiCallbackListener, JSONObject jsonObject) {
        this.context = context;
        this.onApiCallbackListener = onApiCallbackListener;
        prefs = PreferencesHelper.getInstance(this.context);
        this.jsonObject = jsonObject;

    }

    @Override
    protected void onPreExecute() {
        onApiCallbackListener.onApiPreExecute(this);
    }

    @Override
    protected BaseResponse doInBackground(Void... params) {

        BaseResponse response = null;

        try{
            ApiService apiService = ServiceGenerator.getApiService(context);
            response = callApi(apiService,response);

            //TODO check below line
            if(response != null && !response.isEmpty() && response.equalsIgnoreCase("SUCCESS")){
                prefs.putObject(Constants.PREF_ORDER,orderResponse);
            }

            Log.v(TAG,"OrderResponse : " + orderResponse);
        }
        catch (Exception e){
            if(Constants.showStackTrace) e.printStackTrace();
        }

        return orderResponse;
    }

    private String callApi(ApiService apiService,BaseResponse res) throws Exception {

        Call<BaseResponse> orderResponseCall = null;

        orderResponseCall = apiService.executeSaveList("bearer "+prefs.getSavedTokenResponse(context).getAccessToken(),jsonObject);

        Response<BaseResponse> response = orderResponseCall.execute();
        if(response.isSuccessful()){
            if(response.body() != null){
                res = response.body();
                //fillData(orderResponse.getOrderList());
                res.setAction(Constants.ACTION_DO_NOTHING);
                Log.v(TAG,"fillData printed : " + orderResponse.getOrderList());
                //prefs.putObject(Constants.PREF_ORDER,orderResponse.getOrderList());
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

    @Override
    protected void onPostExecute(OrderResponse orderResponse) {

        onApiCallbackListener.onApiPostExecute(orderResponse,this);
    }

    private void fillData(List<OrderListItem> newList){

        if(!prefs.contains(Constants.PREF_ORDER)) return;

        List<OrderListItem> tempOrderListItems = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getOrderList();

        Log.v(TAG,"tempOrderListItems  : " + tempOrderListItems);
        if(tempOrderListItems == null) return;
        if(newList == null) return;

        for(int i=0;i<newList.size();i++){
            returnSavedData(tempOrderListItems,newList.get(i));
        }

    }

    private OrderListItem returnSavedData(List<OrderListItem> tempOrderListItems,OrderListItem orderListItem) {

        if(orderListItem == null) return null;

        if(orderListItem.getPayment() == null) return null;

        OrderListItem savedOrder = null;

        if(tempOrderListItems == null){
            tempOrderListItems = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getOrderList();
            if(tempOrderListItems == null) return orderListItem;
        }

        for(int i=0;i<tempOrderListItems.size();i++){
            if(orderListItem.getIONum() != 0 && orderListItem.getIONum() == tempOrderListItems.get(i).getIONum()){
                savedOrder = tempOrderListItems.get(i);
            }
        }

        if(savedOrder == null) return orderListItem;

        if(savedOrder.getPayment() == null) return orderListItem;

        Payment savedPayment = savedOrder.getPayment();
        Payment newPayment = orderListItem.getPayment();

        if(savedPayment.isAuthorizatinCodeSynced()){
            newPayment.setAuthorizationCode(savedPayment.getAuthorizationCode());
            newPayment.setAuthorizatinCodeSynced(savedPayment.isAuthorizatinCodeSynced());
        }

        if(savedPayment.isCcNameOnCardSynced()){
            newPayment.setcCNameOnCard(savedPayment.getcCNameOnCard());
            newPayment.setCcNameOnCardSynced(savedPayment.isCcNameOnCardSynced());
        }

        if(savedPayment.isAmountSynced()){
            newPayment.setAmount(savedPayment.getAmount());
            newPayment.setAmountSynced(savedPayment.isAmountSynced());
        }

        if(savedPayment.isCcNumberSynced()){
            newPayment.setcCNumber(savedPayment.getcCNumber());
            newPayment.setCcNumberSynced(savedPayment.isCcNumberSynced());
        }

        if(savedPayment.isCcCodeSynced()){
            newPayment.setcCCode(savedPayment.getcCCode());
            newPayment.setCcCodeSynced(savedPayment.isCcCodeSynced());
        }
        if(savedPayment.isCheckNumberSynced()){
            newPayment.setCheckNumber(savedPayment.getCheckNumber());
            newPayment.setCheckNumberSynced(savedPayment.isCheckNumberSynced());
        }
        if(savedPayment.isCcExprMonthSynced()){
            newPayment.setcCExprMonth(savedPayment.getcCExprMonth());
            newPayment.setCcExprMonthSynced(savedPayment.isCcExprMonthSynced());
        }
        if(savedPayment.isCcZipSynced()){
            newPayment.setcCZip(savedPayment.getcCZip());
            newPayment.setCcZipSynced(savedPayment.isCcZipSynced());
        }
        if(savedPayment.isCcCitySynced()){
            newPayment.setcCCity(savedPayment.getcCCity());
            newPayment.setCcCitySynced(savedPayment.isCcCitySynced());
        }

        if(savedPayment.isCcStateSynced()){
            newPayment.setcCState(savedPayment.getcCState());
            newPayment.setCcStateSynced(savedPayment.isCcStateSynced());
        }

        if(savedPayment.isTranTypeSynced()){
            newPayment.setTranType(savedPayment.getTranType());
            newPayment.setTranTypeSynced(savedPayment.isTranTypeSynced());
        }

        if(savedPayment.isCcAddressSynced()){
            newPayment.setcCAddress(savedPayment.getcCAddress());
            newPayment.setCcAddressSynced(savedPayment.isCcAddressSynced());
        }

        if(savedPayment.iscCTypeSynced()){
            newPayment.setcCType(savedPayment.getcCType());
            newPayment.setcCTypeSynced(savedPayment.iscCTypeSynced());
        }

        if(savedPayment.isPaymentTypeSynced()){
            newPayment.setPaymentType(savedPayment.getPaymentType());
            newPayment.setPaymentTypeSynced(savedPayment.isPaymentTypeSynced());
        }

        if(savedPayment.iscCTypeSynced()){
            newPayment.setcCType(savedPayment.getcCType());
            newPayment.setcCTypeSynced(savedPayment.iscCTypeSynced());
        }

        return orderListItem;
    }
*/
}
