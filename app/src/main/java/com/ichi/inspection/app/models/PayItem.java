package com.ichi.inspection.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PayItem{

	@SerializedName("CompanyId")
	@Expose
	private int companyId;

	@SerializedName("Data")
	@Expose
	private String data;

	@SerializedName("Sequence")
	@Expose
	private int sequence;

	@SerializedName("Code")
	@Expose
	private String code;

	public void setCompanyId(int companyId){
		this.companyId = companyId;
	}

	public int getCompanyId(){
		return companyId;
	}

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	public void setSequence(int sequence){
		this.sequence = sequence;
	}

	public int getSequence(){
		return sequence;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	@Override
 	public String toString(){
		return 
			"PayItem{" + 
			"companyId = '" + companyId + '\'' + 
			",data = '" + data + '\'' + 
			",sequence = '" + sequence + '\'' + 
			",code = '" + code + '\'' + 
			"}";
		}
}