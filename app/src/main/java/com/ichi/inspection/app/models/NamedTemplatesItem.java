package com.ichi.inspection.app.models;

import com.google.gson.annotations.SerializedName;

public class NamedTemplatesItem {

    @SerializedName("CompanyId")
    private String companyId;

    @SerializedName("IsActive")
    private String isActive;

    @SerializedName("NamedTemplateId")
    private String namedTemplateId;

    @SerializedName("Name")
    private String name;

	public NamedTemplatesItem(String name) {
		this.name = name;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setIsActive(String isActive){
		this.isActive = isActive;
	}

	public String getIsActive(){
		return isActive;
	}

	public void setNamedTemplateId(String namedTemplateId){
		this.namedTemplateId = namedTemplateId;
	}

	public String getNamedTemplateId(){
		return namedTemplateId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"NamedTemplatesItem{" +
			"companyId = '" + companyId + '\'' + 
			",isActive = '" + isActive + '\'' + 
			",namedTemplateId = '" + namedTemplateId + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}
