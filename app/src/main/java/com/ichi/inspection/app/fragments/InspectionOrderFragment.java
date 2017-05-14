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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.MainActivity;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 05-03-2017.
 */

public class InspectionOrderFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = InspectionOrderFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.rlClientInfo)
    RelativeLayout rlClientInfo;

    @BindView(R.id.rlBuyersAgent)
    RelativeLayout rlBuyersAgent;

    @BindView(R.id.rlLinkingAgent)
    RelativeLayout rlLinkingAgent;

    @BindView(R.id.rlPropertyInfo)
    RelativeLayout rlPropertyInfo;

    @BindView(R.id.rlScheduler)
    RelativeLayout rlScheduler;

    @BindView(R.id.rlFees)
    RelativeLayout rlFees;

    @BindView(R.id.rlSpecialInstruction)
    RelativeLayout rlSpecialInstruction;

    @BindView(R.id.rlPayment)
    RelativeLayout rlPayment;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private OrderListItem orderListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inspection_order, container, false);
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
        /*final Drawable upArrow = ContextCompat.getDrawable(getActivity(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getActivity(),R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);*/

        tvAppTitle.setText(R.string.inspection_order);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        orderListItem = getArguments().getParcelable(Constants.INTENT_SELECTED_ORDER);

        rlClientInfo.setOnClickListener(this);
        rlBuyersAgent.setOnClickListener(this);
        rlLinkingAgent.setOnClickListener(this);
        rlPropertyInfo.setOnClickListener(this);
        rlScheduler.setOnClickListener(this);
        rlFees.setOnClickListener(this);
        rlSpecialInstruction.setOnClickListener(this);
        rlPayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_SELECTED_ORDER, orderListItem);
        switch (view.getId()){
            case R.id.rlClientInfo:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_CLIENT_INFO, bundle, true);
                break;
            case R.id.rlBuyersAgent:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_BUYERS_AGENT, bundle, true);
                break;
            case R.id.rlLinkingAgent:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_LINKING_AGENT, bundle, true);
                break;
            case R.id.rlPropertyInfo:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_PROPERTY_INFO, bundle, true);
                break;
            case R.id.rlScheduler:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_SCHEDULER, bundle, true);
                break;
            case R.id.rlFees:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_FEES, bundle, true);
                break;
            case R.id.rlSpecialInstruction:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_SPECIAL_INSTRUCTION, bundle, true);
                break;
            case R.id.rlPayment:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_PAYMENT, bundle, true);
                break;
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
