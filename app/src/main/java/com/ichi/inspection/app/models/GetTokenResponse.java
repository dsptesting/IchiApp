package com.ichi.inspection.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class GetTokenResponse extends BaseResponse implements Parcelable {

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("expires_in")
    @Expose
    private int expiresIn;

    private SignInRequest loginData;

    public SignInRequest getLoginData() {
        return loginData;
    }

    public void setLoginData(SignInRequest loginData) {
        this.loginData = loginData;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public void setError(String error) {
        this.error = error;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken(){
        return refreshToken;
    }

    public void setTokenType(String tokenType){
        this.tokenType = tokenType;
    }

    public String getTokenType(){
        return tokenType;
    }

    public void setExpiresIn(int expiresIn){
        this.expiresIn = expiresIn;
    }

    public int getExpiresIn(){
        return expiresIn;
    }

    @Override
    public String toString() {
        return "GetTokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", error=" + error +
                ", loginData=" + loginData +
                '}';
    }

    public GetTokenResponse() {
    }

    protected GetTokenResponse(Parcel in) {
        accessToken = in.readString();
        refreshToken = in.readString();
        tokenType = in.readString();
        error = in.readString();
        expiresIn = in.readInt();
        loginData = (SignInRequest) in.readValue(SignInRequest.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessToken);
        dest.writeString(refreshToken);
        dest.writeString(tokenType);
        dest.writeInt(expiresIn);
        dest.writeValue(loginData);
        dest.writeValue(error);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GetTokenResponse> CREATOR = new Parcelable.Creator<GetTokenResponse>() {
        @Override
        public GetTokenResponse createFromParcel(Parcel in) {
            return new GetTokenResponse(in);
        }

        @Override
        public GetTokenResponse[] newArray(int size) {
            return new GetTokenResponse[size];
        }
    };
}