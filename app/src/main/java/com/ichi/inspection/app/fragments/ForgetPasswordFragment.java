package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.utils.CustomEditText;
import com.ichi.inspection.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mayank on 05-03-2017.
 */

public class ForgetPasswordFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.etEmail)
    CustomEditText etEmail;

    @BindView(R.id.cvSubmit)
    CardView cvSubmit;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forgetpassword, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initData();

        return view;
    }

    private void initData() {
        cvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cvSubmit:
                Utils.showSnackBar(coordinatorLayout,getResources().getString(R.string.app_name));
                break;
        }

    }
}
