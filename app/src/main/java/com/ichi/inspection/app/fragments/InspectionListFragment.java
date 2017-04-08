package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.MainActivity;
import com.ichi.inspection.app.adapters.InspectionAdapter;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.interfaces.OnListItemClickListener;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.task.OrderAsyncTask;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 05-03-2017.
 */

public class InspectionListFragment extends BaseFragment implements View.OnClickListener, OnApiCallbackListener, OnListItemClickListener {

    private static final String TAG = InspectionListFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.rvInspectionList)
    RecyclerView rvInspectionList;

    @Nullable @BindView(R.id.pbLoader)
    ProgressBar pbLoader;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private InspectionAdapter inspectionAdapter;
    private List<OrderListItem> alInspections;

    private OrderAsyncTask orderAsyncTask;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private List<OrderListItem> tempOrderListItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inspectionlist, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initData();
        getInspectionList();

        return view;
    }

    private void initData() {

        alInspections = new ArrayList<>();

        //Toolbar shit!
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        final Drawable upArrow = ContextCompat.getDrawable(getActivity(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getActivity(),R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);

        tvAppTitle.setText(R.string.inspections_title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        inspectionAdapter = new InspectionAdapter(getActivity(),alInspections,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvInspectionList.setLayoutManager(linearLayoutManager);

        rvInspectionList.setAdapter(inspectionAdapter);

    }

    private void getInspectionList(){

        tempOrderListItems = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getOrderList();

        if(tempOrderListItems != null && !tempOrderListItems.isEmpty()){
            inspectionAdapter.setData(tempOrderListItems);
        }

        if(Utils.isNetworkAvailable(getActivity())){
            orderAsyncTask = new OrderAsyncTask(getActivity(),this);
            orderAsyncTask.execute();
        }
        else{
            pbLoader.setVisibility(View.GONE);
            tvNoData.setText(getString(R.string.internet_not_avail));
            tvNoData.setVisibility(View.VISIBLE);
            rvInspectionList.setVisibility(View.VISIBLE);
            Utils.showSnackBar(coordinatorLayout,getString(R.string.internet_not_avail));
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onApiPreExecute(AsyncTask asyncTask) {
        if(tempOrderListItems == null) pbLoader.setVisibility(View.VISIBLE);
        if(tempOrderListItems != null && tempOrderListItems.isEmpty()) pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onApiPostExecute(BaseResponse baseResponse, AsyncTask asyncTask) {

        pbLoader.setVisibility(View.GONE);
        if(!Utils.showCallError(coordinatorLayout,baseResponse)){
            OrderResponse orderResponse = (OrderResponse) baseResponse;
            if(orderResponse.getOrderList() != null && !orderResponse.getOrderList().isEmpty()){
                rvInspectionList.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.INVISIBLE);
                inspectionAdapter.setData(orderResponse.getOrderList());
            }
            else{
                tvNoData.setText(getString(R.string.str_no_data));
                tvNoData.setVisibility(View.VISIBLE);
                rvInspectionList.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        if(orderAsyncTask != null && !orderAsyncTask.isCancelled()) orderAsyncTask.cancel(true);
        super.onDestroy();
    }

    @Override
    public void onListItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.rlContainer:
                Log.v(TAG,"Position: " + position);
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.INTENT_POSITION, position);
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_NAVIGATION, bundle, true);
                break;
        }
    }
}
