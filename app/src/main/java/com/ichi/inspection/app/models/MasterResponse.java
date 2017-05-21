package com.ichi.inspection.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterResponse extends BaseResponse{

    @SerializedName("InspectorId")
    private String inspectorId;

    @SerializedName("CompanyId")
    private String companyId;

    @SerializedName("SelectSection")
    private SelectSection selectSection;

    @SerializedName("NamedTemplates")
    private NamedTemplates namedTemplates;

    @SerializedName("Templates")
    private Templates templates;

    @SerializedName("AddSection")
	private List<AddSectionItem> addSection;

	public void setInspectorId(String inspectorId){
		this.inspectorId = inspectorId;
	}

	public String getInspectorId(){
		return inspectorId;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setSelectSection(SelectSection selectSection){
		this.selectSection = selectSection;
	}

	public SelectSection getSelectSection(){
		return selectSection;
	}

	public void setNamedTemplates(NamedTemplates namedTemplates){
		this.namedTemplates = namedTemplates;
	}

	public NamedTemplates getNamedTemplates(){
		return namedTemplates;
	}

	public void setTemplates(Templates templates){
		this.templates = templates;
	}

	public Templates getTemplates(){
		return templates;
	}

	public void setAddSection(List<AddSectionItem> addSection){
		this.addSection = addSection;
	}

	public List<AddSectionItem> getAddSection(){
		return addSection;
	}

	@Override
 	public String toString(){
		return 
			"MasterResponse{" + 
			"inspectorId = '" + inspectorId + '\'' + 
			",companyId = '" + companyId + '\'' + 
			",selectSection = '" + selectSection + '\'' + 
			",namedTemplates = '" + namedTemplates + '\'' + 
			",templates = '" + templates + '\'' + 
			",addSection = '" + addSection + '\'' + 
			"}";
		}
}