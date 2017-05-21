package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.models.AddSectionItem;
import com.ichi.inspection.app.models.NamedTemplatesItem;
import com.ichi.inspection.app.utils.CustomTextView;

import java.util.List;

/**
 * Created by Palak on 21-05-2017.
 */

public class AddSectionAdapter extends BaseAdapter {
    Context context;
    List<AddSectionItem> addSectionItems;
    LayoutInflater inflter;

    public AddSectionAdapter(Context applicationContext, List<AddSectionItem> addSectionItems) {
        this.context = applicationContext;
        this.addSectionItems = addSectionItems;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<AddSectionItem> addSectionItems){
        this.addSectionItems.clear();
        this.addSectionItems.addAll(addSectionItems);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return addSectionItems.size();
    }

    @Override
    public Object getItem(int i) {
        return addSectionItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_spinner, null);
        CustomTextView txtItem = (CustomTextView) view.findViewById(R.id.txtItem);
        txtItem.setText(addSectionItems.get(i).getName());
        return view;
    }
}
