package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.interfaces.OnLineItemClickListener;
import com.ichi.inspection.app.interfaces.OnListItemClickListener;
import com.ichi.inspection.app.models.OrderListItem;
import com.ichi.inspection.app.models.SubSectionsItem;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.CustomButton;
import com.ichi.inspection.app.utils.CustomTextView;
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
    private OnLineItemClickListener onLineItemClickListener;
    private String status = "";
    BottomSheetBehavior behavior;


    public LineAdapter(Context context, List<SubSectionsItem> mList, OnListItemClickListener onListItemClickListener, BottomSheetBehavior behavior
        , OnLineItemClickListener onLineItemClickListener) {
        this.mList = mList;
        mContext = context;
        this.onLineItemClickListener = onLineItemClickListener;
        this.onListItemClickListener = onListItemClickListener;
        this.behavior=behavior;
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
    public void onBindViewHolder(final LineHolder holder, int position) {

        SubSectionsItem subSectionsItem = getItem(position);
        holder.tvName.setText(subSectionsItem.getName()+"");


        if(subSectionsItem.getGood().equalsIgnoreCase("t")){
            holder.btnS.setSelected(true);
            holder.btnR.setSelected(false);
            holder.btnU.setSelected(false);
        }
        else if(subSectionsItem.getFair().equalsIgnoreCase("t")){
            holder.btnS.setSelected(false);
            holder.btnR.setSelected(true);
            holder.btnU.setSelected(false);
        }
        else if(subSectionsItem.getPoor().equalsIgnoreCase("t")){
            holder.btnS.setSelected(false);
            holder.btnR.setSelected(false);
            holder.btnU.setSelected(true);
        }
        else{
            holder.btnS.setSelected(false);
            holder.btnR.setSelected(false);
            holder.btnU.setSelected(false);
        }

        try{
            if(subSectionsItem.getComments() != null && !subSectionsItem.getComments().toString().trim().isEmpty()){
                holder.txtComment.setText(""+subSectionsItem.getComments().toString().trim());
            }
            else{
                holder.txtComment.setText("Add comment");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public SubSectionsItem getItem(int position) {
        return mList.get(position);
    }

    public class LineHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

        @BindView(R.id.btnUpload)
        CustomButton btnUpload;

        @BindView(R.id.btnPhoto)
        CustomButton btnPhoto;

        @BindView(R.id.llAddComment)
        LinearLayout llAddComment;

        @BindView(R.id.txtComment)
        CustomTextView txtComment;

        public LineHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            btnS.setOnClickListener(this);
            btnR.setOnClickListener(this);
            btnU.setOnClickListener(this);
            btnNA.setOnClickListener(this);
            btnHide.setOnClickListener(this);
            llAddComment.setOnClickListener(this);
            btnUpload.setOnClickListener(this);
            btnPhoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //onListItemClickListener.onListItemClick(v,getAdapterPosition());
            switch (view.getId()){
                case R.id.btnS:
                    SubSectionsItem subSectionsItem = getItem(getAdapterPosition());
                    subSectionsItem.setGood("t");
                    subSectionsItem.setFair("f");
                    subSectionsItem.setPoor("f");
                    onLineItemClickListener.onLineItemClick(view,subSectionsItem,getAdapterPosition());
                    notifyDataSetChanged();
                    break;
                case R.id.btnR:
                    subSectionsItem = getItem(getAdapterPosition());
                    subSectionsItem.setGood("f");
                    subSectionsItem.setFair("t");
                    subSectionsItem.setPoor("f");
                    onLineItemClickListener.onLineItemClick(view,subSectionsItem,getAdapterPosition());
                    notifyDataSetChanged();
                    break;
                case R.id.btnU:
                    subSectionsItem = getItem(getAdapterPosition());
                    subSectionsItem.setGood("f");
                    subSectionsItem.setFair("f");
                    subSectionsItem.setPoor("t");
                    onLineItemClickListener.onLineItemClick(view,subSectionsItem,getAdapterPosition());
                    notifyDataSetChanged();
                    break;
                case R.id.llAddComment:
                    subSectionsItem = getItem(getAdapterPosition());
                    onLineItemClickListener.onLineItemClick(view,subSectionsItem,getAdapterPosition());
                    break;
            }
        }
    }
}