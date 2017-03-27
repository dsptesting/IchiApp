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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.adapters.InspectionAdapter;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mayank on 05-03-2017.
 */

public class InspectionListFragment extends BaseFragment implements View.OnClickListener, OnApiCallbackListener {

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
    private List<String> alInspections;

    //private GetInspectionListAsyncTask getInspectionListAsyncTask;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

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
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");
        alInspections.add("a");

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

        inspectionAdapter = new InspectionAdapter(getActivity(),alInspections);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvInspectionList.setLayoutManager(linearLayoutManager);

        rvInspectionList.setAdapter(inspectionAdapter);
    }

    private void getInspectionList(){

        if(!Utils.isNetworkAvailable(getActivity())){
            pbLoader.setVisibility(View.GONE);
            tvNoData.setText(getString(R.string.internet_not_avail));
            tvNoData.setVisibility(View.VISIBLE);
            rvInspectionList.setVisibility(View.GONE);
            Utils.showSnackBar(coordinatorLayout,getString(R.string.internet_not_avail));
            return;
        }

        /*getInspectionListAsyncTask = new GetInspectionListAsyncTask(getActivity(),this);
        getInspectionListAsyncTask.execute();*/
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
        pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onApiPostExecute(BaseResponse baseResponse, AsyncTask asyncTask) {

        /*pbLoader.setVisibility(View.GONE);
        if(!Utils.showCallError(coordinatorLayout,baseResponse)){
            BookingResponse bookingResponse = (BookingResponse) baseResponse;
            if(bookingResponse.bookings != null && !bookingResponse.bookings.isEmpty()){
                recyclerView.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.INVISIBLE);
                bookingAdapter.setData(bookingResponse.bookings);
            }
            else{
                tvNoData.setText(getString(R.string.str_no_data));
                tvNoData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }
        }*/
    }

    @Override
    public void onDestroy() {
        //if(getInspectionListAsyncTask != null && !getInspectionListAsyncTask.isCancelled()) getInspectionListAsyncTask.cancel(true);
        super.onDestroy();
    }
}
