package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.models.NamedTemplatesItem;
import com.ichi.inspection.app.utils.CustomTextView;

import java.util.List;

/**
 * Created by Palak on 21-05-2017.
 */

public class TemplateAdapter extends BaseAdapter {
    Context context;
    List<NamedTemplatesItem> namedTemplatesItems;
    LayoutInflater inflter;

    public TemplateAdapter(Context applicationContext, List<NamedTemplatesItem> namedTemplatesItems) {
        this.context = applicationContext;
        this.namedTemplatesItems = namedTemplatesItems;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<NamedTemplatesItem> namedTemplatesItems){
        this.namedTemplatesItems.clear();
        this.namedTemplatesItems.addAll(namedTemplatesItems);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return namedTemplatesItems.size();
    }

    @Override
    public Object getItem(int i) {
        return namedTemplatesItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_spinner, null);
        CustomTextView txtItem = (CustomTextView) view.findViewById(R.id.txtItem);
        txtItem.setText(namedTemplatesItems.get(i).getName());
        return view;
    }
}
