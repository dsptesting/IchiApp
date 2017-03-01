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

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = SplashFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.btnLogin)
    CustomButton btnLogin;

    @Nullable @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initData();

        return view;
    }

    private void initData() {

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                ((StartActivity)getActivity()).navigateToScreen(Constants.LOG_IN, null, true);
                break;
        }
    }
}
