package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.MainActivity;
import com.ichi.inspection.app.activities.StartActivity;
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

/**
 * Created by Palak on 05-03-2017.
 */

public class InspectionListFragment extends BaseFragment implements View.OnClickListener, OnApiCallbackListener, OnListItemClickListener{

    private static final String TAG = InspectionListFragment.class.getSimpleName();
    private Context mContext;
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_LOCALTIME);

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.rcvCurrent)
    RecyclerView rcvCurrent;

    @BindView(R.id.rcvPending)
    RecyclerView rcvPending;

    @BindView(R.id.tvCurrent)
    TextView tvCurrent;

    @BindView(R.id.tvPending)
    TextView tvPending;

    @BindView(R.id.viewCurrent)
    View viewCurrent;

    @BindView(R.id.viewPending)
    View viewPending;

    @Nullable @BindView(R.id.pbLoader)
    ProgressBar pbLoader;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private InspectionAdapter currentAdapter;
    private InspectionAdapter pendingAdapter;

    private List<OrderListItem> alInspections;
    private List<OrderListItem> alCurrent;
    private List<OrderListItem> alPending;

    private boolean isCurrentSelected = true;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private OrderAsyncTask orderAsyncTask;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inspectionlist, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initData();
        setHasOptionsMenu(true);

        getInspectionList();

        return view;
    }

    private void initData() {

        //Toolbar shit!
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        tvAppTitle.setText(R.string.inspections_title);
        alInspections = new ArrayList<>();
        alCurrent = new ArrayList<>();
        alPending = new ArrayList<>();

        currentAdapter = new InspectionAdapter(getActivity(),alCurrent,this);
        LinearLayoutManager linearLayoutManagerCurrent = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcvCurrent.setLayoutManager(linearLayoutManagerCurrent);
        rcvCurrent.setAdapter(currentAdapter);
        rcvCurrent.setVisibility(View.VISIBLE);

        pendingAdapter = new InspectionAdapter(getActivity(),alPending,this);
        LinearLayoutManager linearLayoutManagerPending = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcvPending.setLayoutManager(linearLayoutManagerPending);
        rcvPending.setAdapter(pendingAdapter);
        rcvPending.setVisibility(View.GONE);

        tvCurrent.setOnClickListener(this);
        tvPending.setOnClickListener(this);

        styleText();
    }

    private void getInspectionList(){

        if(Utils.isNetworkAvailable(getActivity())){
            orderAsyncTask = new OrderAsyncTask(getActivity(),this);
            orderAsyncTask.execute();
        }
        else if(prefs.contains(Constants.PREF_ORDER)) {

            alInspections = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER, OrderResponse.class)).getOrderList();
            if(alInspections != null) fillData(alInspections);
        }
        else{
            setLayoutVisibility();
        }
    }

    private void fillData(List<OrderListItem> list){

        alCurrent.clear();
        alPending.clear();

        for(OrderListItem orderListItem: list){
            try {
                long time = sdf.parse(orderListItem.getTimeStamp()).getTime();
                if(Utils.isCurrentDay(time)) {
                    alCurrent.add(orderListItem);
                }
                else if(!Utils.isOlderThanThreeDay(getActivity(),time,orderListItem)){
                    alPending.add(orderListItem);
                }
            }
            catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }

        if(alInspections != null && !alInspections.isEmpty()){
            currentAdapter.setData(alCurrent);
            pendingAdapter.setData(alPending);
        }

        setLayoutVisibility();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.tvCurrent:
                isCurrentSelected = true;
                setLayoutVisibility();
                styleText();
                break;
            case R.id.tvPending:
                isCurrentSelected = false;
                setLayoutVisibility();
                styleText();
                break;
        }
    }

    private void styleText() {

        if(isCurrentSelected){
            tvCurrent.setTypeface(null, Typeface.BOLD);
            tvPending.setTypeface(null, Typeface.NORMAL);
            viewCurrent.setVisibility(View.VISIBLE);
            viewPending.setVisibility(View.GONE);
        }
        else{
            tvPending.setTypeface(null, Typeface.BOLD);
            tvCurrent.setTypeface(null, Typeface.NORMAL);
            viewPending.setVisibility(View.VISIBLE);
            viewCurrent.setVisibility(View.GONE);
        }
    }

    private void setLayoutVisibility() {

        if(alInspections != null && !alInspections.isEmpty()){

            tvNoData.setVisibility(View.GONE);
            pbLoader.setVisibility(View.GONE);

            if(isCurrentSelected){
                if(!alCurrent.isEmpty()){
                    tvNoData.setVisibility(View.GONE);
                    rcvCurrent.setVisibility(View.VISIBLE);
                }
                else if(Utils.isNetworkAvailable(getActivity())){
                    tvNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.str_no_data));
                    rcvCurrent.setVisibility(View.GONE);
                }
                else{
                    tvNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.internet_not_avail));
                    rcvCurrent.setVisibility(View.GONE);
                }

                rcvPending.setVisibility(View.GONE);
            }
            else{
                if(!alPending.isEmpty()){
                    tvNoData.setVisibility(View.GONE);
                    rcvPending.setVisibility(View.VISIBLE);
                }
                else if(Utils.isNetworkAvailable(getActivity())){
                    tvNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.str_no_data));
                    rcvPending.setVisibility(View.GONE);
                }
                else{
                    tvNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.str_no_data));
                    rcvPending.setVisibility(View.GONE);
                }

                rcvCurrent.setVisibility(View.GONE);
            }
        }
        else{
            pbLoader.setVisibility(View.GONE);
            if(Utils.isNetworkAvailable(getActivity())){
                tvNoData.setText(getString(R.string.str_no_data));
            }
            else{
                if(isCurrentSelected){
                    tvNoData.setText(getString(R.string.internet_not_avail));
                    Utils.showSnackBar(coordinatorLayout,getString(R.string.internet_not_avail));
                }
                else{
                    tvNoData.setText(getString(R.string.str_no_data));
                }

            }
            tvNoData.setVisibility(View.VISIBLE);
            rcvCurrent.setVisibility(View.GONE);
            rcvPending.setVisibility(View.GONE);
        }
    }

    @Override
    public void onApiPreExecute(AsyncTask asyncTask) {
        pbLoader.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
        rcvCurrent.setVisibility(View.GONE);
        rcvPending.setVisibility(View.GONE);
    }

    @Override
    public void onApiPostExecute(BaseResponse baseResponse, AsyncTask asyncTask) {

        pbLoader.setVisibility(View.GONE);
        if(!Utils.showCallError(coordinatorLayout,baseResponse)){

            OrderResponse orderResponse = (OrderResponse) baseResponse;
            if(orderResponse != null){

                if(orderResponse.getAction() == Constants.ACTION_LOGIN_AGAIN){
                    Intent intent = new Intent(getActivity(), StartActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    prefs.clearSavedToken();
                }
                else if(orderResponse.getAction() == Constants.ACTION_DO_NOTHING){
                    if(orderResponse.getOrderList() != null && !orderResponse.getOrderList().isEmpty()){

                        alInspections = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER, OrderResponse.class)).getOrderList();
                        if(alInspections != null) fillData(alInspections);
                    }
                    else{

                        setLayoutVisibility();
                    }
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inspection_order, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout){
            ((MainActivity) getActivity()).logout();
            return true;
        }
        else if(id == R.id.pendingUploads){
            ((MainActivity) getActivity()).openPendingUploads();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.rlContainer:
                Log.v(TAG,"Position: " + position);
                Bundle bundle = new Bundle();
                if(isCurrentSelected){
                    bundle.putParcelable(Constants.INTENT_SELECTED_ORDER, alCurrent.get(position));
                }
                else{
                    bundle.putParcelable(Constants.INTENT_SELECTED_ORDER, alPending.get(position));
                }
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_NAVIGATION, bundle, true);
                break;
        }
    }
}
