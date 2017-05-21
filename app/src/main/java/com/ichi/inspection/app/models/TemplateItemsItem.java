package com.ichi.inspection.app.models;

import com.google.gson.annotations.SerializedName;

public class TemplateItemsItem{

    @SerializedName("UsedHead")
    private String usedHead;

    @SerializedName("IsHead")
    private String isHead;

    @SerializedName("CompanyId")
    private String companyId;

    @SerializedName("Comments")
    private String comments;

    @SerializedName("NamedTemplateId")
    private String namedTemplateId;

    @SerializedName("SectionId")
    private String sectionId;

    @SerializedName("TemplateId")
    private String templateId;

    @SerializedName("Name")
    private String name;

    @SerializedName("NotInspected")
    private String notInspected;

	public void setUsedHead(String usedHead){
		this.usedHead = usedHead;
	}

	public String getUsedHead(){
		return usedHead;
	}

	public void setIsHead(String isHead){
		this.isHead = isHead;
	}

	public String getIsHead(){
		return isHead;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public String getComments(){
		return comments;
	}

	public void setNamedTemplateId(String namedTemplateId){
		this.namedTemplateId = namedTemplateId;
	}

	public String getNamedTemplateId(){
		return namedTemplateId;
	}

	public void setSectionId(String sectionId){
		this.sectionId = sectionId;
	}

	public String getSectionId(){
		return sectionId;
	}

	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}

	public String getTemplateId(){
		return templateId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setNotInspected(String notInspected){
		this.notInspected = notInspected;
	}

	public String getNotInspected(){
		return notInspected;
	}

	@Override
 	public String toString(){
		return 
			"TemplateItemsItem{" + 
			"usedHead = '" + usedHead + '\'' + 
			",isHead = '" + isHead + '\'' + 
			",companyId = '" + companyId + '\'' + 
			",comments = '" + comments + '\'' + 
			",namedTemplateId = '" + namedTemplateId + '\'' + 
			",sectionId = '" + sectionId + '\'' + 
			",templateId = '" + templateId + '\'' + 
			",name = '" + name + '\'' + 
			",notInspected = '" + notInspected + '\'' + 
			"}";
		}
}
