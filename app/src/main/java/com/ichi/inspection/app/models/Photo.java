package com.ichi.inspection.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank on 27-06-2017.
 */

public class Photo {

    @SerializedName("InspectionId") @Expose
    public String InspectionId;

    @SerializedName("LineId") @Expose
    public String LineId;

    @SerializedName("PhotoName") @Expose
    public String PhotoName;

    @SerializedName("PhotoPath") @Expose
    public String PhotoPath;

    public int uploadStatus;

    public Photo(String InspectionId,String LineId,String PhotoName, String PhotoPath){
        this.InspectionId=InspectionId;
        this.LineId=LineId;
        this.PhotoName=PhotoName;
        this.PhotoPath=PhotoPath;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "InspectionId='" + InspectionId + '\'' +
                ", LineId='" + LineId + '\'' +
                ", PhotoName='" + PhotoName + '\'' +
                ", PhotoPath='" + PhotoPath + '\'' +
                ", uploadStatus=" + uploadStatus +
                '}';
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getInspectionId() {
        return InspectionId;
    }

    public void setInspectionId(String inspectionId) {
        InspectionId = inspectionId;
    }

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public String getPhotoName() {
        return PhotoName;
    }


    public void setPhotoName(String photoName) {
        PhotoName = photoName;
    }
}

