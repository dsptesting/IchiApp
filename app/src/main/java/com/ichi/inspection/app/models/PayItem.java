package com.ichi.inspection.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PayItem implements Parcelable {
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

	protected PayItem(Parcel in) {
		companyId = in.readInt();
		data = in.readString();
		sequence = in.readInt();
		code = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(companyId);
		dest.writeString(data);
		dest.writeInt(sequence);
		dest.writeString(code);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<PayItem> CREATOR = new Parcelable.Creator<PayItem>() {
		@Override
		public PayItem createFromParcel(Parcel in) {
			return new PayItem(in);
		}

		@Override
		public PayItem[] newArray(int size) {
			return new PayItem[size];
		}
	};
}