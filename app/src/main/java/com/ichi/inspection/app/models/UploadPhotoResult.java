package com.ichi.inspection.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Palak on 22-07-2017.
 */

public class UploadPhotoResult {

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Path")
    @Expose
    private String Path;

    @SerializedName("Size")
    @Expose
    private int Size;

    @Override
    public String toString() {
        return "UploadPhotoResult{" +
                "Name='" + Name + '\'' +
                ", Path='" + Path + '\'' +
                ", Size=" + Size +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }
}
