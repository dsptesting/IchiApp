package com.ichi.inspection.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.models.AddSectionItem;
import com.ichi.inspection.app.models.SubSectionsItem;
import com.ichi.inspection.app.models.TemplateItemsItem;
import com.ichi.inspection.app.utils.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Palak on 21-05-2017.
 */

public class SelectSectionAdapter extends BaseAdapter {
    Context context;
    List<SubSectionsItem> subSectionsItems;
    LayoutInflater inflter;

    public SelectSectionAdapter(Context applicationContext, List<SubSectionsItem> subSectionsItems) {
        this.context = applicationContext;
        this.subSectionsItems = new ArrayList<>();
        this.subSectionsItems.addAll(subSectionsItems);
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<SubSectionsItem> subSectionsItems){
        this.subSectionsItems.clear();
        this.subSectionsItems.addAll(subSectionsItems);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return subSectionsItems.size();
    }

    @Override
    public Object getItem(int i) {
        return subSectionsItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_spinner, viewGroup,false);
        CustomTextView txtItem = (CustomTextView) view.findViewById(R.id.txtItem);
        txtItem.setText(subSectionsItems.get(i).getName());
        return view;
    }
}
