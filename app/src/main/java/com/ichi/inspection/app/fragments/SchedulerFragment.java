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

public class SchedulerFragment extends BaseFragment{

    private static final String TAG = SchedulerFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.etWhoPlacedOrder)
    EditText etWhoPlacedOrder;

    @BindView(R.id.etDate)
    EditText etDate;

    @BindView(R.id.etTime)
    EditText etTime;

    @BindView(R.id.etNumberOfSlot)
    EditText etNumberOfSlot;

    @BindView(R.id.etInspector)
    EditText etInspector;

    @BindView(R.id.etAccess)
    EditText etAccess;

    @BindView(R.id.etAccessCode)
    EditText etAccessCode;

    @BindView(R.id.etLocale)
    EditText etLocale;

    @BindView(R.id.etReferredBy)
    EditText etReferredBy;

    @BindView(R.id.cbInspectAgreement)
    CheckBox cbInspectAgreement;

    @BindView(R.id.cbBuyerAttending)
    CheckBox cbBuyerAttending;

    @BindView(R.id.cbAgentAttending)
    CheckBox cbAgentAttending;

    @BindView(R.id.cbSpecialRequest)
    CheckBox cbSpecialRequest;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scheduler, container, false);
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

        tvAppTitle.setText(R.string.scheduler);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        etWhoPlacedOrder.setText("abcd");
        etDate.setText("abcd");
        etTime.setText("abcd");
        etNumberOfSlot.setText("abcd");
        etInspector.setText("abcd");
        etAccess.setText("abcd");
        etAccessCode.setText("abcd");
        etLocale.setText("abcd");
        etReferredBy.setText("abcd");
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
