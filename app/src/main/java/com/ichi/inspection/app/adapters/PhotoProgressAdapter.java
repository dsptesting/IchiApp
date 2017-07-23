package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.models.Photo;
import com.ichi.inspection.app.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 16-07-2017.
 */

public class PhotoProgressAdapter extends RecyclerView.Adapter<PhotoProgressAdapter.PhotoProgressHolder> {

    public static final String TAG = PhotoProgressAdapter.class.getSimpleName();
    private List<Photo> mList;
    private Context mContext;

    public PhotoProgressAdapter(Context context, List<Photo> mList) {
        this.mList = mList;
        mContext = context;
    }

    @Override
    public PhotoProgressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_upload, parent, false);

        return new PhotoProgressAdapter.PhotoProgressHolder(itemView);
    }

    public void setData(List<Photo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PhotoProgressHolder holder, int position) {
        Photo photo = getItem(position);
        holder.tvTitle.setText(photo.getPhotoName());
        if(photo.getUploadStatus() == Constants.UPLOAD_STATUS.PENDING.ordinal()){
            holder.tvProgress.setText("Pending");
        }
        else if(photo.getUploadStatus() == Constants.UPLOAD_STATUS.UPLOADING.ordinal()){
            holder.tvProgress.setText("Uploading..");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Photo getItem(int position) {
        return mList.get(position);
    }

    public class PhotoProgressHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvProgress)
        TextView tvProgress;

        public PhotoProgressHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
