package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.MainActivity;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.CustomEditText;

import java.util.List;

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

    @BindView(R.id.txtOrderNo)
    public TextView txtOrderNo;

    @BindView(R.id.etAddress)
    CustomEditText etAddress;

    @BindView(R.id.etCity)
    CustomEditText etCity;

    @BindView(R.id.etState)
    CustomEditText etState;

    @BindView(R.id.etZip)
    CustomEditText etZip;

    @BindView(R.id.etCrossStreets)
    CustomEditText etCrossStreets;

    @BindView(R.id.etBuildingType)
    CustomEditText etBuildingType;

    @BindView(R.id.etSquareFootage)
    CustomEditText etSquareFootage;

    @BindView(R.id.etYearBuilt)
    CustomEditText etYearBuilt;

    @BindView(R.id.etOccupied)
    CustomEditText etOccupied;

    @BindView(R.id.etBedRooms)
    CustomEditText etBedRooms;

    @BindView(R.id.etBathRooms)
    CustomEditText etBathRooms;

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

    private OrderListItem orderListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_propertyinfo, container, false);
        ButterKnife.bind(this, view);
        orderListItem = getArguments().getParcelable(Constants.INTENT_SELECTED_ORDER);
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
        /*final Drawable upArrow = ContextCompat.getDrawable(getActivity(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getActivity(),R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);*/

        tvAppTitle.setText(R.string.property_info);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if(orderListItem != null){
            txtOrderNo.setText(txtOrderNo.getText().toString()+orderListItem.getIONum());
            etAddress.setCustomText(orderListItem.getPropertyAddress());
            etCity.setCustomText(orderListItem.getPropertyCity());
            etState.setCustomText(orderListItem.getPropertyState());
            etZip.setCustomText(orderListItem.getPropertyZip());
            etCrossStreets.setCustomText(orderListItem.getCrossStreets());
            etBuildingType.setCustomText(orderListItem.getBuildingType());
            etSquareFootage.setCustomText(orderListItem.getSqFoot()+"");
            etYearBuilt.setCustomText(orderListItem.getYearBuilt()+"");
            etOccupied.setCustomText(orderListItem.getYearBuilt()+"");
            etBedRooms.setCustomText(orderListItem.getBedRooms()+"");
            etBathRooms.setCustomText(orderListItem.getBathRooms()+"");
            if (orderListItem.isUtilPower())
                cbPower.setChecked(true);

            if (orderListItem.isUtilGas())
                cbGas.setChecked(true);

            if (orderListItem.isUtilWater())
                cbWater.setChecked(true);

            if (orderListItem.isUtilOther())
                cbOtherUtility.setChecked(true);

            cbPower.setClickable(false);
            cbGas.setClickable(false);
            cbWater.setClickable(false);
            cbOtherUtility.setClickable(false);
        }


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
