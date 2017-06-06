package com.ichi.inspection.app.models;

/**
 * Created by Palak on 05-06-2017.
 */

public class OrderUpdates {

    private String inspectionId;
    private boolean isUpdated;

    @Override
    public String toString() {
        return "OrderUpdates{" +
                "inspectionId='" + inspectionId + '\'' +
                ", isUpdated=" + isUpdated +
                '}';
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}
