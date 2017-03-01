package com.ichi.inspection.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Palak on 07-01-2017.
 */
//TODO class is copied as helper class. use params which are comman in webcall response
public class BaseResponse implements Parcelable {

    @SerializedName("result_code")
    public int resultCode;

    @SerializedName("msg")
    public String msg;

    public BaseResponse() {
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "resultCode=" + resultCode +
                ", msg='" + msg + '\'' +
                '}';
    }

    protected BaseResponse(Parcel in) {
        resultCode = in.readInt();
        msg = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resultCode);
        dest.writeString(msg);
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}