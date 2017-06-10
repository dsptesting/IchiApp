package com.ichi.inspection.app.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ichi.inspection.app.R;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mayank on 04-06-2017.
 */

public class GridImageActivity extends BaseActivity {
    private Context mContext;

    @BindView(R.id.tbAppToolbarNormal)
    public Toolbar toolbar;

    @BindView(R.id.tvAppTitle)
    public TextView tvAppTitle;

    @BindView(R.id.imageView)
    public ImageView imageView;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_image);
        ButterKnife.bind(this);
        mContext = this;
        name = getIntent().getStringExtra("name");

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
        Bundle bundle=intent.getExtras();
        String uri= bundle.getString("URI");
        imageView.setImageURI(Uri.parse(uri));
    }
}
