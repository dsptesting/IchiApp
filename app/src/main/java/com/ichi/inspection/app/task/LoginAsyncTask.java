package com.ichi.inspection.app.task;

import android.content.Context;
import android.os.AsyncTask;

import com.ichi.inspection.app.interfaces.OnApiCallbackListener;

/**
 * Created by Palak on 05-03-2017.
 */

public class LoginAsyncTask extends AsyncTask<Void,Void,Void> {

    private Context context;
    private OnApiCallbackListener onApiCallbackListener;

    public LoginAsyncTask(Context context, OnApiCallbackListener onApiCallbackListener) {
        this.context = context;
        this.onApiCallbackListener = onApiCallbackListener;
    }

    @Override
    protected void onPreExecute() {
        onApiCallbackListener.onApiPreExecute(this);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        onApiCallbackListener.onApiPostExecute();

    }
}
