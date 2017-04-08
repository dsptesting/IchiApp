package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
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
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 05-03-2017.
 */

public class SchedulerFragment extends BaseFragment{

    private static final String TAG = SchedulerFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.txtOrderNo)
    public TextView txtOrderNo;

    @BindView(R.id.etDate)
    EditText etDate;

    @BindView(R.id.etTime)
    EditText etTime;

    @BindView(R.id.etAccess)
    EditText etAccess;

    @BindView(R.id.etAccessCode)
    EditText etAccessCode;

    @BindView(R.id.cbBuyerAttending)
    CheckBox cbBuyerAttending;

    @BindView(R.id.cbAgentAttending)
    CheckBox cbAgentAttending;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private int position;
    private OrderListItem orderListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scheduler, container, false);
        ButterKnife.bind(this, view);
        position = getArguments().getInt(Constants.INTENT_POSITION);
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

        tvAppTitle.setText(R.string.scheduler);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        List<OrderListItem> orderListItems = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getOrderList();
        orderListItem = orderListItems.get(position);

        if(orderListItem != null){
            txtOrderNo.setText(txtOrderNo.getText().toString()+orderListItem.getIONum());
            String Date=null;
            String Time=null;
            String[] DateTime=orderListItem.getTimeStamp().split("T");
            Date=DateTime[0];
            Time=DateTime[1];
            etDate.setText(Date);
            etTime.setText(Time);
            etAccess.setText(orderListItem.getPropertyAccess());
            etAccessCode.setText(orderListItem.getPropertyCode());

            if (orderListItem.isAgentAttending())
                cbAgentAttending.setChecked(true);

            if (orderListItem.isBuyerAttending())
                cbBuyerAttending.setChecked(true);

            cbAgentAttending.setClickable(false);
            cbBuyerAttending.setClickable(false);
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
