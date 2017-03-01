package com.ichi.inspection.app.interfaces;

import android.os.AsyncTask;

import com.ichi.inspection.app.models.BaseResponse;


/**
 * Created by Palak on 08-01-2017.
 */

public interface OnApiCallbackListener {

    void onApiPreExecute(AsyncTask asyncTask);
    void onApiPostExecute(BaseResponse baseResponse, AsyncTask asyncTask);
}
