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

    public List<SubSectionsItem> getSubSections(String inspectionId){

        List<SubSectionsItem> subSectionsItemsWithInspectionId  = new ArrayList<>();
        for(SubSectionsItem subSectionsItem : subSections){
            if(subSectionsItem != null && subSectionsItem.getInspectionId().equalsIgnoreCase(inspectionId)){
                subSectionsItemsWithInspectionId.add(subSectionsItem);
            }
        }
        return subSectionsItemsWithInspectionId;
    }

	@Override
 	public String toString(){
		return 
			"SelectSection{" + 
			"subSections = '" + subSections + '\'' + 
			"}";
		}
}