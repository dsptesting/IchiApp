package com.ichi.inspection.app.rest;

import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.GetTokenResponse;
import com.ichi.inspection.app.models.MasterResponse;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.utils.Constants;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST(Constants.URL_LOGIN)
    Call<GetTokenResponse> executeLogin(@Field("Username") String username, @Field("password") String password,
                                        @Field("grant_type") String grant_type,@Field("client_id") String client_id);

    @GET(Constants.URL_ORDER_LIST)
    Call<OrderResponse> executeOrderList(@Header("Authorization") String authorization);

    @GET(Constants.URL_MASTER_LIST)
    Call<MasterResponse> executeMasterList(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Constants.URL_LOGIN)
    Call<GetTokenResponse> executeRefreshToken(@Field("refresh_token") String refresh_token, @Field("grant_type") String grant_type,
                                               @Field("client_id") String client_id);

    @GET(Constants.URL_SAVE_LIST)
    Call<BaseResponse> executeSaveList(@Header("Authorization") String authorization, @Body JSONObject jsonObject);
/*
    @FormUrlEncoded
    @POST(Constants.URL_CHECK_VCARD_ID)
    Call<BaseResponse> executeCheckVCardId(@Field("vcard_id") String vcard_id);

    @FormUrlEncoded
    @POST(Constants.URL_GET_TOKEN)
    Call<GetTokenResponse> executeGetToken(@Field("grant_type") String grant_type, @Field("client_id") String client_id,
                                           @Field("client_secret") String client_secret, @Field("scope") String scope);

    @FormUrlEncoded
    @POST(Constants.URL_REGISTER)
    Call<RegisterResponse> executeRegister(@Header("Authorization") String authorization, @Field("vcard_id") String vcard_id, @Field("account_password") String account_password,
                                           @Field("phone_country") String phone_country, @Field("phone_number") String phone_number,
                                           @Field("device_identifier") String device_identifier, @Field("manufacturer_device_code") String manufacturer_device_code);

    @FormUrlEncoded
    @POST(Constants.URL_VERIFY)
    Call<VerifyResponse> executeVerify(@Header("Authorization") String authorization, @Field("vcard_id") String vcardId, @Field("registration_id") String registrationId, @Field("verification_code") String verificationCode);

    @FormUrlEncoded
    @POST(Constants.URL_REGISTER_DETAILS)
    Call<VerifyResponse> executeRegisterDetails(@Header("Authorization") String authorization, @Field("email_address") String emailId, @Field("first_name") String firstName, @Field("last_name") String lastName);

    @POST(Constants.URL_MUTATION_CREATE_ACCOUNT)
    Call<CreateAccountResponseParser> executeCreateAccount(@Header("Authorization") String authorization, @Body RequestBody body);

    @POST
    Call<CreateAccountResponseParser> queueManager(@Url String url, @Header("Authorization") String authorization, @Body RequestBody body);*/
}