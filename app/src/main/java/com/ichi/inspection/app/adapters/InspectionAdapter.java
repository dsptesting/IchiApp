package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 06-01-2017.
 */

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.InspectionHolder>{

    public static final String TAG = InspectionAdapter.class.getSimpleName();
    private List<String> mList;
    private Context mContext;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_LOCALTIME_DATE);
    private String status = "";

    public InspectionAdapter(Context context, List<String> mList) {
        this.mList = mList;
        mContext = context;
    }

    public void setData(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public InspectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection, parent, false);

        return new InspectionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InspectionHolder holder, int position) {

        String inspection = getItem(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public String getItem(int position) {
        return mList.get(position);
    }

    public class InspectionHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvBuyerName)
        TextView tvBuyerName;

        @BindView(R.id.tvInspectionNo)
        TextView tvInspectionNo;

        @BindView(R.id.tvCity)
        TextView tvCity;

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.tvTime)
        TextView tvTime;

        public InspectionHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}