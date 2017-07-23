package com.ichi.inspection.app.utils;

import com.ichi.inspection.app.models.Photo;

/**
 * Created by Palak on 16-07-2017.
 */

public class Events {

    public static class UploadPhotoStarted{
        public UploadPhotoStarted() {
        }
    }

    public static class UploadPhotoStartedUploading{
        Photo photo;
        public UploadPhotoStartedUploading(Photo photo) {
            this.photo = photo;
        }

        public Photo getPhoto() {
            return photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }
    }


    public static class UploadPhotoRemoved{
        Photo photo;
        public UploadPhotoRemoved(Photo photo) {
            this.photo = photo;
        }

        public Photo getPhoto() {
            return photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }
    }
}
