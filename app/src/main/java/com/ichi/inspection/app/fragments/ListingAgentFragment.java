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

public class ListingAgentFragment extends BaseFragment{

    private static final String TAG = ListingAgentFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.txtOrderNo)
    public TextView txtOrderNo;

    @BindView(R.id.etAgentName)
    CustomEditText etAgentName;

    @BindView(R.id.etAgencyName)
    CustomEditText etAgencyName;

    @BindView(R.id.etPhone)
    CustomEditText etPhone;

    @BindView(R.id.etEmail)
    CustomEditText etEmail;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private OrderListItem orderListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listingagent, container, false);
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

        tvAppTitle.setText(R.string.listing_agent);
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
            etAgentName.setCustomText(orderListItem.getListingAgentName());
            etAgencyName.setCustomText(orderListItem.getListingAgentAgencyName());
            if (!(orderListItem.getListingAgentPhone()==null||orderListItem.getListingAgentPhone().equals(""))){
            etPhone.setCustomText(orderListItem.getListingAgentPhone().substring(0,orderListItem.getListingAgentPhone().indexOf("<")));}
            else{
                etPhone.setCustomText("-");
            }
            if (!(orderListItem.getListingAgentEmail()==null||orderListItem.getListingAgentEmail().equals(""))){
            etEmail.setCustomText(orderListItem.getListingAgentEmail().substring(0,orderListItem.getListingAgentEmail().indexOf("<")));}
            else{
                etEmail.setCustomText("-");
            }
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
