package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.MainActivity;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.models.PayItem;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.Utils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 05-03-2017.
 */

public class PaymentFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = PaymentFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.txtOrderNo)
    public TextView txtOrderNo;

    @BindView(R.id.etAmount)
    EditText etAmount;

    @BindView(R.id.sType)
    AppCompatSpinner sType;

    @BindView(R.id.etNumber)
    EditText etNumber;

    @BindView(R.id.sCardType)
    AppCompatSpinner sCardType;

    @BindView(R.id.etCcNumber)
    EditText etCcNumber;

    @BindView(R.id.etNameOnCard)
    EditText etNameOnCard;

    @BindView(R.id.sMonth)
    AppCompatSpinner sMonth;

    @BindView(R.id.sYear)
    AppCompatSpinner sYear;

    @BindView(R.id.etCsc)
    EditText etCsc;

    @BindView(R.id.etAuthorizationCode)
    EditText etAuthorizationCode;

    @BindView(R.id.etAddress)
    EditText etAddress;

    @BindView(R.id.etCity)
    EditText etCity;

    @BindView(R.id.etState)
    EditText etState;

    @BindView(R.id.etZip)
    EditText etZip;

    @BindView(R.id.cvSave)
    CardView cvSave;


    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private int position;
    private OrderListItem orderListItem;
    private List<OrderListItem> orderListItems;
    private List<PayItem> payItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);
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

        tvAppTitle.setText(R.string.payment);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

       orderListItems = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getOrderList();
        orderListItem = orderListItems.get(position);

        payItems= ((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getPay();


        if(orderListItem != null) {
            txtOrderNo.setText(txtOrderNo.getText().toString() + orderListItem.getIONum());
            etAmount.setText(orderListItem.getPayment().getAmount());
            etNumber.setText(orderListItem.getPayment().getCheckNumber());
            etCcNumber.setText(orderListItem.getPayment().getCCNumber());
            etNameOnCard.setText(orderListItem.getPayment().getCCNameOnCard());
            etCsc.setText(orderListItem.getPayment().getCCCode());
            etAuthorizationCode.setText(orderListItem.getPayment().getAuthorizationCode());
            etAddress.setText(orderListItem.getPayment().getCCAddress());
            etCity.setText(orderListItem.getPayment().getCCCity());
            etState.setText(orderListItem.getPayment().getCCState());
            etZip.setText(orderListItem.getPayment().getCCZip());
        }

        if(payItems!=null){
            String type=payItems.get(0).getData();
            String[] types=type.split("#\n");

            String cctype=payItems.get(1).getData();
            String[] cctypes=cctype.split("#\n");

            ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item,types);
            sType.setAdapter(typeAdapter);

            ArrayAdapter<String> cctypeAdapter=new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item,cctypes);
            sCardType.setAdapter(cctypeAdapter);

            int sTypeSelection = 0;
            for (int i=0;i<types.length;i++){
                if (types[i].equals(orderListItem.getPayment().getPaymentType()));{
                    sTypeSelection=i;
                }
            }
            sType.setSelection(sTypeSelection);
            int sCCTypeSelection = 0;
            for (int i=0;i<cctypes.length;i++){
                if (cctypes[i].equals(orderListItem.getPayment().getCCType()));{
                    sCCTypeSelection=i;
                }
            }
            sCardType.setSelection(sCCTypeSelection);
        }else {
            Log.d(TAG, "initData: PayItem Null");
        }

        int year= Calendar.getInstance().get(Calendar.YEAR);
        String[] years=new String[6];
        for (int i=0;i<6;i++){
            years[i]=year+"";
            year++;
        }
        String[] months=new String[12];
        for (int j=1;j<=12;j++){
            months[j-1]=j+"";
        }
        ArrayAdapter<String> yearAdapter=new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item,years);
        sYear.setAdapter(yearAdapter);

        ArrayAdapter<String> monthAdapter=new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item,months);
        sMonth.setAdapter(monthAdapter);

        int sMonthSelection = 0;
        for (int i=0;i<months.length;i++){
            if (months[i].equals(orderListItem.getPayment().getCCExprMonth())){
                sMonthSelection=i;
            }
        }
        sMonth.setSelection(sMonthSelection);
        int sYearSelection = 0;
        for (int i=0;i<years.length;i++){

            Log.d(TAG, "initData: "+orderListItem.getPayment().getCCExprYear()); /*if (years[i].equals(orderListItem.getPayment().getCCExprYear())){
                sYearSelection=i;
            }*/
        }
        sYear.setSelection(sYearSelection);

        cvSave.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cvSave:
                savePaymentData();
                break;
        }
    }

    private void savePaymentData(){
        orderListItem.getPayment().setAmount(etAmount.getText().toString().trim());
        orderListItem.getPayment().setCheckNumber(etNumber.getText().toString().trim());
        orderListItem.getPayment().setCCNumber(etCcNumber.getText().toString().trim());
        orderListItem.getPayment().setCCNameOnCard(etNameOnCard.getText().toString().trim());
        orderListItem.getPayment().setCCCode(etCsc.getText().toString().trim());
        orderListItem.getPayment().setAuthorizationCode(etAuthorizationCode.getText().toString().trim());
        orderListItem.getPayment().setCCAddress(etAddress.getText().toString().trim());
        orderListItem.getPayment().setCCCity(etCity.getText().toString().trim());
        orderListItem.getPayment().setCCState(etState.getText().toString().trim());
        orderListItem.getPayment().setCCZip(etZip.getText().toString().trim());
        orderListItem.getPayment().setPaymentType(sType.getSelectedItem().toString().trim());
        orderListItem.getPayment().setCCType(sCardType.getSelectedItem().toString().trim());
        orderListItem.getPayment().setCCExprMonth(sMonth.getSelectedItem().toString().trim());
        orderListItem.getPayment().setCCExprYear(sYear.getSelectedItem().toString().trim());
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderList(orderListItems);
        orderResponse.setPay(payItems);
        orderResponse.setAction(((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getAction());
        orderResponse.setError(((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getError());
        orderResponse.setMessage(((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getMessage());
        orderResponse.setResultCode(((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getResultCode());

        prefs.putObject(Constants.PREF_ORDER,orderResponse);
        Utils.showSnackBar(coordinatorLayout,"Saved");
    }
}
