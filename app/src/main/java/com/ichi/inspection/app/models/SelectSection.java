package com.ichi.inspection.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SelectSection{

    @SerializedName("SubSections")
    private List<SubSectionsItem> subSections = new ArrayList<>();

    public SelectSection() {}

	public void setSubSections(List<SubSectionsItem> subSections){
		this.subSections = subSections;
	}

	public List<SubSectionsItem> getSubSections(){
		return subSections;
	}

	@Override
 	public String toString(){
		return 
			"SelectSection{" + 
			"subSections = '" + subSections + '\'' + 
			"}";
		}
}