package com.ichi.inspection.app.models;

import com.google.gson.annotations.SerializedName;

public class AddSectionItem {

	@SerializedName("AddSectionId")
	private String addSectionId;

	@SerializedName("IsHead")
	private String isHead;

	@SerializedName("CompanyId")
	private String companyId;

	@SerializedName("IncludeInReports")
	private String includeInReports;

	@SerializedName("CommentsRequired")
	private String commentsRequired;

	@SerializedName("PopupId")
	private String popupId;

	@SerializedName("SectionId")
	private String sectionId;

	@SerializedName("Name")
	private String name;

	@SerializedName("HasRating")
	private String hasRating;

	public AddSectionItem(String name) {
		this.name = name;
	}

	public void setAddSectionId(String addSectionId){
		this.addSectionId = addSectionId;
	}

	public String getAddSectionId(){
		return addSectionId;
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

	public void setIncludeInReports(String includeInReports){
		this.includeInReports = includeInReports;
	}

	public String getIncludeInReports(){
		return includeInReports;
	}

	public void setCommentsRequired(String commentsRequired){
		this.commentsRequired = commentsRequired;
	}

	public String getCommentsRequired(){
		return commentsRequired;
	}

	public void setPopupId(String popupId){
		this.popupId = popupId;
	}

	public String getPopupId(){
		return popupId;
	}

	public void setSectionId(String sectionId){
		this.sectionId = sectionId;
	}

	public String getSectionId(){
		return sectionId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setHasRating(String hasRating){
		this.hasRating = hasRating;
	}

	public String getHasRating(){
		return hasRating;
	}

	@Override
 	public String toString(){
		return 
			"AddSectionItem{" + 
			"addSectionId = '" + addSectionId + '\'' + 
			",isHead = '" + isHead + '\'' + 
			",companyId = '" + companyId + '\'' + 
			",includeInReports = '" + includeInReports + '\'' + 
			",commentsRequired = '" + commentsRequired + '\'' + 
			",popupId = '" + popupId + '\'' + 
			",sectionId = '" + sectionId + '\'' + 
			",name = '" + name + '\'' + 
			",hasRating = '" + hasRating + '\'' + 
			"}";
		}
}