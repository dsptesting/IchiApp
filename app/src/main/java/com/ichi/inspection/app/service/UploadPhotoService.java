package com.ichi.inspection.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UploadPhotoService extends Service {
    public UploadPhotoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
