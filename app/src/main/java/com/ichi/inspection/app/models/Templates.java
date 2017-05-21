package com.ichi.inspection.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Templates {

    @SerializedName("TemplateItems")
    private List<TemplateItemsItem> templateItems = new ArrayList<>();

    public Templates() {
    }

    public void setTemplateItems(List<TemplateItemsItem> templateItems) {
        this.templateItems = templateItems;
    }

    public List<TemplateItemsItem> getTemplateItems() {
        return templateItems;
    }

    @Override
    public String toString() {
        return
                "Templates{" +
                        "templateItems = '" + templateItems + '\'' +
                        "}";
    }

    public List<TemplateItemsItem> getHeaderSections(String namedTemplateId){
        List<TemplateItemsItem> headerSections = new ArrayList<>();

        for(TemplateItemsItem templateItemsItem : templateItems){
            if(templateItemsItem.getNamedTemplateId().equalsIgnoreCase(namedTemplateId) && Boolean.parseBoolean(templateItemsItem.getIsHead())){
                headerSections.add(templateItemsItem);
            }
        }

        return headerSections;
    }
}