package com.ichi.inspection.app.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
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
import com.ichi.inspection.app.adapters.AddSectionAdapter;
import com.ichi.inspection.app.adapters.LineAdapter;
import com.ichi.inspection.app.adapters.SelectSectionAdapter;
import com.ichi.inspection.app.adapters.TemplateAdapter;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.interfaces.OnListItemClickListener;
import com.ichi.inspection.app.models.AddSection;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.MasterResponse;
import com.ichi.inspection.app.models.NamedTemplates;
import com.ichi.inspection.app.models.NamedTemplatesItem;
import com.ichi.inspection.app.models.SelectSection;
import com.ichi.inspection.app.models.TemplateItemsItem;
import com.ichi.inspection.app.models.Templates;
import com.ichi.inspection.app.task.MasterAsyncTask;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 05-03-2017.
 */

public class InspectionDetailsFragment extends BaseFragment implements View.OnClickListener, OnApiCallbackListener, OnListItemClickListener
                    ,AdapterView.OnItemSelectedListener{

    private static final String TAG = InspectionDetailsFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.rcvItems)
    RecyclerView rcvItems;

    @Nullable @BindView(R.id.pbLoader)
    ProgressBar pbLoader;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    @BindView(R.id.sAddTemplate)
    AppCompatSpinner sAddTemplate;

    @BindView(R.id.sAddSection)
    AppCompatSpinner sAddSection;

    @BindView(R.id.sSelectSection)
    AppCompatSpinner sSelectSection;

    private LineAdapter lineAdapter;
    private MasterResponse masterResponse;
    private AddSection addSection = new AddSection();
    private SelectSection selectSection = new SelectSection();
    private NamedTemplates namedTemplates = new NamedTemplates();
    private Templates templates = new Templates();

    private TemplateAdapter templateAdapter;
    private AddSectionAdapter addSectionAdapter;
    private SelectSectionAdapter selectSectionAdapter;

    private MasterAsyncTask masterAsyncTask;

    @Nullable
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @BindView(R.id.mainLayout)
    NestedScrollView mainLayout;

    private int selectedIndexNamedTemplates = -1;
    private int selectedIndexAddSection = -1;
    private int selectedIndexSelectSection = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inspection_details, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        setHasOptionsMenu(true);
        initData();
        getMasterList();

        return view;
    }

    private void initData() {

        //Toolbar shit!
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        tvAppTitle.setText(R.string.inspections_title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        templateAdapter = new TemplateAdapter(getActivity(),namedTemplates.getNamedTemplatesItems());
        sAddTemplate.setAdapter(templateAdapter);
        sAddTemplate.setOnItemSelectedListener(this);

        addSectionAdapter = new AddSectionAdapter(getActivity(),addSection.getHeaderItems());
        sAddSection.setAdapter(addSectionAdapter);
        sAddSection.setOnItemSelectedListener(this);

        String selectedNamedTemplate = "-1";
        if(selectedIndexNamedTemplates != -1){
            NamedTemplatesItem namedTemplatesItem = namedTemplates.getNamedTemplatesItems().get(selectedIndexNamedTemplates);
            if(namedTemplatesItem != null) selectedNamedTemplate = namedTemplatesItem.getNamedTemplateId();
        }
        selectSectionAdapter = new SelectSectionAdapter(getActivity(),templates.getHeaderSections(selectedNamedTemplate));
        sSelectSection.setAdapter(selectSectionAdapter);
        sSelectSection.setOnItemSelectedListener(this);

    }

    private void initView() {

        //This is to refresh data once we get from web call async.
        templateAdapter.setData(namedTemplates.getNamedTemplatesItems());
        addSectionAdapter.setData(addSection.getHeaderItems());
    }

    private void getMasterList(){

        if(Utils.isNetworkAvailable(getActivity()) && prefs.getBoolean(Constants.PREF_REQUEST_MASTER_AFTER_LOGIN,false)){
            masterAsyncTask = new MasterAsyncTask(getActivity(),this);
            masterAsyncTask.execute();
        }
        else if(prefs.contains(Constants.PREF_MASTER)) {

            masterResponse = ((MasterResponse) prefs.getObject(Constants.PREF_MASTER, MasterResponse.class));
            addSection = ((AddSection) prefs.getObject(Constants.PREF_ADD_SECTION, AddSection.class));
            selectSection = ((SelectSection) prefs.getObject(Constants.PREF_SELECT_SECTION, SelectSection.class));
            namedTemplates = ((NamedTemplates) prefs.getObject(Constants.PREF_NAMED_TEMPLATES, NamedTemplates.class));
            templates = ((Templates) prefs.getObject(Constants.PREF_TEMPLATES, Templates.class));

            initView();
            setLayoutVisibility();
        }
        else{
            setLayoutVisibility();
        }
    }

    private void setLayoutVisibility() {

        if(masterResponse != null){

            tvNoData.setVisibility(View.GONE);
            pbLoader.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
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
            mainLayout.setVisibility(View.GONE);
        }
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
        tvNoData.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);
    }

    @Override
    public void onApiPostExecute(BaseResponse baseResponse, AsyncTask asyncTask) {

        pbLoader.setVisibility(View.GONE);
        if(!Utils.showCallError(coordinatorLayout,baseResponse)){

            MasterResponse masterResponseData = (MasterResponse) baseResponse;
            if(masterResponseData != null){

                masterResponse = ((MasterResponse) prefs.getObject(Constants.PREF_MASTER, MasterResponse.class));
                addSection = ((AddSection) prefs.getObject(Constants.PREF_ADD_SECTION, AddSection.class));
                selectSection = ((SelectSection) prefs.getObject(Constants.PREF_SELECT_SECTION, SelectSection.class));
                namedTemplates = ((NamedTemplates) prefs.getObject(Constants.PREF_NAMED_TEMPLATES, NamedTemplates.class));
                templates = ((Templates) prefs.getObject(Constants.PREF_TEMPLATES, Templates.class));

                initView();
                setLayoutVisibility();
            }
        }
    }

    @Override
    public void onDestroy() {
        if(masterAsyncTask != null && !masterAsyncTask.isCancelled()) masterAsyncTask.cancel(true);
        super.onDestroy();
    }

    @Override
    public void onListItemClick(View view, int position) {
       /* switch (view.getId()){
            case R.id.rlContainer:
                Log.v(TAG,"Position: " + position);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.INTENT_SELECTED_ORDER, position);
                ((MainActivity)getActivity()).navigateToScreen(Constants.INSPECTION_NAVIGATION, bundle, true);
                break;
        }*/
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

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position,long id) {

        switch (parent.getId()){
            case R.id.sAddTemplate:
                if(position != -1){
                    selectedIndexNamedTemplates = position;
                    Log.v(TAG,"SelectedTemplates "+ namedTemplates.getNamedTemplatesItems().get(selectedIndexNamedTemplates));

                    if(selectedIndexNamedTemplates != -1){
                        NamedTemplatesItem namedTemplatesItem = namedTemplates.getNamedTemplatesItems().get(selectedIndexNamedTemplates);
                        if(namedTemplatesItem != null) selectSectionAdapter.setData(templates.getHeaderSections(namedTemplatesItem.getNamedTemplateId()));
                    }

                }
                break;
            case R.id.sAddSection:
                if(position != -1){
                    selectedIndexAddSection = position;
                    Log.v(TAG,"SelectedAddSection "+ addSection.getHeaderItems().get(selectedIndexAddSection));
                }
                break;

            case R.id.sSelectSection:
                if(position != -1){
                    selectedIndexSelectSection = position;
                    String selectedNamedTemplate = "-1";
                    /*if(selectedIndexNamedTemplates != -1){
                        namedTemplates.getNamedTemplatesItems().get(selectedIndexNamedTemplates);
                    }
                    List<TemplateItemsItem> templateItemsItems = templates.getHeaderSections(selectedNamedTemplate);
                    if(templateItemsItems != null){
                        Log.v(TAG,"SelectSection "+ templateItemsItems.get(selectedIndexSelectSection));
                    }*/

                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
