package com.ichi.inspection.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.adapters.GridViewAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Mayank on 28-05-2017.
 */

public class GridActivity extends BaseActivity {
    private static final String TAG = GridActivity.class.getSimpleName();
    GridViewAdapter adapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        mContext = this;

        Intent intent=getIntent();
        ArrayList<String> imageURIs = intent.getStringArrayListExtra("URIs");


        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvGalleryView);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter=new GridViewAdapter(mContext,imageURIs);
        recyclerView.setAdapter(adapter);
    }
}
