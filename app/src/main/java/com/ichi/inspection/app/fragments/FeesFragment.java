package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class FeesFragment extends BaseFragment {

    private static final String TAG = FeesFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.txtOrderNo)
    public TextView txtOrderNo;

    @BindView(R.id.etTotal)
    EditText etTotal;

    /*@BindView(R.id.etSavedTotal)
    EditText etSavedTotal;*/

    @BindView(R.id.etPaymentsMade)
    EditText etPaymentsMade;

    @BindView(R.id.ll)
    LinearLayout ll;

    /*@BindView(R.id.cbHome)
    CheckBox cbHome;

    @BindView(R.id.cbTownHouse)
    CheckBox cbTownHouse;

    @BindView(R.id.cbCondo)
    CheckBox cbCondo;

    @BindView(R.id.cbPoolOrSpa)
    CheckBox cbPoolOrSpa;

    @BindView(R.id.cbTermite)
    CheckBox cbTermite;

    @BindView(R.id.cbPoolAndSpa)
    CheckBox cbPoolAndSpa;

    @BindView(R.id.cbAgeFee)
    CheckBox cbAgeFee;

    @BindView(R.id.cbTripCharge)
    CheckBox cbTripCharge;

    @BindView(R.id.cbGuestHouse)
    CheckBox cbGuestHouse;

    @BindView(R.id.cbOther)
    CheckBox cbOther;

    @BindView(R.id.cbReInspection)
    CheckBox cbReInspection;

    @BindView(R.id.cbDuplex)
    CheckBox cbDuplex;

    @BindView(R.id.cbTriplex)
    CheckBox cbTriplex;

    @BindView(R.id.cb4Plex)
    CheckBox cb4Plex;

    @BindView(R.id.cbDiscount)
    CheckBox cbDiscount;*/

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private int position;
    private OrderListItem orderListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fees, container, false);
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
        upArrow.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);*/

        tvAppTitle.setText(R.string.fees);
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
            etTotal.setText(orderListItem.getFeeCharged()+"");
            etPaymentsMade.setText(orderListItem.getPayment().getAmount());
            String fee=orderListItem.getFees();//"Fees":"299<inspect:Home>;325<inspect:Condo>",

            String[] fees=fee.split(";");
            if (fees!=null){
                for (String f:fees){
                    String value=f.substring(0,f.indexOf("<"));
                    String hint=f.substring(f.indexOf(":")+1,f.indexOf(">"));
                    Log.d(TAG, "initData: hint:"+hint);
                    Log.d(TAG, "initData: value:"+value);

                    LayoutInflater layoutInflater=getLayoutInflater(null);
                    View view=layoutInflater.inflate(R.layout.edittext,null);

                    CustomEditText customEditText= (CustomEditText) view.findViewById(R.id.et);
                    customEditText.setText(hint+" : "+value);
                    //customEditText.setHint(hint);
                    ll.addView(view);
                }


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
        if (id == R.id.logout) {
            ((MainActivity) getActivity()).logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
