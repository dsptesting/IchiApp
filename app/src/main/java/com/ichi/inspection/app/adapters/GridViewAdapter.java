package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ichi.inspection.app.R;
import com.ichi.inspection.app.interfaces.OnListItemClickListener;
import com.ichi.inspection.app.interfaces.OnListItemLongClickListener;
import com.ichi.inspection.app.utils.SquareImageView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mayank on 28-05-2017.
 */

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.MyGridHolder>{
    ArrayList<String> images;
    private Context mContext;
    private OnListItemClickListener onListItemClickListener;
    private OnListItemLongClickListener onListItemLongClickListener;
    public GridViewAdapter(Context mContext, ArrayList<String> images, OnListItemClickListener onListItemClickListener, OnListItemLongClickListener onListItemLongClickListener){
        this.images=images;
        this.mContext = mContext;
        this.onListItemClickListener=onListItemClickListener;
        this.onListItemLongClickListener=onListItemLongClickListener;
    }
    public void setData(ArrayList<String> images) {
        this.images=images;
        notifyDataSetChanged();
    }
    @Override
    public MyGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image, parent, false);
        return new MyGridHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyGridHolder holder, int position) {
        File file = new File(images.get(position));
        if(file != null && file.exists()){
            Glide.with(mContext)
                    .load(file) // Uri of the picture
                    .into(holder.imageView);
        }
        else{
            holder.imageView.setImageResource(R.color.blue);
        }

        //holder.imageView.setImageURI(Uri.parse(images.get(position)));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyGridHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    @BindView(R.id.imageView)
    SquareImageView imageView;

    public MyGridHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        imageView.setOnClickListener(this);
        imageView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onListItemClickListener.onListItemClick(v,getAdapterPosition());
    }

        @Override
        public boolean onLongClick(View v) {
            onListItemLongClickListener.onListItemLongClick(v,getAdapterPosition());
            return true;
        }
    }
}
