package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.activities.BaseActivity;
import com.ichi.inspection.app.models.NamedTemplatesItem;
import com.ichi.inspection.app.utils.CustomTextView;

import java.util.List;

/**
 * Created by Mayank on 03-06-2017.
 */

public class MarkAllAdapter extends BaseAdapter {
    Context context;
    List<String> markAllLines;
    LayoutInflater inflter;
    public MarkAllAdapter(Context context,List<String> markAllLines){
        this.context = context;
        this.markAllLines = markAllLines;
        inflter = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return markAllLines.size();
    }

    @Override
    public Object getItem(int position) {
        return markAllLines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.item_spinner, null);
        CustomTextView txtItem = (CustomTextView) view.findViewById(R.id.txtItem);
        txtItem.setText(markAllLines.get(position));
        return view;
    }
}
