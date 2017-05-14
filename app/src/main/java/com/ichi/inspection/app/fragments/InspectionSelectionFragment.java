package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class InspectionSelectionFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = InspectionSelectionFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.cvInspectionOrder)
    CardView cvInspectionOrder;

    @BindView(R.id.cvInspectionDetail)
    CardView cvInspectionDetail;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private OrderListItem orderListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inspection_selection, container, false);
        ButterKnife.bind(this, view);
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

        tvAppTitle.setText("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        orderListItem = getArguments().getParcelable(Constants.INTENT_SELECTED_ORDER);

        cvInspectionOrder.setOnClickListener(this);
        cvInspectionDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.cvInspectionDetail:
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_DETAIL, null, true);
                break;
            case R.id.cvInspectionOrder:

                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.INTENT_SELECTED_ORDER, orderListItem);
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_ORDER, bundle, true);
                break;
        }
    }
}
