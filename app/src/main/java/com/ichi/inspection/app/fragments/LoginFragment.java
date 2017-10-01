package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.MainActivity;
import com.ichi.inspection.app.activities.StartActivity;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.GetTokenResponse;
import com.ichi.inspection.app.models.SignInRequest;
import com.ichi.inspection.app.task.LoginAsyncTask;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.CustomButton;
import com.ichi.inspection.app.utils.CustomEditText;
import com.ichi.inspection.app.utils.CustomTextView;
import com.ichi.inspection.app.utils.Utils;
import com.ichi.inspection.app.utils.ValidateHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mayank on 05-03-2017.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener, OnApiCallbackListener {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.etEmail)
    CustomEditText etEmail;

    @BindView(R.id.etPassword)
    CustomEditText etPassword;

    @BindView(R.id.btnLogin)
    CustomTextView btnLogin;

    @BindView(R.id.txtForgetPassword)
    CustomTextView txtForgetPassword;

    @Nullable @BindView(R.id.pbLoader)
    ProgressBar pbLoader;

    @BindView(R.id.cvLogin)
    CardView cvLogin;

    private LoginAsyncTask loginAsyncTask;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initData();

        if(prefs.getSavedTokenResponse(getActivity()) != null){
            if(!Utils.isNetworkAvailable(getActivity())){
                goToHomeActivity();
            }
            else{
                GetTokenResponse getTokenResponse = prefs.getSavedTokenResponse(getActivity());
                SignInRequest signInRequest = new SignInRequest();
                signInRequest.setUserName(getTokenResponse.getLoginData().getUserName().toString());
                signInRequest.setPassword(getTokenResponse.getLoginData().getPassword().toString());

                loginAsyncTask = new LoginAsyncTask(getActivity(),this);
                loginAsyncTask.execute(signInRequest);
            }
        }

        return view;
    }

    private void initData() {

        btnLogin.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);

        /*etEmail.setText("john.allen.427@gmail.com");
        etPassword.setText("Admin@123");*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if(validate()){
                    if(!Utils.isNetworkAvailable(getActivity())){
                        Utils.showSnackBar(coordinatorLayout,getString(R.string.internet_not_avail));
                        return;
                    }

                    SignInRequest signInRequest = new SignInRequest();
                    signInRequest.setUserName(etEmail.getText().toString());
                    signInRequest.setPassword(etPassword.getText().toString());

                    loginAsyncTask = new LoginAsyncTask(getActivity(),this);
                    loginAsyncTask.execute(signInRequest);
                }
                break;
            case R.id.txtForgetPassword:
                ((StartActivity)getActivity()).navigateToScreen(Constants.FORGET_PASSWORD,null,true);
                break;
        }

    }

    private boolean validate() {

       /* if(!ValidateHelper.validateEmail(etEmail)){
            Utils.showSnackBar(coordinatorLayout,"Please enter Username");
            return false;
        }*/
        if(!ValidateHelper.validateEditText(etEmail)){
            Utils.showSnackBar(coordinatorLayout,"Please enter Username or Password");
            return false;
        }
        if(!ValidateHelper.validateEditText(etPassword)){
            Utils.showSnackBar(coordinatorLayout,"Please enter Username or Password");
            return false;
        }

        return true;
    }

    @Override
    public void onApiPreExecute(AsyncTask asyncTask) {
        pbLoader.setVisibility(View.VISIBLE);
        cvLogin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onApiPostExecute(BaseResponse baseResponse, AsyncTask asyncTask) {

        //Utils.showCallError(coordinatorLayout,baseResponse);
        pbLoader.setVisibility(View.GONE);
        cvLogin.setVisibility(View.VISIBLE);
        /*if(Utils.hasCallErrorInBackground(baseResponse)){
            cvLogin.setVisibility(View.VISIBLE);
        }
        else */
        if(baseResponse != null){
            if(asyncTask instanceof LoginAsyncTask){
                GetTokenResponse getTokenResponse = (GetTokenResponse) baseResponse;
                if(getTokenResponse.getError() != null && !getTokenResponse.getError().isEmpty()){
                    Utils.showSnackBar(coordinatorLayout,"Invalid Username or Password.");
                }
                else{
                    if(getTokenResponse.getAccessToken() != null && !getTokenResponse.getAccessToken().trim().isEmpty()){
                        prefs.putGetTokenResponse(getActivity(),getTokenResponse);
                        goToHomeActivity();
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        if(loginAsyncTask != null && !loginAsyncTask.isCancelled()) loginAsyncTask.cancel(true);
        super.onDestroy();
    }

    private void goToHomeActivity(){
        prefs.putBoolean(Constants.PREF_REQUEST_MASTER_AFTER_LOGIN,true);
        Intent intent = new Intent(getActivity() , MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
