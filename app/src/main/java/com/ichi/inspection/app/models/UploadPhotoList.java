package com.ichi.inspection.app.models;

import java.util.List;

/**
 * Created by Palak on 16-07-2017.
 */

public class UploadPhotoList {

    private List<Photo> photos;

    @Override
    public String toString() {
        return "UploadPhotoList{" +
                "photos=" + photos +
                '}';
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
