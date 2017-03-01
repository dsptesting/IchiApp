package com.ichi.inspection.app.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ichi.inspection.app.utils.PreferencesHelper;

public class BaseActivity extends AppCompatActivity {

    PreferencesHelper prefs;
    //DbHelper dbHelper;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dbHelper = DbHelper.getInstance(this);
        prefs = PreferencesHelper.getInstance(this);
    }

    protected void showProgressDialog(String title, String message){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }

        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle(title);

        progressDialog.setMessage(message);

        progressDialog.setCancelable(false);

        progressDialog.show();
    }

    protected void cancelProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }
}
