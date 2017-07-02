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

    public Photo(String InspectionId,String LineId,String PhotoName){
        this.InspectionId=InspectionId;
        this.LineId=LineId;
        this.PhotoName=PhotoName;
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
