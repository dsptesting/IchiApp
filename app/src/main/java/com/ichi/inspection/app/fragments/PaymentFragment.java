package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.EditText;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.MainActivity;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.OrderResponse;
import com.ichi.inspection.app.models.OrderUpdateContainer;
import com.ichi.inspection.app.models.PayItem;
import com.ichi.inspection.app.models.Payment;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.PreferencesHelper;
import com.ichi.inspection.app.utils.Utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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

    private OrderListItem orderListItem;
    private List<OrderListItem> orderListItems;
    private List<PayItem> payItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);
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

        payItems= ((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getPay();

        if(orderListItem != null) {
            txtOrderNo.setText(txtOrderNo.getText().toString() + orderListItem.getIONum());
            etAmount.setText(orderListItem.getPayment().getAmount());
            etNumber.setText(orderListItem.getPayment().getCheckNumber());
            etCcNumber.setText(orderListItem.getPayment().getcCNumber());
            etNameOnCard.setText(orderListItem.getPayment().getcCNameOnCard());
            etCsc.setText(orderListItem.getPayment().getcCCode());
            etAuthorizationCode.setText(orderListItem.getPayment().getAuthorizationCode());
            etAddress.setText(orderListItem.getPayment().getcCAddress());
            etCity.setText(orderListItem.getPayment().getcCCity());
            etState.setText(orderListItem.getPayment().getcCState());
            etZip.setText(orderListItem.getPayment().getcCZip());
        }

        if(payItems!=null){
            String type=payItems.get(0).getData();
            String[] types=type.split("#\n");
            List<String> typeList = new ArrayList<>(Arrays.asList(types));
            typeList.add(0,"Select");
            types = typeList.toArray(new String[typeList.size()]);

            String cctype=payItems.get(1).getData();
            String[] cctypes=cctype.split("#\n");
            List<String> cctypeList = new ArrayList<>(Arrays.asList(cctypes));
            cctypeList.add(0,"Select");
            cctypes = cctypeList.toArray(new String[cctypeList.size()]);

            ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item,types);
            sType.setAdapter(typeAdapter);

            ArrayAdapter<String> cctypeAdapter=new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item,cctypes);
            sCardType.setAdapter(cctypeAdapter);

            int sTypeSelection = 0;
            for (int i=0;i<types.length;i++){
                if (types[i].trim().equals(orderListItem.getPayment().getPaymentType())){
                    sTypeSelection=i;
                }
            }
            sType.setSelection(sTypeSelection);
            int sCCTypeSelection = 0;
            for (int i=0;i<cctypes.length;i++){
                if (cctypes[i].equals(orderListItem.getPayment().getcCType())){
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
        List<String> yearsList = new ArrayList<>(Arrays.asList(years));
        yearsList.add(0,"Select");
        years = yearsList.toArray(new String[yearsList.size()]);

        String[] months=new String[12];
        for (int j=1;j<=12;j++){
            months[j-1]=j+"";
        }
        List<String> monthsList = new ArrayList<>(Arrays.asList(months));
        monthsList.add(0,"Select");
        months = monthsList.toArray(new String[monthsList.size()]);

        ArrayAdapter<String> yearAdapter=new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item,years);
        sYear.setAdapter(yearAdapter);

        ArrayAdapter<String> monthAdapter=new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item,months);
        sMonth.setAdapter(monthAdapter);

        int sMonthSelection = 0;
        for (int i=0;i<months.length;i++){
            if (months[i].equals(orderListItem.getPayment().getcCExprMonth())){
                sMonthSelection=i;
            }
        }
        sMonth.setSelection(sMonthSelection);
        int sYearSelection = 0;
        for (int i=0;i<years.length;i++){

            Log.d(TAG, "initData: "+orderListItem.getPayment().getcCExprYear());
            if (years[i].equals(orderListItem.getPayment().getcCExprYear())){
                sYearSelection=i;
            }
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
              //  if(validate()){
                    savePaymentData();
                //}
                break;
        }
    }

    private boolean validate() {

        if(sCardType.getSelectedItemPosition() == 0){
            Utils.showSnackBar(coordinatorLayout,"Select card");
            return false;
        }
        if(sType.getSelectedItemPosition() == 0){
            Utils.showSnackBar(coordinatorLayout,"Select type");
            return false;
        }
        if(sMonth.getSelectedItemPosition() == 0){
            Utils.showSnackBar(coordinatorLayout,"Select month");
            return false;
        }
        if(sYear.getSelectedItemPosition() == 0){
            Utils.showSnackBar(coordinatorLayout,"Select year");
            return false;
        }

        return true;
    }

    private void savePaymentData(){

        Payment payment = orderListItem.getPayment();
        if(!payment.getAmount().equalsIgnoreCase(etAmount.getText().toString().trim())){
            payment.setAmount(etAmount.getText().toString().trim());
            payment.setAmountSynced(true);
        }
        if(!payment.getCheckNumber().equalsIgnoreCase(etNumber.getText().toString().trim())){
            payment.setCheckNumber(etNumber.getText().toString().trim());
            payment.setCheckNumberSynced(true);
        }
        if(!payment.getcCNumber().equalsIgnoreCase(etCcNumber.getText().toString().trim())){
            payment.setcCNumber(etCcNumber.getText().toString().trim());
            payment.setCcNumberSynced(true);
        }
        if(!payment.getcCNameOnCard().equalsIgnoreCase(etNameOnCard.getText().toString().trim())){
            payment.setcCNameOnCard(etNameOnCard.getText().toString().trim());
            payment.setCcNameOnCardSynced(true);
        }
        if(!payment.getcCCode().equalsIgnoreCase(etCsc.getText().toString().trim())){
            payment.setcCCode(etCsc.getText().toString().trim());
            payment.setCcCodeSynced(true);
        }
        if(!payment.getAuthorizationCode().equalsIgnoreCase(etAuthorizationCode.getText().toString().trim())){
            payment.setAuthorizationCode(etAuthorizationCode.getText().toString().trim());
            payment.setAuthorizatinCodeSynced(true);
        }
        if(!payment.getcCAddress().equalsIgnoreCase(etAddress.getText().toString().trim())){
            payment.setcCAddress(etAddress.getText().toString().trim());
            payment.setCcAddressSynced(true);
        }
        if(!payment.getcCCity().equalsIgnoreCase(etCity.getText().toString().trim())){
            payment.setcCCity(etCity.getText().toString().trim());
            payment.setCcCitySynced(true);
        }
        if(!payment.getcCState().equalsIgnoreCase(etState.getText().toString().trim())){
            payment.setcCState(etState.getText().toString().trim());
            payment.setCcStateSynced(true);
        }
        if(!payment.getcCZip().equalsIgnoreCase(etZip.getText().toString().trim())){
            payment.setcCZip(etZip.getText().toString().trim());
            payment.setCcZipSynced(true);
        }
        if(!payment.getPaymentType().equalsIgnoreCase(sType.getSelectedItem().toString().trim())){
            if(sType.getSelectedItemPosition() == 0){
                payment.setPaymentType("");
                payment.setPaymentTypeSynced(true);
            }
            else{
                payment.setPaymentType(sType.getSelectedItem().toString().trim());
                payment.setPaymentTypeSynced(true);
            }
        }
        if(!payment.getcCType().equalsIgnoreCase(sCardType.getSelectedItem().toString().trim())){
            if(sCardType.getSelectedItemPosition() == 0){
                payment.setcCType("");
                payment.setcCTypeSynced(true);
            }
            else{
                payment.setcCType(sCardType.getSelectedItem().toString().trim());
                payment.setcCTypeSynced(true);
            }
        }
        if(!payment.getcCExprMonth().equalsIgnoreCase(sMonth.getSelectedItem().toString().trim())){
            if(sMonth.getSelectedItemPosition() == 0){
                payment.setcCExprMonth("");
                payment.setCcExprMonthSynced(true);
            }
            else{
                payment.setcCExprMonth(sMonth.getSelectedItem().toString().trim());
                payment.setCcExprMonthSynced(true);
            }
        }
        if(!payment.getcCExprYear().equalsIgnoreCase(sYear.getSelectedItem().toString().trim())){
            if(sYear.getSelectedItemPosition() == 0){
                payment.setcCExprYear("");
                payment.setcCExprYearSynced(true);
            }
            else{
                payment.setcCExprYear(sYear.getSelectedItem().toString().trim());
                payment.setcCExprYearSynced(true);
            }
        }

        OrderResponse orderResponse = ((OrderResponse) prefs.getObject(Constants.PREF_ORDER, OrderResponse.class));
        List<OrderListItem> inspections =  orderResponse.getOrderList();
        for(OrderListItem order : inspections){
            if(order != null && order.getIONum() == orderListItem.getIONum()){
                order.setPayment(payment);
            }
        }
        prefs.putObject(Constants.PREF_ORDER,orderResponse);

        PreferencesHelper prefs = PreferencesHelper.getInstance(getActivity());
        boolean found = false;
        List<String> orderUpdates = null;
        OrderUpdateContainer orderUpdateContainer = null;

        if(!prefs.contains(Constants.PREF_ORDER_UPDATE)){
            orderUpdateContainer = new OrderUpdateContainer();
            orderUpdates = orderUpdateContainer.getOrderUpdatesList();
        }
        else{
            orderUpdateContainer = (OrderUpdateContainer) prefs.getObject(Constants.PREF_ORDER_UPDATE, OrderUpdateContainer.class);
            if(orderUpdateContainer != null && orderUpdateContainer.getOrderUpdatesList() != null && !orderUpdateContainer.getOrderUpdatesList().isEmpty()){
                orderUpdates = orderUpdateContainer.getOrderUpdatesList();

                for(String inspectionId : orderUpdates){
                    if((""+orderListItem.getSequence()).equalsIgnoreCase(inspectionId)){
                        found = true;
                    }
                }
            }
        }

        if(!found){
            orderUpdates.add(""+orderListItem.getSequence());
            prefs.putObject(Constants.PREF_ORDER_UPDATE,orderUpdateContainer);
            Log.v(TAG,"order saved in order update pref");
        }

/*
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderList(orderListItems);
        orderResponse.setPay(payItems);
        orderResponse.setAction(((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getAction());
        orderResponse.setError(((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getError());
        orderResponse.setMessage(((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getMessage());
        orderResponse.setResultCode(((OrderResponse) prefs.getObject(Constants.PREF_ORDER,OrderResponse.class)).getResultCode());

        prefs.putObject(Constants.PREF_ORDER,orderResponse);*/
        Utils.showSnackBar(coordinatorLayout,"Saved");
    }
}
