package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.models.AddSectionItem;
import com.ichi.inspection.app.models.TemplateItemsItem;
import com.ichi.inspection.app.utils.CustomTextView;

import java.util.List;

/**
 * Created by Palak on 21-05-2017.
 */

public class SelectSectionAdapter extends BaseAdapter {
    Context context;
    List<TemplateItemsItem> templateItemsItems;
    LayoutInflater inflter;

    public SelectSectionAdapter(Context applicationContext, List<TemplateItemsItem> templateItemsItems) {
        this.context = applicationContext;
        this.templateItemsItems = templateItemsItems;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<TemplateItemsItem> templateItemsItems){
        this.templateItemsItems.clear();
        this.templateItemsItems.addAll(templateItemsItems);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return templateItemsItems.size();
    }

    @Override
    public Object getItem(int i) {
        return templateItemsItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_spinner, null);
        CustomTextView txtItem = (CustomTextView) view.findViewById(R.id.txtItem);
        txtItem.setText(templateItemsItems.get(i).getName());
        return view;
    }
}
