package com.ichi.inspection.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import com.ichi.inspection.app.utils.PreferencesHelper;

/**
 * Created by Palak on 14-01-2017.
 */

public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    PreferencesHelper prefs;
    //DbHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferencesHelper.getInstance(getActivity());
        //dbHelper = DbHelper.getInstance(getActivity());
    }

    protected void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
