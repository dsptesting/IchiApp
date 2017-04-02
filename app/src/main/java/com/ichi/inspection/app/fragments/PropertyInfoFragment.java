package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 05-03-2017.
 */

public class PropertyInfoFragment extends BaseFragment{

    private static final String TAG = PropertyInfoFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.etAddress)
    EditText etAddress;

    @BindView(R.id.etCity)
    EditText etCity;

    @BindView(R.id.etState)
    EditText etState;

    @BindView(R.id.etZip)
    EditText etZip;

    @BindView(R.id.etCrossStreets)
    EditText etCrossStreets;

    @BindView(R.id.etBuildingType)
    EditText etBuildingType;

    @BindView(R.id.etSquareFootage)
    EditText etSquareFootage;

    @BindView(R.id.etYearBuilt)
    EditText etYearBuilt;

    @BindView(R.id.etOccupied)
    EditText etOccupied;

    @BindView(R.id.etBedRooms)
    EditText etBedRooms;

    @BindView(R.id.etBathRooms)
    EditText etBathRooms;

    @BindView(R.id.cbPower)
    CheckBox cbPower;

    @BindView(R.id.cbWater)
    CheckBox cbWater;

    @BindView(R.id.cbGas)
    CheckBox cbGas;

    @BindView(R.id.cbOtherUtility)
    CheckBox cbOtherUtility;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_propertyinfo, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        mContext = getActivity();
        initData();

        return view;
    }

    private void initData() {

        //Toolbar shit!
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        final Drawable upArrow = ContextCompat.getDrawable(getActivity(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getActivity(),R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);

        tvAppTitle.setText(R.string.property_info);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        etAddress.setText("abcd");
        etCity.setText("abcd");
        etState.setText("abcd");
        etZip.setText("abcd");
        etCrossStreets.setText("abcd");
        etBuildingType.setText("abcd");
        etSquareFootage.setText("abcd");
        etYearBuilt.setText("abcd");
        etOccupied.setText("abcd");
        etBedRooms.setText("abcd");
        etBathRooms.setText("abcd");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inspection_logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout){
            ((MainActivity) getActivity()).logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
