package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.interfaces.OnListItemClickListener;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.SubSectionsItem;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.CustomButton;
import com.ichi.inspection.app.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 06-01-2017.
 */

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.LineHolder>{

    public static final String TAG = LineAdapter.class.getSimpleName();
    private List<SubSectionsItem> mList;
    private Context mContext;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_LOCALTIME_DATE);
    private OnListItemClickListener onListItemClickListener;
    private String status = "";

    public LineAdapter(Context context, List<SubSectionsItem> mList, OnListItemClickListener onListItemClickListener) {
        this.mList = mList;
        mContext = context;
        this.onListItemClickListener = onListItemClickListener;
    }

    public void setData(List<SubSectionsItem> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection_detail_line, parent, false);

        return new LineHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LineHolder holder, int position) {

        SubSectionsItem subSectionsItem = getItem(position);
        holder.tvName.setText(subSectionsItem.getName()+"");

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public SubSectionsItem getItem(int position) {
        return mList.get(position);
    }

    public class LineHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.btnS)
        CustomButton btnS;

        @BindView(R.id.btnR)
        CustomButton btnR;

        @BindView(R.id.btnU)
        CustomButton btnU;

        @BindView(R.id.btnHide)
        CustomButton btnHide;

        @BindView(R.id.btnNA)
        CustomButton btnNA;

        public LineHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

        }
    }
}