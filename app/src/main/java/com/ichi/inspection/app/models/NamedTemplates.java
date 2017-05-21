package com.ichi.inspection.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NamedTemplates {

    @SerializedName("Items")
    private List<NamedTemplatesItem> items = new ArrayList<>();

    public NamedTemplates() {}

    public void setNamedTemplatesItems(List<NamedTemplatesItem> items) {
        this.items = items;
    }

    public List<NamedTemplatesItem> getNamedTemplatesItems() {
        return items;
    }

    @Override
    public String toString() {
        return
                "NamedTemplates{" +
                        "items = '" + items + '\'' +
                        "}";
    }
}