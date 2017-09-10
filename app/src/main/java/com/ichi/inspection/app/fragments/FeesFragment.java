package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    @BindView(R.id.etPaymentsMade)
    EditText etPaymentsMade;

    @BindView(R.id.ll)
    LinearLayout ll;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private OrderListItem orderListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fees, container, false);
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

        tvAppTitle.setText(R.string.fees);
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
            etTotal.setText(orderListItem.getFeeCharged()+"");
            if(orderListItem.getPayment().getAmount() != null && orderListItem.getPayment().getAmount().length() > 0){
                etPaymentsMade.setText(orderListItem.getPayment().getAmount());
            }
            else{
                etPaymentsMade.setText("-");
            }

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
