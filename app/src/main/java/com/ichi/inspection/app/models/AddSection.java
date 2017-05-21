package com.ichi.inspection.app.models;

import java.util.ArrayList;
import java.util.List;

public class AddSection {

    private List<AddSectionItem> items = new ArrayList<>();

    public AddSection() {}

    public AddSection(List<AddSectionItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "AddSection{" +
                "items=" + items +
                '}';
    }

    public List<AddSectionItem> getItems() {
        return items;
    }

    public void setItems(List<AddSectionItem> items) {
        this.items = items;
    }

    public List<AddSectionItem> getHeaderItems(){
        List<AddSectionItem> headerItems = new ArrayList<>();

        for(AddSectionItem addSectionItem : items){
            if(Boolean.parseBoolean(addSectionItem.getIsHead())){
                headerItems.add(addSectionItem);
            }
        }

        return headerItems;
    }
}
