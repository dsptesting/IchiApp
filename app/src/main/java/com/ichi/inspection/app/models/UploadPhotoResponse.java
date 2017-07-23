package com.ichi.inspection.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Palak on 22-07-2017.
 */

public class UploadPhotoResponse extends BaseResponse {

    @SerializedName("Result")
    @Expose
    private List<UploadPhotoResult> Result;

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("Exception")
    @Expose
    private String Exception;

    @SerializedName("Status")
    @Expose
    private int Status;

    @SerializedName("IsCanceled")
    @Expose
    private boolean IsCanceled;

    @SerializedName("IsCompleted")
    @Expose
    private boolean IsCompleted;

    @SerializedName("CreationOptions")
    @Expose
    private int CreationOptions;

    @SerializedName("AsyncState")
    @Expose
    private String AsyncState;

    @SerializedName("IsFaulted")
    @Expose
    private boolean IsFaulted;

    @Override
    public String toString() {
        return "UploadPhotoResponse{" +
                "Result=" + Result +
                ", Id=" + Id +
                ", Exception='" + Exception + '\'' +
                ", Status=" + Status +
                ", IsCanceled=" + IsCanceled +
                ", IsCompleted=" + IsCompleted +
                ", CreationOptions=" + CreationOptions +
                ", AsyncState='" + AsyncState + '\'' +
                ", IsFaulted=" + IsFaulted +
                '}';
    }

    public List<UploadPhotoResult> getResult() {
        return Result;
    }

    public void setResult(List<UploadPhotoResult> result) {
        Result = result;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        Exception = exception;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public boolean isCanceled() {
        return IsCanceled;
    }

    public void setCanceled(boolean canceled) {
        IsCanceled = canceled;
    }

    public boolean isCompleted() {
        return IsCompleted;
    }

    public void setCompleted(boolean completed) {
        IsCompleted = completed;
    }

    public int getCreationOptions() {
        return CreationOptions;
    }

    public void setCreationOptions(int creationOptions) {
        CreationOptions = creationOptions;
    }

    public String getAsyncState() {
        return AsyncState;
    }

    public void setAsyncState(String asyncState) {
        AsyncState = asyncState;
    }

    public boolean isFaulted() {
        return IsFaulted;
    }

    public void setFaulted(boolean faulted) {
        IsFaulted = faulted;
    }
}
