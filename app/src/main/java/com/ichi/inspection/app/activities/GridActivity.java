package com.ichi.inspection.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.adapters.GridViewAdapter;
import com.ichi.inspection.app.fragments.InspectionDetailsFragment;
import com.ichi.inspection.app.interfaces.OnListItemClickListener;
import com.ichi.inspection.app.interfaces.OnListItemLongClickListener;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Mayank on 28-05-2017.
 */

public class GridActivity extends BaseActivity implements OnListItemClickListener,OnListItemLongClickListener {
    private static final String TAG = GridActivity.class.getSimpleName();
    GridViewAdapter adapter;
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;
    private ArrayList<String> imageURIs;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        mContext = this;
        name = getIntent().getStringExtra("name");
        init();
    }

    private void init(){
        //Toolbar shit!
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        tvAppTitle.setText("Pictures of "+name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });


        Intent intent=getIntent();
        imageURIs = intent.getStringArrayListExtra("URIs");


        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvGalleryView);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter=new GridViewAdapter(mContext,imageURIs,this,this);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onListItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.imageView:
                Intent intent=new Intent(GridActivity.this,GridImageActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("URI",imageURIs.get(position));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onListItemLongClick(View view, final int position) {
        switch (view.getId()){
            case R.id.imageView:
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mContext);
                View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_remove_photo, null);
                mBottomSheetDialog.setContentView(sheetView);

                LinearLayout llRemove= (LinearLayout) sheetView.findViewById(R.id.llRemove);
                llRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String removeImageUri=imageURIs.get(position);
                        imageURIs.remove(position);
                        adapter.setData(imageURIs);
                        mBottomSheetDialog.dismiss();
                        File file=new File(removeImageUri);
                        file.delete();

                    }
                });
                mBottomSheetDialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
