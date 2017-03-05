package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.StartActivity;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.CustomButton;
import com.ichi.inspection.app.utils.CustomEditText;
import com.ichi.inspection.app.utils.CustomTextView;
import com.ichi.inspection.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mayank on 05-03-2017.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.etEmail)
    CustomEditText etEmail;

    @BindView(R.id.etPassword)
    CustomEditText etPassword;

    @BindView(R.id.btnLogin)
    CustomButton btnLogin;

    @BindView(R.id.txtForgetPassword)
    CustomTextView txtForgetPassword;


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

        return view;
    }

    private void initData() {

        btnLogin.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Utils.showSnackBar(coordinatorLayout,getResources().getString(R.string.app_name));
                break;
            case R.id.txtForgetPassword:
                ((StartActivity)getActivity()).navigateToScreen(Constants.FORGET_PASSWORD,null,true);
                break;
        }

    }
}
