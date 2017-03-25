package com.ichi.inspection.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Palak on 07-01-2017.
 */
public class BaseResponse implements Parcelable {

    @SerializedName("result_code")
    public int resultCode;

    @SerializedName("error")
    public String error;

    public BaseResponse() {
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "resultCode=" + resultCode +
                ", error='" + error + '\'' +
                '}';
    }

    public BaseResponse(int resultCode, String error) {
        this.resultCode = resultCode;
        this.error = error;
    }

    protected BaseResponse(Parcel in) {
        resultCode = in.readInt();
        error = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resultCode);
        dest.writeString(error);
    }

    @SuppressWarnings("unused")
    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel in) {
            return new BaseResponse(in);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}