package com.ichi.inspection.app.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.ichi.inspection.app.models.AddSectionItem;
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
import com.ichi.inspection.app.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Palak on 05-03-2017.
 */
@RuntimePermissions
public class InspectionDetailsFragment extends BaseFragment implements View.OnClickListener, OnApiCallbackListener, OnListItemClickListener
        , AdapterView.OnItemSelectedListener, OnLineItemClickListener {

    private static final String TAG = InspectionDetailsFragment.class.getSimpleName();
    private static final int REQUEST_CODE_GRID = 14;
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

    private static final int REQUEST_CODE_IMAGE_PICKER = 24;

    private MasterResponse masterResponse;
    private AddSection addSection = new AddSection();
    private SelectSection selectSection = new SelectSection();
    private NamedTemplates namedTemplates = new NamedTemplates();
    private Templates templates = new Templates();

    private TemplateAdapter templateAdapter;
    private AddSectionAdapter addSectionAdapter;
    private SelectSectionAdapter selectSectionAdapter;
    private LineAdapter lineAdapter;

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
    @BindView(R.id.cvAddNewLine)
    CardView cvAddNewLine;

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
    private boolean uploadClicked;

    private AddSectionSelectedAsyncTask addSectionSelectedAsyncTask;
    private TemplateSelectedAsyncTask templateSelectedAsyncTask;
    private SubSectionSelectedAsyncTask subSectionSelectedAsyncTask;
    private MarkAllSelectedAsyncTask markAllSelectedAsyncTask;
    private ChangeStatusAsyncTask changeStatusAsyncTask;

    @BindView(R.id.tvErrorCount)
    TextView tvErrorCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inspection_details, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        setHasOptionsMenu(true);
        initData();
        /*EasyImage.configuration(mContext).saveInAppExternalFilesDir().
                saveInRootPicturesDirectory().
                setCopyExistingPicturesToPublicLocation(true);*/

        EasyImage.configuration(getActivity()).setAllowMultiplePickInGallery(true);
        getMasterList();

        return view;
    }
    private void errorCount(){
        int errorCount=0;
        Log.d(TAG, "onListItemClick: Size:"+alSubSections.size());

        List<SubSectionsItem> tmp = selectSection.getSubSections(""+orderListItem.getSequence());
        Log.d(TAG, "errorCount: Size temp:"+tmp.size());
        for (SubSectionsItem sub:tmp){
            if (!Boolean.parseBoolean(sub.getIsHead()) && sub.getStatus() != Constants.DELETED){

                        /*boolean g=false,f=false,p=false,na=false,hide=false,comment=false;
                        if (sub.getGood().equalsIgnoreCase("f")){
                            g=true;
                        }
                        if (sub.getFair().equalsIgnoreCase("f")){
                            f=true;
                        }
                        if (sub.getPoor().equalsIgnoreCase("f")){
                            p=true;
                        }
                        if (sub.getSuppressPrint().equalsIgnoreCase("F")){
                            hide=true;
                        }
                        if (sub.getNotInspected()==null || sub.getNotInspected().equalsIgnoreCase("")||sub.getNotInspected().equalsIgnoreCase("f")){
                            na=true;
                        }
                        if (sub.getComments()==null||sub.getComments().equals("")){
                            comment=true;
                        }*/

                if(((sub.getPoor().equalsIgnoreCase("t") || sub.getFair().equalsIgnoreCase("t"))
                        && (sub.getComments() == null || sub.getComments().isEmpty()))
                        || ((sub.getPoor().equalsIgnoreCase("f") && sub.getFair().equalsIgnoreCase("f") && sub.getGood().equalsIgnoreCase("f"))
                        && (sub.getSuppressPrint().equalsIgnoreCase("f") && (sub.getNotInspected() == null || sub.getNotInspected().isEmpty() || sub.getNotInspected().equalsIgnoreCase("f"))))){
                    errorCount++;
                }

                        /*if (g && f && p && hide && na && comment){
                            Log.d(TAG, "onListItemClick: Array:"+sub.toString());
                            errorCount++;
                        }*/
            }
        }

        Log.d(TAG, "onListItemClick: Error Count:"+errorCount);
        tvErrorCount.setText("You have "+errorCount+" errors");
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
        markAllLines = new ArrayList<>();
        markAllLines.add("Select option");
        markAllLines.add("Good");
        markAllLines.add("Fair");
        markAllLines.add("Poor");
        markAllLines.add("Hide");
        markAllLines.add("N/A");

        markAllAdapter = new MarkAllAdapter(getActivity(), markAllLines);
        sMarkAll.setAdapter(markAllAdapter);
        sMarkAll.setOnItemSelectedListener(this);

        templateAdapter = new TemplateAdapter(getActivity(), namedTemplates.getNamedTemplatesItems());
        sAddTemplate.setAdapter(templateAdapter);
        sAddTemplate.setOnItemSelectedListener(this);
        sAddTemplate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:

                        if(alSubSections != null && !alSubSections.isEmpty()){
                            Utils.showSnackBar(coordinatorLayout,"Template selected already!");
                            return true;
                        }
                }
                return false;
            }
        });

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
        alSubSectionsOnly.add(new SubSectionsItem("Select option",Constants.HEADER));
        alSubSectionsLines = new ArrayList<>();

        //selectSectionAdapter = new SelectSectionAdapter(getActivity(),templates.getHeaderSections(selectedNamedTemplate));
        selectSectionAdapter = new SelectSectionAdapter(getActivity(), alSubSectionsOnly);
        sSelectSection.setAdapter(selectSectionAdapter);
        sSelectSection.setOnItemSelectedListener(this);

        lineAdapter = new LineAdapter(getActivity(), alSubSectionsLines, this, behavior, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvItems.setLayoutManager(linearLayoutManager);
        rcvItems.setAdapter(lineAdapter);

        cvEditName.setOnClickListener(this);
        cvRemoveSection.setOnClickListener(this);
        cvAddNewLine.setOnClickListener(this);

    }

    private void initView() {

        //if this satisfies, then we have data from web...
        if (selectSection != null) {
            List<SubSectionsItem> temp = selectSection.getSubSections("" + orderListItem.getSequence());
            if (temp != null && !temp.isEmpty()) {
                alSubSections.clear();
                //this will include sections, lines which are already selected... new selected template's lines and sections are going to be entered here too..
                alSubSections.addAll(temp);

                //Load sections only to show in select section spinner
                alSubSectionsOnly.clear();
                alSubSectionsOnly.add(new SubSectionsItem("Select option",Constants.HEADER));
                for (SubSectionsItem sub : alSubSections) {
                    if (Boolean.parseBoolean(sub.getIsHead()) && sub.getStatus() != Constants.DELETED) {
                        alSubSectionsOnly.add(sub);
                    }
                }
            }
        }

        //This is to refresh data once we get from web call async.
        List<NamedTemplatesItem> templateNameList = namedTemplates.getNamedTemplatesItems();
        templateNameList.add(0,new NamedTemplatesItem("Select option"));
        templateAdapter.setData(templateNameList);

        List<AddSectionItem> addSectionList = addSection.getHeaderItems();
        addSectionList.add(0,new AddSectionItem("Select option"));
        addSectionAdapter.setData(addSectionList);

        selectSectionAdapter.setData(alSubSectionsOnly);
        errorCount();
    }

    private void showGallary(int position) {

        currentSelectedLinePositionForImage = position;
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        mBottomSheetDialog.setContentView(sheetView);
        //sheetView.findViewById(R.id.).
        LinearLayout llCamera = (LinearLayout) sheetView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) sheetView.findViewById(R.id.llGallery);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openCamera(InspectionDetailsFragment.this, 0);
                mBottomSheetDialog.dismiss();
            }
        });
        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openGallery(InspectionDetailsFragment.this, 0);
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_GRID && resultCode == getActivity().RESULT_OK){
            if(lineAdapter != null && lineAdapter.getItemCount() > 0){
                lineAdapter.notifyDataSetChanged();
            }
        }
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                Log.d(TAG, "onImagePickerError: " + e.getMessage());
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                ArrayList<String> uris = null;
                boolean changed = false;
                String name ="";
                SubSectionsItem updatedSubSection = null;
                String lineIONum = null;

                for(int i= 0;i<imageFiles.size();i++){
                    File imageFile = imageFiles.get(i);
                    Log.v(TAG, "imageFile file : " + imageFile.getAbsolutePath());
                    Log.d(TAG, "onImagePicked: " + imageFile.getAbsolutePath());
                    Log.d(TAG, "onImagePicked: ");
                    File file = null;
                    //TODO copy file to our folder..
                    try {
                        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        File createDir = new File(root + "/ICHI" + File.separator);
                        if (!createDir.exists()) {
                            createDir.mkdir();
                        }

                        //Create Image Name
                        String orderNum = String.valueOf(orderListItem.getIONum());
                        updatedSubSection = alSubSectionsLines.get(currentSelectedLinePositionForImage);
                        lineIONum = updatedSubSection.getIOLineId();
                        String imageName = null;
                        String extension = imageFile.getName().substring(imageFile.getName().lastIndexOf("."));
                        uris = alSubSectionsLines.get(currentSelectedLinePositionForImage).getImageURIs();
                        name = alSubSectionsLines.get(currentSelectedLinePositionForImage).getName();
                        Log.d(TAG, "onImagePicked: array size:" + uris.size());

                        if (uris.size() == 0) {
                            String noe;
                            noe = alSubSectionsLines.get(currentSelectedLinePositionForImage).getNumberOfExposures();
                            if (noe == null || noe.equalsIgnoreCase("") || noe.isEmpty()) {
                                noe = "0";
                            }
                            imageName = orderNum + "_" + lineIONum + "_" + noe + extension;
                        } else {
                            File fileUri = new File(uris.get(uris.size() - 1));
                            String lastImageName = fileUri.getName();
                            Log.d(TAG, "onImagePicked: lastImage:" + lastImageName);
                            String li = lastImageName.substring(0, lastImageName.lastIndexOf("."));
                            Log.d(TAG, "onImagePicked: With Ext:" + li);
                            int lastNum = Integer.parseInt(li.split("_")[2]);
                            lastNum++;
                            imageName = orderNum + "_" + lineIONum + "_" + lastNum + extension;
                        }

                        Log.d(TAG, "onImagePicked: array:" + uris.toString());
                        Log.d(TAG, "onImagePicked: name:" + imageName);

                        file = new File(root + "/ICHI" + File.separator + imageName);
                        file.createNewFile();

                        Log.d(TAG, "onImagePicked: FileName: " + file.getName());

                        copy(imageFile,file);
                        if (currentSelectedLinePositionForImage != -1 && alSubSectionsLines.get(currentSelectedLinePositionForImage) != null) {

                            changed = true;
                            uris.add(file.getAbsolutePath());
                            //alSubSectionsLines.get(currentSelectedLinePositionForImage).setImageURIs(uris);
                        }
                    } catch (IOException e) {
                        Log.d(TAG, "onImagePicked error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                if(changed && updatedSubSection != null){
                    for (SubSectionsItem subSectionsItem : alSubSections) {
                        if (subSectionsItem.getIOLineId().equalsIgnoreCase(lineIONum)){
                            subSectionsItem.setImageURIs(uris);
                        }
                    }

                    List<SubSectionsItem> temp = selectSection.getSubSections(/*"" + orderListItem.getSequence()*/);
                    if (temp != null && !temp.isEmpty()) {
                        for (int j = 0; j < temp.size(); j++) {
                            if (temp.get(j).getIOLineId().equalsIgnoreCase(lineIONum)) {
                                temp.set(j, updatedSubSection);
                                break;
                            }
                        }
                    }
                    prefs.putObject(Constants.PREF_SELECT_SECTION, selectSection);

                    Intent intent = new Intent(mContext, GridActivity.class);
                    intent.putExtra("name",name);
                    intent.putStringArrayListExtra("URIs", uris);
                    startActivityForResult(intent,REQUEST_CODE_GRID);
                }


            }
        });
    }

    private void copy(File imageFile, File file) throws IOException {
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

        Log.v(TAG,"copied");
    }

    private void getMasterList() {

        //TODO dont delete this block
        /*if (prefs.contains(Constants.PREF_MASTER)) {

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
        }*/

        if (Utils.isNetworkAvailable(getActivity()) && prefs.getBoolean(Constants.PREF_REQUEST_MASTER_AFTER_LOGIN, false)) {
            masterAsyncTask = new MasterAsyncTask(getActivity(), this);
            masterAsyncTask.execute();
        } else if (prefs.contains(Constants.PREF_MASTER)) {

            masterResponse = ((MasterResponse) prefs.getObject(Constants.PREF_MASTER, MasterResponse.class));
            addSection = ((AddSection) prefs.getObject(Constants.PREF_ADD_SECTION, AddSection.class));
            selectSection = ((SelectSection) prefs.getObject(Constants.PREF_SELECT_SECTION, SelectSection.class));
            namedTemplates = ((NamedTemplates) prefs.getObject(Constants.PREF_NAMED_TEMPLATES, NamedTemplates.class));
            templates = ((Templates) prefs.getObject(Constants.PREF_TEMPLATES, Templates.class));

            initView();
            setLayoutVisibility();
        } else {
            setLayoutVisibility();
        }
    }

    private void setLayoutVisibility() {

        if (masterResponse != null) {

            tvNoData.setVisibility(View.GONE);
            pbLoader.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        } else {
            pbLoader.setVisibility(View.GONE);
            if (Utils.isNetworkAvailable(getActivity())) {
                tvNoData.setText(getString(R.string.str_no_data));
            } else {
                tvNoData.setText(getString(R.string.internet_not_avail));
                Utils.showSnackBar(coordinatorLayout, getString(R.string.internet_not_avail));
            }
            tvNoData.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
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
                break;
            case R.id.cvAddNewLine:
                showAddNewLineDialog();
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
        if (!Utils.showCallError(coordinatorLayout, baseResponse)) {

            MasterResponse masterResponseData = (MasterResponse) baseResponse;
            if (masterResponseData != null) {

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
    public void onListItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlContainer:
                Log.v(TAG, "Position: " + position);
            case R.id.btnUpload:
                currentSelectedLinePositionForImage = position;
                uploadClicked = true;
                InspectionDetailsFragmentPermissionsDispatcher.showCameraWithCheck(this);
                //showGallary(position);
                break;
            case R.id.btnPhoto:
                currentSelectedLinePositionForImage = position;
                uploadClicked = false;
                InspectionDetailsFragmentPermissionsDispatcher.showCameraWithCheck(this);
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
        if (id == R.id.logout) {
            ((MainActivity) getActivity()).logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {

        switch (parent.getId()) {
            case R.id.sAddTemplate:
                if (position > 0) {

                    selectedIndexNamedTemplates = position;

                    if (selectedIndexNamedTemplates > 0) {
                        templateSelectedAsyncTask = new TemplateSelectedAsyncTask(getActivity(),true);
                        templateSelectedAsyncTask.execute();
                    }

                }
                break;
            case R.id.sAddSection:
                if (position > 0) {

                    /*String templateId = getTemplateIdFromSections(0);
                    if (templateId == null || templateId.isEmpty()) {

                        Utils.showSnackBar(coordinatorLayout,"Select Template first!");
                        sAddSection.setSelection(0);
                        return;
                    }

                    Log.v(TAG,"template ID got : "+ templateId);*/


                    //TODO -1 is magic
                    selectedIndexAddSection = position-1;
                    addSectionSelectedAsyncTask = new AddSectionSelectedAsyncTask(getActivity(),true);
                    addSectionSelectedAsyncTask.execute();

                }
                break;

            case R.id.sSelectSection:
                sMarkAll.setSelection(0);

                if (alSubSectionsOnly.get(position).getContentType() != Constants.HEADER && position != -1) {
                    selectedIndexSelectSection = position;

                    selectedsubSectionsItem = alSubSectionsOnly.get(selectedIndexSelectSection);
                    subSectionSelectedAsyncTask = new SubSectionSelectedAsyncTask(getActivity(),true);
                    subSectionSelectedAsyncTask.execute();
                }
                else{
                    selectedIndexSelectSection = -1;
                    selectedsubSectionsItem = null;
                    alSubSectionsLines.clear();
                    lineAdapter.setData(alSubSectionsLines);
                }
                break;

            case R.id.sMarkAll:
                if (position > 0) {

                    if(alSubSectionsLines == null || alSubSectionsLines.isEmpty()){
                        Utils.showSnackBar(coordinatorLayout,"Select Section first!");
                        sMarkAll.setSelection(0);
                        return;
                    }

                    markAllSelectedAsyncTask = new MarkAllSelectedAsyncTask(getActivity(),true);
                    markAllSelectedAsyncTask.execute(position);
                }
                break;
        }

    }

    private String getTemplateIdFromSections(int position) {

        if(alSubSections == null || alSubSections.isEmpty()) return null;

        if(alSubSections.size() == position) return null;

        SubSectionsItem subSectionsItem = alSubSections.get(position);
        if(subSectionsItem != null && subSectionsItem.getContentType() != Constants.HEADER){
            if(subSectionsItem.getTemplatedId() != null && !subSectionsItem.getTemplatedId().trim().isEmpty()){
                return subSectionsItem.getTemplatedId();
            }
        }

        position++;
        return getTemplateIdFromSections(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Log.v(TAG,"nothing");
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
                    Utils.updateThisSubSection(getActivity(), selectedsubSectionsItem);
                    selectedsubSectionsItem.setName(((EditText) view.findViewById(R.id.et)).getText().toString().trim());

                    List<TemplateItemsItem> templateItemsItems = templates.getTemplateItems();
                    for (int p = 0; p < templateItemsItems.size(); p++) {
                        if (templateItemsItems.get(p).getSectionId().equalsIgnoreCase(selectedsubSectionsItem.getSectionId()) &&
                                templateItemsItems.get(p).getUsedHead().equalsIgnoreCase("" + selectedsubSectionsItem.getUsedHead())) {
                            templateItemsItems.get(p).setName(selectedsubSectionsItem.getName());
                        }
                    }

                    List<SubSectionsItem> selectTemp = selectSection.getSubSections(/*"" + orderListItem.getSequence()*/);
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

    //TODO remove baki from sync
    public void showRemoveSectionDialog() {

        if (selectedsubSectionsItem == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do you wish to delete this section?");

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                cancelThisDialog();

                Iterator<SubSectionsItem> iter1 = alSubSectionsOnly.iterator();
                while (iter1.hasNext()) {
                    SubSectionsItem subSectionsItem = iter1.next();
                    if (subSectionsItem != null && subSectionsItem.getContentType() != Constants.HEADER &&
                            subSectionsItem.getSectionId().equalsIgnoreCase("" + selectedsubSectionsItem.getSectionId()) &&
                            subSectionsItem.getUsedHead().equalsIgnoreCase("" + selectedsubSectionsItem.getUsedHead())) {
                        //Log.v(TAG, "Total alSubSectionsOnly name : " + subSectionsItem);
                        iter1.remove();
                    }
                }

                List<SubSectionsItem> allsubs = selectSection.getSubSections();
                boolean deleted = false;
                for(SubSectionsItem subSectionsItem : allsubs){
                    if(subSectionsItem != null && subSectionsItem.getContentType() != Constants.HEADER
                            && subSectionsItem.getInspectionId().equalsIgnoreCase("" + orderListItem.getSequence())
                            && subSectionsItem.getSectionId().equalsIgnoreCase(""+selectedsubSectionsItem.getSectionId())
                            && subSectionsItem.getUsedHead().equalsIgnoreCase("" + selectedsubSectionsItem.getUsedHead())){
                        subSectionsItem.setStatus(Constants.DELETED);
                        deleted = true;
                        Utils.updateThisSubSection(mContext, subSectionsItem);
                    }
                }


                if(deleted){
                    if (selectedsubSectionsItem != null) {
                        String usedHead = selectedsubSectionsItem.getUsedHead();
                        for (SubSectionsItem sub : allsubs) {
                            if (sub.getUsedHead().equalsIgnoreCase(usedHead) && !Boolean.parseBoolean(sub.getIsHead())) {
                                sub.setStatus(Constants.DELETED);
                            }
                        }
                    }
                }


                prefs.putObject(Constants.PREF_SELECT_SECTION, selectSection);
                selectSectionAdapter.setData(alSubSectionsOnly);
                sSelectSection.setSelection(0);
                errorCount();
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
        switch (view.getId()) {
            case R.id.btnR:
            case R.id.btnS:
            case R.id.btnU:
            case R.id.btnNA:
            case R.id.btnHide:
                changeStatusAsyncTask = new ChangeStatusAsyncTask(getActivity(),true,subSectionsItem, position,false);
                changeStatusAsyncTask.execute();
                break;
            case R.id.llAddComment:
                showCommentBox(subSectionsItem, position);
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

                    Utils.updateThisSubSection(mContext, subSectionsItem);

                    changeStatusAsyncTask = new ChangeStatusAsyncTask(getActivity(),true,subSectionsItem, position,true);
                    changeStatusAsyncTask.execute();
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

    public void showAddNewLineDialog() {

        if (selectedsubSectionsItem == null) {
            Utils.showSnackBar(coordinatorLayout, "Please select section first to add new line to!");
            return;
        }

        final View view = getActivity().getLayoutInflater().inflate(R.layout.item_textinput_edit_name, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please enter the name of the new line");
        builder.setView(view);
        builder.setMessage(null);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!((EditText) view.findViewById(R.id.et)).getText().toString().trim().isEmpty()) {
                    cancelThisDialog();

                    SubSectionsItem subSectionsItem = new SubSectionsItem();
                    subSectionsItem.setName(((EditText) view.findViewById(R.id.et)).getText().toString().trim());
                    subSectionsItem.setIOLineId(Utils.getGlobalUniqueNumber(getActivity(), true) + "");
                    subSectionsItem.setSectionId(selectedsubSectionsItem.getSectionId());
                    subSectionsItem.setInspectionId(selectedsubSectionsItem.getInspectionId());
                    subSectionsItem.setGood("f");
                    subSectionsItem.setFair("f");
                    subSectionsItem.setPoor("f");
                    subSectionsItem.setComments("");
                    subSectionsItem.setLineNumber("");
                    subSectionsItem.setIsHead("false");
                    subSectionsItem.setLineOrder("");
                    subSectionsItem.setSuppressPrint("F");
                    subSectionsItem.setPageBreak("");
                    subSectionsItem.setUsedHead(selectedsubSectionsItem.getUsedHead());
                    subSectionsItem.setNumberOfExposures("");
                    subSectionsItem.setNotInspected("f");
                    subSectionsItem.setVeryPoor("f");
                    subSectionsItem.setStatus(Constants.ADDED);

                    Utils.updateThisSubSection(mContext, subSectionsItem);
                    selectSection.getSubSections().add(subSectionsItem);
                    prefs.putObject(Constants.PREF_SELECT_SECTION, selectSection);

                    alSubSectionsLines.add(subSectionsItem);
                    lineAdapter.notifyDataSetChanged();

                    initView();


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

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showCamera() {
        Log.v(TAG, "showCamera called");
        if (uploadClicked) {
            EasyImage.openChooserWithGallery(this, "Select profile picture", REQUEST_CODE_IMAGE_PICKER);
        } else {
            ArrayList<String> imageURIs = alSubSectionsLines.get(currentSelectedLinePositionForImage).getImageURIs();
            if (imageURIs != null && !imageURIs.isEmpty()) {
                Intent intent = new Intent(mContext, GridActivity.class);
                intent.putExtra("name",alSubSectionsLines.get(currentSelectedLinePositionForImage).getName());
                intent.putStringArrayListExtra("URIs", imageURIs);
                startActivityForResult(intent,REQUEST_CODE_GRID);
            } else {
                Utils.showSnackBar(coordinatorLayout, getString(R.string.no_image_avail));
            }

        }
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.permission_camera_rationale)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showDeniedForCamera() {
        Utils.showSnackBar(coordinatorLayout, getString(R.string.permission_camera_denied));
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showNeverAskForCamera() {
        Utils.showSnackBar(coordinatorLayout, getString(R.string.permission_camera_never_askagain));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        InspectionDetailsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private class ChangeStatusAsyncTask extends AsyncTask<Void,Void,Void> {

        Activity activity;
        boolean showLoader;
        SubSectionsItem subSectionsItem;
        int position;
        boolean updateLineAdapter;

        public ChangeStatusAsyncTask(Activity activity, boolean showLoader, SubSectionsItem subSectionsItem, int position,boolean updateLineAdapter) {
            this.activity = activity;
            this.showLoader = showLoader;
            this.subSectionsItem = subSectionsItem;
            this.position = position;
            this.updateLineAdapter = updateLineAdapter;
        }

        @Override
        protected void onPreExecute() {
            if (showLoader) Utils.showProgressBar(getActivity());
        }

        @Override
        protected Void doInBackground(Void... params) {

            boolean hasChanged = false;
            for (SubSectionsItem sub : alSubSections) {
                if (sub.getIOLineId().equalsIgnoreCase(subSectionsItem.getIOLineId())) {
                    alSubSections.remove(sub);
                    alSubSections.add(position, subSectionsItem);

                    hasChanged = true;
                    break;
                }
            }

            if (hasChanged) {
                List<SubSectionsItem> temp = selectSection.getSubSections(/*"" + orderListItem.getSequence()*/);
                if (temp != null && !temp.isEmpty()) {
                    for (int i = 0; i < temp.size(); i++) {
                        if (temp.get(i).getIOLineId().equalsIgnoreCase(subSectionsItem.getIOLineId())) {
                            temp.set(i, subSectionsItem);
                            prefs.putObject(Constants.PREF_SELECT_SECTION, selectSection);
                            break;
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(showLoader) Utils.hideProgressBar(getActivity());
            if(updateLineAdapter){
                lineAdapter.notifyDataSetChanged();
            }
            errorCount();
        }
    }

    private class TemplateSelectedAsyncTask extends AsyncTask<Void,Void,Void>{

        Activity activity;
        boolean showLoader;

        public TemplateSelectedAsyncTask(Activity activity, boolean showLoader) {
            this.activity = activity;
            this.showLoader = showLoader;
        }

        @Override
        protected void onPreExecute() {
            if(showLoader) Utils.showProgressBar(getActivity());
        }

        @Override
        protected Void doInBackground(Void... params) {

            NamedTemplatesItem namedTemplatesItem = namedTemplates.getNamedTemplatesItems().get(selectedIndexNamedTemplates);

            if (namedTemplatesItem != null) {

                List<TemplateItemsItem> templateItemsItems = templates.getTemplateItems(namedTemplatesItem.getNamedTemplateId());

                SubSectionsItem subSectionsItem;

                if(selectSection == null){
                    selectSection = new SelectSection();
                }
                List<SubSectionsItem> storedSubSections = selectSection.getSubSections();

                for (TemplateItemsItem templateItemsItem : templateItemsItems) {

                    subSectionsItem = Utils.convertTemplateToSubSection(getActivity(), templateItemsItem, "" + orderListItem.getSequence());

                    if (Boolean.parseBoolean(subSectionsItem.getIsHead()) && !Utils.hasSubSection(alSubSectionsOnly, subSectionsItem)) {

                        alSubSectionsOnly.add(subSectionsItem);
                    }

                    if (!Utils.hasSubSection(alSubSections, subSectionsItem)) {

                        alSubSections.add(subSectionsItem);
                    }

                    if(!Utils.hasSubSection(storedSubSections, subSectionsItem)){

                        storedSubSections.add(subSectionsItem);
                    }
                }

                selectSection.setSubSections(storedSubSections);
                prefs.putObject(Constants.PREF_SELECT_SECTION,selectSection);

                if(activity != null){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            selectSectionAdapter.setData(alSubSectionsOnly);
                        }
                    });
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(showLoader) Utils.hideProgressBar(getActivity());
        }
    }

    private class SubSectionSelectedAsyncTask extends AsyncTask<String,Void,Void>{

        Activity activity;
        boolean showLoader;

        public SubSectionSelectedAsyncTask(Activity activity, boolean showLoader) {
            this.activity = activity;
            this.showLoader = showLoader;
        }

        @Override
        protected void onPreExecute() {
            if(showLoader) Utils.showProgressBar(getActivity());
        }

        @Override
        protected Void doInBackground(String... params) {

            if (selectedsubSectionsItem != null) {
                String usedHead = selectedsubSectionsItem.getUsedHead();
                alSubSectionsLines.clear();
                for (SubSectionsItem sub : alSubSections) {
                    if (sub.getUsedHead().equalsIgnoreCase(usedHead) && !Boolean.parseBoolean(sub.getIsHead())) {
                        alSubSectionsLines.add(sub);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            lineAdapter.setData(alSubSectionsLines);
            if(showLoader) Utils.hideProgressBar(getActivity());
        }
    }

    private class MarkAllSelectedAsyncTask extends AsyncTask<Integer,Void,Void>{

        Activity activity;
        boolean showLoader;

        public MarkAllSelectedAsyncTask(Activity activity, boolean showLoader) {
            this.activity = activity;
            this.showLoader = showLoader;
        }

        @Override
        protected void onPreExecute() {
            if(showLoader) Utils.showProgressBar(getActivity());
        }

        @Override
        protected Void doInBackground(Integer... params) {

            int position = params[0];

            for (int i = 0; i < alSubSectionsLines.size(); i++) {
                SubSectionsItem subSectionsItem = alSubSectionsLines.get(i);
                if (position == 1) {
                    subSectionsItem.setGood("t");
                    subSectionsItem.setFair("f");
                    subSectionsItem.setPoor("f");
                } else if (position == 2) {
                    subSectionsItem.setGood("f");
                    subSectionsItem.setFair("t");
                    subSectionsItem.setPoor("f");
                } else if (position == 3) {
                    subSectionsItem.setGood("f");
                    subSectionsItem.setFair("f");
                    subSectionsItem.setPoor("t");
                } else if (position == 4) {
                    subSectionsItem.setNotInspected("f");
                    subSectionsItem.setSuppressPrint("t");
                } else if (position == 5) {
                    subSectionsItem.setNotInspected("t");
                    subSectionsItem.setSuppressPrint("f");
                }

                Utils.updateThisSubSection(getActivity(), subSectionsItem);

                for (SubSectionsItem sub : alSubSections) {
                    if (sub.getIOLineId().equalsIgnoreCase(subSectionsItem.getIOLineId())) {
                        alSubSections.remove(sub);
                        alSubSections.add(position, subSectionsItem);
                        break;
                    }
                }

                List<SubSectionsItem> temp = selectSection.getSubSections("" + orderListItem.getSequence());
                if (temp != null && !temp.isEmpty()) {
                    for (int j = 0; j < temp.size(); j++) {
                        if (temp.get(j).getIOLineId().equalsIgnoreCase(subSectionsItem.getIOLineId())) {
                            temp.set(j, subSectionsItem);
                            break;
                        }
                    }
                }
            }

            prefs.putObject(Constants.PREF_SELECT_SECTION, selectSection);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            lineAdapter.notifyDataSetChanged();
            if(showLoader) Utils.hideProgressBar(getActivity());
            errorCount();
        }

    }

    private class AddSectionSelectedAsyncTask extends AsyncTask<Void,Void,Boolean>{

        Activity activity;
        boolean showLoader;

        public AddSectionSelectedAsyncTask(Activity activity, boolean showLoader) {
            this.activity = activity;
            this.showLoader = showLoader;
        }

        @Override
        protected void onPreExecute() {
            if(showLoader) Utils.showProgressBar(getActivity());
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String templateId = "0";

            AddSectionItem selectedAddSectionItem = addSection.getHeaderItems().get(selectedIndexAddSection);
            if(selectedAddSectionItem != null){

                //TODO Ask if duplicate add section is allowed or not to Amit
                if(Utils.hasSubSection(alSubSections,selectedAddSectionItem)){
                    Utils.showSnackBar(coordinatorLayout,"This section is already selected!");
                    if(activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sAddSection.setSelection(0);
                            }
                        });
                    }
                    return false;
                }

                String sectionId = selectedAddSectionItem.getSectionId();
                if(sectionId != null && !sectionId.toString().trim().isEmpty() && Utils.isNumeric(sectionId.toString().trim())){
                    int sectionIdNumberStart = Integer.parseInt(sectionId);
                    int sectionIdNumberEnd = Integer.parseInt(sectionId.replace("00","99"));

                    List<AddSectionItem> allAddSections = addSection.getItems();
                    List<AddSectionItem> selectedAddSectionsItems = new ArrayList<>();
                    for(int i=0;i<allAddSections.size();i++){
                        if(Integer.parseInt(allAddSections.get(i).getSectionId()) > sectionIdNumberStart &&
                                Integer.parseInt(allAddSections.get(i).getSectionId()) < sectionIdNumberEnd){
                            selectedAddSectionsItems.add(allAddSections.get(i));
                        }
                    }

                    List<SubSectionsItem> orgList = selectSection.getSubSections();
                    SubSectionsItem subSectionsItemSectionOnly = Utils.convertAddSectionToSubSection(getActivity(), selectedAddSectionItem,""+ orderListItem.getSequence(),templateId);
                    alSubSections.add(subSectionsItemSectionOnly);
                    alSubSectionsOnly.add(subSectionsItemSectionOnly);
                    orgList.add(subSectionsItemSectionOnly);

                    for(AddSectionItem addSectionItem : selectedAddSectionsItems){
                        SubSectionsItem subSectionsItemTemp = Utils.convertAddSectionItemToSubSection(getActivity(),addSectionItem,""+ orderListItem.getSequence(),templateId);
                        alSubSections.add(subSectionsItemTemp);
                        orgList.add(subSectionsItemTemp);
                    }

                    if(activity != null){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                selectSectionAdapter.setData(alSubSectionsOnly);

                            }
                        });
                    }

                    selectSection.setSubSections(orgList);
                    prefs.putObject(Constants.PREF_SELECT_SECTION,selectSection);
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            if(showLoader) Utils.hideProgressBar(getActivity());
            if(aVoid) sSelectSection.setSelection(alSubSectionsOnly.size() - 1);
            errorCount();
        }
    }

    @Override
    public void onDestroy() {
        if (masterAsyncTask != null && !masterAsyncTask.isCancelled()) masterAsyncTask.cancel(true);
        if(addSectionSelectedAsyncTask != null && !addSectionSelectedAsyncTask.isCancelled()) addSectionSelectedAsyncTask.cancel(true);
        if(templateSelectedAsyncTask != null && !templateSelectedAsyncTask.isCancelled()) templateSelectedAsyncTask.cancel(true);
        if(subSectionSelectedAsyncTask != null && !subSectionSelectedAsyncTask.isCancelled()) subSectionSelectedAsyncTask.cancel(true);
        if(markAllSelectedAsyncTask != null && !markAllSelectedAsyncTask.isCancelled()) markAllSelectedAsyncTask.cancel(true);

        super.onDestroy();
    }

}