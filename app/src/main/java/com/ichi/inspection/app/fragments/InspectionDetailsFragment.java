package com.ichi.inspection.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.GridActivity;
import com.ichi.inspection.app.activities.MainActivity;
import com.ichi.inspection.app.adapters.AddSectionAdapter;
import com.ichi.inspection.app.adapters.LineAdapter;
import com.ichi.inspection.app.adapters.MarkAllAdapter;
import com.ichi.inspection.app.adapters.SelectSectionAdapter;
import com.ichi.inspection.app.adapters.TemplateAdapter;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.interfaces.OnLineItemClickListener;
import com.ichi.inspection.app.interfaces.OnListItemClickListener;
import com.ichi.inspection.app.models.AddSection;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.models.MasterResponse;
import com.ichi.inspection.app.models.NamedTemplates;
import com.ichi.inspection.app.models.NamedTemplatesItem;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.SelectSection;
import com.ichi.inspection.app.models.SubSectionsItem;
import com.ichi.inspection.app.models.TemplateItemsItem;
import com.ichi.inspection.app.models.Templates;
import com.ichi.inspection.app.task.MasterAsyncTask;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.CustomTextView;
import com.ichi.inspection.app.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;

/**
 * Created by Palak on 05-03-2017.
 */

public class InspectionDetailsFragment extends BaseFragment implements View.OnClickListener, OnApiCallbackListener, OnListItemClickListener
                    ,AdapterView.OnItemSelectedListener,OnLineItemClickListener {

    private static final String TAG = InspectionDetailsFragment.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.rcvItems)
    RecyclerView rcvItems;

    @Nullable
    @BindView(R.id.pbLoader)
    ProgressBar pbLoader;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    @BindView(R.id.btnEditName)
    TextView btnEditName;

    @BindView(R.id.sAddTemplate)
    AppCompatSpinner sAddTemplate;

    @BindView(R.id.sMarkAll)
    AppCompatSpinner sMarkAll;

    @BindView(R.id.sAddSection)
    AppCompatSpinner sAddSection;

    @BindView(R.id.sSelectSection)
    AppCompatSpinner sSelectSection;
    private AlertDialog alertDialog;

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

    @Nullable
    @BindView(R.id.cvEditName)
    CardView cvEditName;

    @Nullable
    @BindView(R.id.cvRemoveSection)
    CardView cvRemoveSection;

    private int selectedIndexNamedTemplates = -1;
    private int selectedIndexAddSection = -1;
    private int selectedIndexSelectSection = -1;

    private OrderListItem orderListItem;
    private List<SubSectionsItem> alSubSections;
    //below is for spinner to load sections only.
    private List<SubSectionsItem> alSubSectionsOnly;

    private List<SubSectionsItem> alSubSectionsLines;
    private BottomSheetBehavior<View> behavior;
    private SubSectionsItem selectedsubSectionsItem;
    private int currentSelectedLinePositionForImage = -1;

    private List<String> markAllLines;
    MarkAllAdapter markAllAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inspection_details, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        setHasOptionsMenu(true);
        initData();
        EasyImage.configuration(mContext).saveInAppExternalFilesDir().saveInRootPicturesDirectory().setCopyExistingPicturesToPublicLocation(true);
        getMasterList();

        return view;
    }

    private void initData() {


        //Toolbar shit!
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        orderListItem = getArguments().getParcelable(Constants.INTENT_SELECTED_ORDER);

        tvAppTitle.setText(R.string.inspections_title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Mark All Lines
        markAllLines=new ArrayList<>();
        markAllLines.add("Mark All Lines");
        markAllLines.add("Good");
        markAllLines.add("Fair");
        markAllLines.add("Poor");
        markAllLines.add("Hide");
        markAllLines.add("N/A");

        markAllAdapter=new MarkAllAdapter(getActivity(),markAllLines);
        sMarkAll.setAdapter(markAllAdapter);
        sMarkAll.setOnItemSelectedListener(this);

        templateAdapter = new TemplateAdapter(getActivity(), namedTemplates.getNamedTemplatesItems());
        sAddTemplate.setAdapter(templateAdapter);
        sAddTemplate.setOnItemSelectedListener(this);

        addSectionAdapter = new AddSectionAdapter(getActivity(), addSection.getHeaderItems());
        sAddSection.setAdapter(addSectionAdapter);
        sAddSection.setOnItemSelectedListener(this);

        String selectedNamedTemplate = "-1";
        if (selectedIndexNamedTemplates != -1) {
            NamedTemplatesItem namedTemplatesItem = namedTemplates.getNamedTemplatesItems().get(selectedIndexNamedTemplates);
            if (namedTemplatesItem != null)
                selectedNamedTemplate = namedTemplatesItem.getNamedTemplateId();
        }

        alSubSections = new ArrayList<>();
        alSubSectionsOnly = new ArrayList<>();
        alSubSectionsLines = new ArrayList<>();

        //selectSectionAdapter = new SelectSectionAdapter(getActivity(),templates.getHeaderSections(selectedNamedTemplate));
        selectSectionAdapter = new SelectSectionAdapter(getActivity(), alSubSectionsOnly);
        sSelectSection.setAdapter(selectSectionAdapter);
        sSelectSection.setOnItemSelectedListener(this);

        lineAdapter = new LineAdapter(getActivity(),alSubSectionsLines,this,behavior,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcvItems.setLayoutManager(linearLayoutManager);
        rcvItems.setAdapter(lineAdapter);

        cvEditName.setOnClickListener(this);
        cvRemoveSection.setOnClickListener(this);

    }

    private void showGallary(int position){

        currentSelectedLinePositionForImage =  position;
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        mBottomSheetDialog.setContentView(sheetView);
        //sheetView.findViewById(R.id.).
        LinearLayout llCamera= (LinearLayout) sheetView.findViewById(R.id.llCamera);
        LinearLayout llGallery= (LinearLayout) sheetView.findViewById(R.id.llGallery);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openCamera(InspectionDetailsFragment.this,0);
                mBottomSheetDialog.dismiss();
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openGallery(InspectionDetailsFragment.this,0);
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                Log.d(TAG, "onImagePickerError: "+e.getMessage());
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source1, int type) {
                Log.v(TAG,"imageFile file : "+ imageFile.getAbsolutePath());
                Log.d(TAG, "onImagePicked: "+imageFile.getAbsolutePath());
                Log.d(TAG, "onImagePicked: ");
                File file = null;
                //TODO copy file to our folder..
                try {
                File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File createDir = new File(root+"/ICHI"+File.separator);
                if(!createDir.exists()) {
                    createDir.mkdir();
                }


                /*String orderNum= String.valueOf(orderListItem.getIONum());
                String lineIONum=alSubSectionsLines.get(currentSelectedLinePositionForImage).getIOLineId();
                String imageName=null;

                    Log.d(TAG, "onImagePicked: array size"+alSubSections.get(currentSelectedLinePositionForImage).getImageURIs().size());
                    if (alSubSections.get(currentSelectedLinePositionForImage).getImageURIs().size()==0){
                        imageName=orderNum+"_"+lineIONum+"_"+1;
                    }else{
                        String lastImageName=alSubSections.get(currentSelectedLinePositionForImage).getImageURIs().get(alSubSections.get(currentSelectedLinePositionForImage).getImageURIs().size()-1);
                        //lastImageName.substring(0,lastImageName.lastIndexOf(".")+1);
                        int lastNum= Integer.parseInt(lastImageName.split("_")[2]);
                        lastNum++;
                        imageName=orderNum+"_"+lineIONum+"_"+lastNum;
                    }
                    Log.d(TAG, "onImagePicked: array"+alSubSections.get(currentSelectedLinePositionForImage).getImageURIs());
                    Log.d(TAG, "onImagePicked: imagename:"+imageName);
*/
                file = new File(root + "/ICHI" + File.separator +imageFile);
                file.createNewFile();


                    //Copy Image
                    FileChannel source = null;
                    FileChannel destination = null;
                    source = new FileInputStream(imageFile).getChannel();
                    destination = new FileOutputStream(file).getChannel();
                    if (destination != null && source != null) {
                        destination.transferFrom(source, 0, source.size());
                    }
                    if (source != null) {
                        source.close();
                    }
                    if (destination != null) {
                        destination.close();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(currentSelectedLinePositionForImage != -1 && alSubSectionsLines.get(currentSelectedLinePositionForImage) != null){
                    ArrayList<String> uris = alSubSectionsLines.get(currentSelectedLinePositionForImage).getImageURIs();
                    uris.add(file.getAbsolutePath());
                    //alSubSectionsLines.get(currentSelectedLinePositionForImage).setImageURIs(uris);

                    Intent intent = new Intent(mContext, GridActivity.class);
                    intent.putStringArrayListExtra("URIs",uris);
                    startActivity(intent);
                }


            }

        });
    }

    private void initView() {

        //if this satisfies, then we have data from web...
        if (selectSection != null) {
            List<SubSectionsItem> temp = selectSection.getSubSections("" + orderListItem.getSequence());
            if (temp != null && !temp.isEmpty()) {
                //alSubSections.clear();
                //this will include sections, lines which are already selected... new selected template's lines and sections are going to be entered here too..
                alSubSections.addAll(temp);

                //Load sections only to show in select section spinner
                alSubSectionsOnly.clear();
                for (SubSectionsItem sub : alSubSections) {
                    if (Boolean.parseBoolean(sub.getIsHead())) {
                        alSubSectionsOnly.add(sub);
                    }
                }
            }
        }

        //This is to refresh data once we get from web call async.
        templateAdapter.setData(namedTemplates.getNamedTemplatesItems());
        addSectionAdapter.setData(addSection.getHeaderItems());
        selectSectionAdapter.setData(alSubSectionsOnly);
    }

    private void getMasterList(){

        if (prefs.contains(Constants.PREF_MASTER)) {

            masterResponse = ((MasterResponse) prefs.getObject(Constants.PREF_MASTER, MasterResponse.class));
            addSection = ((AddSection) prefs.getObject(Constants.PREF_ADD_SECTION, AddSection.class));
            selectSection = ((SelectSection) prefs.getObject(Constants.PREF_SELECT_SECTION, SelectSection.class));
            namedTemplates = ((NamedTemplates) prefs.getObject(Constants.PREF_NAMED_TEMPLATES, NamedTemplates.class));
            templates = ((Templates) prefs.getObject(Constants.PREF_TEMPLATES, Templates.class));

            initView();
            setLayoutVisibility();
        } else if (Utils.isNetworkAvailable(getActivity())) {
            masterAsyncTask = new MasterAsyncTask(getActivity(), this);
            masterAsyncTask.execute();
        } else {
            setLayoutVisibility();
        }

        /*if(Utils.isNetworkAvailable(getActivity()) && prefs.getBoolean(Constants.PREF_REQUEST_MASTER_AFTER_LOGIN,false)){
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
        }*/
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
            case R.id.cvRemoveSection:
                showRemoveSectionDialog();
                break;
            case R.id.cvEditName:
                showEditNameDialog();
                break;
            case R.id.btnEditName:
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

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
        switch (view.getId()) {
            case R.id.rlContainer:
                Log.v(TAG, "Position: " + position);
            case R.id.btnUpload:
                showGallary(position);
                break;
            case R.id.btnPhoto:
                ArrayList<String> imageURIs=alSubSectionsLines.get(position).getImageURIs();
                Intent intent = new Intent(mContext, GridActivity.class);
                intent.putStringArrayListExtra("URIs",imageURIs);
                startActivity(intent);
                break;
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

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position,long id) {

        switch (parent.getId()){
            case R.id.sAddTemplate:
                if(position != -1){
                    selectedIndexNamedTemplates = position;

                    if (selectedIndexNamedTemplates != -1) {
                        NamedTemplatesItem namedTemplatesItem = namedTemplates.getNamedTemplatesItems().get(selectedIndexNamedTemplates);
                        //Log.v(TAG, "SelectedTemplates namedTemplatesItem: " + namedTemplatesItem);
                        if (namedTemplatesItem != null) {

                            /*List<TemplateItemsItem> templateItemsItems = templates.getHeaderSections(namedTemplatesItem.getNamedTemplateId());
                            SubSectionsItem subSectionsItem;
                            for(TemplateItemsItem templateItemsItem : templateItemsItems){
                                subSectionsItem = Utils.convertTemplateToSubSection(getActivity(),templateItemsItem, ""+orderListItem.getSequence());
                                if(!Utils.hasSubSection(alSubSections,subSectionsItem)){
                                    alSubSections.add(subSectionsItem);
                                }
                            }*/

                            List<TemplateItemsItem> templateItemsItems = templates.getTemplateItems(namedTemplatesItem.getNamedTemplateId());

                            SubSectionsItem subSectionsItem;

                            //Log.v(TAG, "templateItemsItems size " + templateItemsItems.size());

                            for (TemplateItemsItem templateItemsItem : templateItemsItems) {

                                subSectionsItem = Utils.convertTemplateToSubSection(getActivity(), templateItemsItem, "" + orderListItem.getSequence());

                                if (Boolean.parseBoolean(subSectionsItem.getIsHead()) && !Utils.hasSubSection(alSubSectionsOnly, subSectionsItem)) {

                                    alSubSectionsOnly.add(subSectionsItem);
                                }

                                if (!Utils.hasSubSection(alSubSections, subSectionsItem)) {

                                    alSubSections.add(subSectionsItem);
                                }
                            }

                            //Log.v(TAG, "after selected new temp, alSubSections size: " + alSubSections.size());
                            //Log.v(TAG, "after selected new temp, alSubSectionsOnly size: " + alSubSectionsOnly.size());

                            selectSectionAdapter.setData(alSubSectionsOnly);
                        }
                    }

                }
                break;
            case R.id.sAddSection:
                if (position != -1) {
                    selectedIndexAddSection = position;
                    //Log.v(TAG, "SelectedAddSection " + addSection.getHeaderItems().get(selectedIndexAddSection));
                }
                break;

            case R.id.sSelectSection:
                if (position != -1) {
                    selectedIndexSelectSection = position;
                    String selectedNamedTemplate = "-1";
                    /*if(selectedIndexNamedTemplates != -1){
                        namedTemplates.getNamedTemplatesItems().get(selectedIndexNamedTemplates);
                    }
                    List<TemplateItemsItem> templateItemsItems = templates.getHeaderSections(selectedNamedTemplate);
                    if(templateItemsItems != null){
                        Log.v(TAG,"SelectSection "+ templateItemsItems.get(selectedIndexSelectSection));
                    }*/

                    selectedsubSectionsItem = alSubSectionsOnly.get(selectedIndexSelectSection);
                    if (selectedsubSectionsItem != null) {
                        String usedHead = selectedsubSectionsItem.getUsedHead();
                        alSubSectionsLines.clear();
                        for (SubSectionsItem sub : alSubSections) {
                            if (sub.getUsedHead().equalsIgnoreCase(usedHead) && !Boolean.parseBoolean(sub.getIsHead())) {
                                alSubSectionsLines.add(sub);
                            }
                        }
                    }
                    //Log.v(TAG, "alSubSectionsLines: " + alSubSectionsLines);
                    lineAdapter.setData(alSubSectionsLines);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void showEditNameDialog() {

        if (selectedsubSectionsItem == null) return;

        final View view = getActivity().getLayoutInflater().inflate(R.layout.item_textinput_edit_name, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please enter new name");
        builder.setView(view);
        builder.setMessage(null);

        ((EditText) view.findViewById(R.id.et)).setText("" + selectedsubSectionsItem.getName());
        ((EditText) view.findViewById(R.id.et)).setSelection(selectedsubSectionsItem.getName().length());

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!((EditText) view.findViewById(R.id.et)).getText().toString().trim().isEmpty()) {
                    cancelThisDialog();
                    selectedsubSectionsItem.setName(((EditText) view.findViewById(R.id.et)).getText().toString().trim());

                    List<TemplateItemsItem> templateItemsItems = templates.getTemplateItems();
                    for (int p = 0; p < templateItemsItems.size(); p++) {
                        if (templateItemsItems.get(p).getSectionId().equalsIgnoreCase(selectedsubSectionsItem.getSectionId()) &&
                                templateItemsItems.get(p).getUsedHead().equalsIgnoreCase("" + selectedsubSectionsItem.getUsedHead())) {
                            templateItemsItems.get(p).setName(selectedsubSectionsItem.getName());
                        }
                    }

                    List<SubSectionsItem> selectTemp = selectSection.getSubSections("" + orderListItem.getSequence());
                    for (int p = 0; p < selectTemp.size(); p++) {
                        if (selectTemp.get(p).getSectionId().equalsIgnoreCase(selectedsubSectionsItem.getSectionId()) &&
                                selectTemp.get(p).getUsedHead().equalsIgnoreCase("" + selectedsubSectionsItem.getUsedHead())) {
                            selectTemp.get(p).setName(selectedsubSectionsItem.getName());
                        }
                    }

                    prefs.putObject(Constants.PREF_SELECT_SECTION, selectSection);
                    prefs.putObject(Constants.PREF_TEMPLATES, templates);
                    selectSectionAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelThisDialog();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }

    public void showRemoveSectionDialog() {

        if (selectedsubSectionsItem == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do you wish to delete this page?");

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                cancelThisDialog();
                //Log.v(TAG, "Total alSubSectionsOnly : " + alSubSectionsOnly.size());
                Iterator<SubSectionsItem> iter1 = alSubSectionsOnly.iterator();
                while (iter1.hasNext()) {
                    SubSectionsItem subSectionsItem = iter1.next();
                    if (subSectionsItem.getSectionId().equalsIgnoreCase("" + selectedsubSectionsItem.getSectionId()) &&
                            subSectionsItem.getUsedHead().equalsIgnoreCase("" + selectedsubSectionsItem.getUsedHead())) {
                        // Remove the current element from the iterator and the list.
                        //Log.v(TAG, "Total alSubSectionsOnly name : " + subSectionsItem);
                        iter1.remove();
                    }
                }
                //Log.v(TAG, "after Total alSubSectionsOnly : " + alSubSectionsOnly.size());


               /* Log.v(TAG, "Total templates : " + templates.getTemplateItems().size());
                Iterator<TemplateItemsItem> iter2 = templates.getTemplateItems().iterator();
                while (iter2.hasNext()) {
                    TemplateItemsItem tmp = iter2.next();
                    if (tmp.getSectionId().equalsIgnoreCase(selectedsubSectionsItem.getSectionId()) &&
                            tmp.getUsedHead().equalsIgnoreCase("" + selectedsubSectionsItem.getUsedHead())) {
                        iter2.remove();
                    }
                }
                Log.v(TAG, "after Total templates : " + templates.getTemplateItems().size());*/

                //Log.v(TAG, "before Total selectSection : " + selectSection.getSubSections().size());
                Iterator<SubSectionsItem> iter3 = selectSection.getSubSections().iterator();
                while (iter3.hasNext()) {
                    SubSectionsItem tmp = iter3.next();
                    if (tmp.getInspectionId().equalsIgnoreCase(""+orderListItem.getSequence()) &&
                            tmp.getSectionId().equalsIgnoreCase(selectedsubSectionsItem.getSectionId()) &&
                            tmp.getUsedHead().equalsIgnoreCase("" + selectedsubSectionsItem.getUsedHead())) {
                        //Log.v(TAG, "Total alSubSectionsOnly tmp.getSectionId() : " + tmp.getSectionId());
                        iter3.remove();
                    }
                }

                //Log.v(TAG, "after Total selectSection : " + selectSection.getSubSections().size());
                prefs.putObject(Constants.PREF_SELECT_SECTION, selectSection);
                //prefs.putObject(Constants.PREF_TEMPLATES, templates);
                selectSectionAdapter.setData(alSubSectionsOnly);
                //Log.v(TAG, "after Total getCount : " +selectSectionAdapter.getCount());

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelThisDialog();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }

    public void cancelThisDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onLineItemClick(View view, SubSectionsItem subSectionsItem, int position) {
        switch (view.getId()){
            case R.id.btnR:
            case R.id.btnS:
            case R.id.btnU:
                changeLineStatus(subSectionsItem,position);
                break;
            case R.id.llAddComment:
                showCommentBox(subSectionsItem,position);
                break;
        }
    }

    private void showCommentBox(final SubSectionsItem subSectionsItem, final int position) {

        if (subSectionsItem == null) return;

        final View view = getActivity().getLayoutInflater().inflate(R.layout.item_textinput_edit_name, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Note");
        builder.setView(view);
        builder.setMessage(null);

        ((EditText) view.findViewById(R.id.et)).setText("" + subSectionsItem.getComments());
        ((EditText) view.findViewById(R.id.et)).setSelection(subSectionsItem.getComments().length());

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!((EditText) view.findViewById(R.id.et)).getText().toString().trim().isEmpty()) {
                    cancelThisDialog();
                    subSectionsItem.setComments(((EditText) view.findViewById(R.id.et)).getText().toString().trim());

                    changeLineStatus(subSectionsItem,position);
                    lineAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelThisDialog();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void changeLineStatus(SubSectionsItem subSectionsItem, int position) {

        boolean hasChanged = false;
        for (SubSectionsItem sub : alSubSections) {
            if (sub.getIOLineId().equalsIgnoreCase(subSectionsItem.getIOLineId())) {
                alSubSections.remove(sub);
                alSubSections.add(position,subSectionsItem);

                hasChanged = true;
                break;
            }
        }

        if(hasChanged){
            List<SubSectionsItem> temp = selectSection.getSubSections("" + orderListItem.getSequence());
            if(temp != null && !temp.isEmpty()){
                for(int i=0;i<temp.size();i++){
                    if(temp.get(i).getIOLineId().equalsIgnoreCase(subSectionsItem.getIOLineId())){
                        temp.set(i,subSectionsItem);
                        prefs.putObject(Constants.PREF_SELECT_SECTION, selectSection);
                        break;
                    }
                }
            }
        }

    }
}