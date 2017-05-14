package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentFragment extends BaseFragment implements View.OnClickListener, OnApiCallbackListener, OnListItemClickListener {
    private static final String TAG = CurrentFragment.class.getSimpleName();
    private Context mContext;
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_LOCALTIME);

    @BindView(R.id.rvInspectionList)
    RecyclerView rvInspectionList;

    @Nullable
    @BindView(R.id.pbLoader)
    ProgressBar pbLoader;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private InspectionAdapter inspectionAdapter;
    private List<OrderListItem> alInspections;

    private OrderAsyncTask orderAsyncTask;

    private List<OrderListItem> tempOrderListItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initData();
        getInspectionList();
        return view;
    }
    private void initData() {

        alInspections = new ArrayList<>();

        inspectionAdapter = new InspectionAdapter(getActivity(),alInspections,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvInspectionList.setLayoutManager(linearLayoutManager);

        rvInspectionList.setAdapter(inspectionAdapter);

    }

    private void fillData(List<OrderListItem> list){

        alInspections.clear();
        for(OrderListItem orderListItem: list){
            try {
                long time = sdf.parse(orderListItem.getTimeStamp()).getTime();
                if(Utils.isCurrentDay(time)) {
                    alInspections.add(orderListItem);
                }
            }
            catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }

        if(alInspections != null && !alInspections.isEmpty()){
            inspectionAdapter.setData(alInspections);
        }
        else{
            pbLoader.setVisibility(View.GONE);
            if(Utils.isNetworkAvailable(getActivity())){
                tvNoData.setText(getString(R.string.str_no_data));
            }
            else{
                tvNoData.setText(getString(R.string.internet_not_avail));
                Utils.showSnackBar(coordinatorLayout,getString(R.string.internet_not_avail));
            }
            tvNoData.setVisibility(View.VISIBLE);
            rvInspectionList.setVisibility(View.VISIBLE);
        }
    }

    private void getInspectionList(){

        if(Utils.isNetworkAvailable(getActivity())){
            orderAsyncTask = new OrderAsyncTask(getActivity(),this);
            orderAsyncTask.execute();
        }

        if(prefs.contains(Constants.PREF_ORDER)){

            tempOrderListItems = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getOrderList();
            fillData(tempOrderListItems);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onListItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.rlContainer:
                Log.v(TAG,"Position: " + position);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.INTENT_SELECTED_ORDER, alInspections.get(position));
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_NAVIGATION, bundle, true);
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

                if(prefs.contains(Constants.PREF_ORDER)){

                    fillData(orderResponse.getOrderList());
                }
            }
        }
    }
    @Override
    public void onDestroy() {
        if(orderAsyncTask != null && !orderAsyncTask.isCancelled()) orderAsyncTask.cancel(true);
        super.onDestroy();
    }
}