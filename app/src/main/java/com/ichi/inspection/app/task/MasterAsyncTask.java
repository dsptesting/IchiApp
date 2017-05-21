package com.ichi.inspection.app.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.AddSection;
import com.ichi.inspection.app.models.GetTokenResponse;
import com.ichi.inspection.app.models.MasterResponse;
import com.ichi.inspection.app.models.NamedTemplates;
import com.ichi.inspection.app.models.SelectSection;
import com.ichi.inspection.app.models.Templates;
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

public class MasterAsyncTask extends AsyncTask<Void,Void,MasterResponse> {

    private static final String TAG = MasterAsyncTask.class.getSimpleName();
    private Context context;
    private OnApiCallbackListener onApiCallbackListener;
    private PreferencesHelper prefs;

    public MasterAsyncTask(Context context, OnApiCallbackListener onApiCallbackListener) {
        this.context = context;
        this.onApiCallbackListener = onApiCallbackListener;
        prefs = PreferencesHelper.getInstance(this.context);

    }

    @Override
    protected void onPreExecute() {
        onApiCallbackListener.onApiPreExecute(this);
    }

    @Override
    protected MasterResponse doInBackground(Void... params) {

        MasterResponse masterResponse = null;

        try{
            ApiService apiService = ServiceGenerator.getApiService(context);
            masterResponse = callApi(apiService,masterResponse);

            /*if(masterResponse != null && masterResponse.getOrderList() != null){
                prefs.putObject(Constants.PREF_ORDER,orderResponse);
            }*/

            Log.v(TAG,"masterResponse : " + masterResponse);
        }
        catch (Exception e){
            if(Constants.showStackTrace) e.printStackTrace();
        }

        return masterResponse;
    }

    private MasterResponse callApi(ApiService apiService,MasterResponse masterResponse) throws Exception {

        Call<MasterResponse> masterResponseCall = null;

        masterResponseCall = apiService.executeMasterList("bearer "+prefs.getSavedTokenResponse(context).getAccessToken());

        Response<MasterResponse> response = masterResponseCall.execute();
        if(response.isSuccessful()){
            if(response.body() != null){
                masterResponse = response.body();
                //fillData(masterResponse.getOrderList());
                masterResponse.setAction(Constants.ACTION_DO_NOTHING);

                Log.v(TAG,"MyObject masterResponse : " +masterResponse);
                //Log.v(TAG,"masterResponse printed : " + masterResponse);
                prefs.putObject(Constants.PREF_MASTER,masterResponse);
                prefs.putObject(Constants.PREF_ADD_SECTION,new AddSection(masterResponse.getAddSection()));
                prefs.putObject(Constants.PREF_SELECT_SECTION,masterResponse.getSelectSection());
                prefs.putObject(Constants.PREF_NAMED_TEMPLATES,masterResponse.getNamedTemplates());
                prefs.putObject(Constants.PREF_TEMPLATES,masterResponse.getTemplates());

                prefs.putBoolean(Constants.PREF_REQUEST_MASTER_AFTER_LOGIN,false);

                Log.v(TAG,"MyObject AddSection : " +prefs.getObject(Constants.PREF_ADD_SECTION, AddSection.class));
                Log.v(TAG,"MyObject SelectSection : " +prefs.getObject(Constants.PREF_SELECT_SECTION, SelectSection.class));
                Log.v(TAG,"MyObject NamedTemplates : " +prefs.getObject(Constants.PREF_NAMED_TEMPLATES, NamedTemplates.class));
                Log.v(TAG,"MyObject Templates : " +prefs.getObject(Constants.PREF_TEMPLATES, Templates.class));
            }
        }
        else{
            if(response.errorBody() != null){
                ResponseBody responseBody = response.errorBody();
                masterResponse = new Gson().fromJson(new String(responseBody.bytes()), MasterResponse.class);

                if(masterResponse != null && masterResponse.getMessage() != null
                        && !masterResponse.getMessage().isEmpty() && masterResponse.getMessage().startsWith("Authorization has been denied")){

                    //As we dont have any error code, we will directly compare error string.
                    Call<GetTokenResponse> getTokenResponseCall = apiService.executeRefreshToken(prefs.getSavedTokenResponse(context).getRefreshToken(),
                            "refresh_token",Constants.CLIENT_ID);

                    GetTokenResponse getTokenResponse;

                    Response<GetTokenResponse> getTokenResp = getTokenResponseCall.execute();
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            getTokenResponse = getTokenResp.body();
                            prefs.putGetTokenResponse(context,getTokenResponse);
                            masterResponse.setAction(Constants.ACTION_DO_NOTHING);
                            callApi(apiService,masterResponse);
                        }
                    }
                    else{
                        if(response.errorBody() != null){
                            responseBody = response.errorBody();
                            getTokenResponse = new Gson().fromJson(new String(responseBody.bytes()), GetTokenResponse.class);
                            masterResponse.setAction(Constants.ACTION_LOGIN_AGAIN);
                        }
                    }
                }
            }
        }

        return masterResponse;
    }

    @Override
    protected void onPostExecute(MasterResponse orderResponse) {

        onApiCallbackListener.onApiPostExecute(orderResponse,this);
    }

    /*private void fillData(List<OrderListItem> newList){

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
    }*/

}
