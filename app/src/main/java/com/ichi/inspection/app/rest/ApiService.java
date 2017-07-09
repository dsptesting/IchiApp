package com.ichi.inspection.app.rest;

import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.GetTokenResponse;
import com.ichi.inspection.app.models.MasterResponse;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.models.UploadPhoto;
import com.ichi.inspection.app.utils.Constants;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

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

    @POST(Constants.URL_SAVE_LIST)
    Call<BaseResponse> executeSaveList(@Header("Authorization") String authorization, @Body RequestBody jsonObject);

    @Multipart
    @POST
    Call<UploadPhoto> uploadVideoToServer(@Url String url, @Header("Authorization") String authorization, @Part MultipartBody.Part file);
}