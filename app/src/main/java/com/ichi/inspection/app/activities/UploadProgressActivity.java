package com.ichi.inspection.app.activities;

import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.adapters.PhotoProgressAdapter;
import com.ichi.inspection.app.models.Photo;
import com.ichi.inspection.app.models.UploadPhotoList;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.Events;
import com.ichi.inspection.app.utils.PreferencesHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadProgressActivity extends AppCompatActivity {

    private static final String TAG = UploadProgressActivity.class.getSimpleName();

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.rcv)
    RecyclerView rcv;

    @Nullable
    @BindView(R.id.pbLoader)
    ProgressBar pbLoader;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private PhotoProgressAdapter photoProgressAdapter;

    private List<Photo> alPhotos;
    private PreferencesHelper pref;
    private UploadPhotoList uploadPhotoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_progress);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        pref = PreferencesHelper.getInstance(this);

        initUi();
    }

    private void initUi() {

        //Toolbar shit!
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        tvAppTitle.setText("Pending photos to upload");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        uploadPhotoList = (UploadPhotoList) pref.getObject(Constants.PREF_PHOTO_TO_BE_UPLOADED, UploadPhotoList.class);
        alPhotos = new ArrayList<>();
        if(uploadPhotoList != null){
            alPhotos.addAll(uploadPhotoList.getPhotos());
        }

        photoProgressAdapter = new PhotoProgressAdapter(this,alPhotos);
        LinearLayoutManager linearLayoutManagerCurrent = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManagerCurrent);
        rcv.setAdapter(photoProgressAdapter);

        setLayoutVisibility();
    }

    @Override
    protected void onDestroy() {

        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void setLayoutVisibility() {

        if(alPhotos != null && !alPhotos.isEmpty()) {

            tvNoData.setVisibility(View.GONE);
            pbLoader.setVisibility(View.GONE);
            rcv.setVisibility(View.VISIBLE);
        }
        else{
            tvNoData.setVisibility(View.VISIBLE);
            pbLoader.setVisibility(View.GONE);
            rcv.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Events.UploadPhotoStarted uploadPhotoStarted){

        //reload whole list
        uploadPhotoList = (UploadPhotoList) pref.getObject(Constants.PREF_PHOTO_TO_BE_UPLOADED, UploadPhotoList.class);
        alPhotos.clear();
        alPhotos.addAll(uploadPhotoList.getPhotos());
        photoProgressAdapter.setData(alPhotos);
        setLayoutVisibility();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Events.UploadPhotoStartedUploading uploadPhotoStartedUploading){

        //reload whole list
        for(int i=0;i<alPhotos.size();i++){
            if(alPhotos.get(i).getLineId().equalsIgnoreCase(uploadPhotoStartedUploading.getPhoto().getLineId())){
                alPhotos.get(i).setUploadStatus(Constants.UPLOAD_STATUS.UPLOADING.ordinal());
            }
        }
        photoProgressAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Events.UploadPhotoRemoved uploadPhotoRemoved){

        Iterator<Photo> photoIterator = alPhotos.iterator();

        while(photoIterator.hasNext()){
            Photo ph = photoIterator.next();
            if(ph!= null && ph.getLineId().equals(uploadPhotoRemoved.getPhoto().getLineId()) && ph.getPhotoName().equals(uploadPhotoRemoved.getPhoto().getPhotoName())){
                photoIterator.remove();
            }
        }
        photoProgressAdapter.notifyDataSetChanged();
        setLayoutVisibility();
    }
}
