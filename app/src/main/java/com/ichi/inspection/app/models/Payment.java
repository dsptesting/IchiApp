package com.ichi.inspection.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Payment implements Parcelable {

	@SerializedName("AuthorizationCode")
	@Expose
	private String authorizationCode;

	@SerializedName("tranUserID")
	@Expose
	private String tranUserID;

	@SerializedName("CCNameOnCard")
	@Expose
	private String cCNameOnCard;

	@SerializedName("PaymentSequence")
	@Expose
	private String paymentSequence;

	@SerializedName("Amount")
	@Expose
	private String amount;

	@SerializedName("CCNumber")
	@Expose
	private String cCNumber;

	@SerializedName("CCCode")
	@Expose
	private String cCCode;

	@SerializedName("tranNo")
	@Expose
	private String tranNo;

	@SerializedName("CheckNumber")
	@Expose
	private String checkNumber;

	@SerializedName("CCExprMonth")
	@Expose
	private String cCExprMonth;

	@SerializedName("CCZip")
	@Expose
	private String cCZip;

	@SerializedName("CCCity")
	@Expose
	private String cCCity;

	@SerializedName("CCState")
	@Expose
	private String cCState;

	@SerializedName("tranResponse")
	@Expose
	private String tranResponse;

	@SerializedName("tranType")
	@Expose
	private String tranType;

	@SerializedName("CCAddress")
	@Expose
	private String cCAddress;

	@SerializedName("CCType")
	@Expose
	private String cCType;

	@SerializedName("PaymentType")
	@Expose
	private String paymentType;

	@SerializedName("tranDate")
	@Expose
	private String tranDate;

	@SerializedName("tranStoreNo2")
	@Expose
	private String tranStoreNo2;

	@SerializedName("CCExprYear")
	@Expose
	private String cCExprYear;

	@SerializedName("tranStoreNo1")
	@Expose
	private String tranStoreNo1;

	public void setAuthorizationCode(String authorizationCode){
		this.authorizationCode = authorizationCode;
	}

	public String getAuthorizationCode(){
		return authorizationCode;
	}

	public void setTranUserID(String tranUserID){
		this.tranUserID = tranUserID;
	}

	public String getTranUserID(){
		return tranUserID;
	}

	public void setCCNameOnCard(String cCNameOnCard){
		this.cCNameOnCard = cCNameOnCard;
	}

	public String getCCNameOnCard(){
		return cCNameOnCard;
	}

	public void setPaymentSequence(String paymentSequence){
		this.paymentSequence = paymentSequence;
	}

	public String getPaymentSequence(){
		return paymentSequence;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setCCNumber(String cCNumber){
		this.cCNumber = cCNumber;
	}

	public String getCCNumber(){
		return cCNumber;
	}

	public void setCCCode(String cCCode){
		this.cCCode = cCCode;
	}

	public String getCCCode(){
		return cCCode;
	}

	public void setTranNo(String tranNo){
		this.tranNo = tranNo;
	}

	public String getTranNo(){
		return tranNo;
	}

	public void setCheckNumber(String checkNumber){
		this.checkNumber = checkNumber;
	}

	public String getCheckNumber(){
		return checkNumber;
	}

	public void setCCExprMonth(String cCExprMonth){
		this.cCExprMonth = cCExprMonth;
	}

	public String getCCExprMonth(){
		return cCExprMonth;
	}

	public void setCCZip(String cCZip){
		this.cCZip = cCZip;
	}

	public String getCCZip(){
		return cCZip;
	}

	public void setCCCity(String cCCity){
		this.cCCity = cCCity;
	}

	public String getCCCity(){
		return cCCity;
	}

	public void setCCState(String cCState){
		this.cCState = cCState;
	}

	public String getCCState(){
		return cCState;
	}

	public void setTranResponse(String tranResponse){
		this.tranResponse = tranResponse;
	}

	public String getTranResponse(){
		return tranResponse;
	}

	public void setTranType(String tranType){
		this.tranType = tranType;
	}

	public String getTranType(){
		return tranType;
	}

	public void setCCAddress(String cCAddress){
		this.cCAddress = cCAddress;
	}

	public String getCCAddress(){
		return cCAddress;
	}

	public void setCCType(String cCType){
		this.cCType = cCType;
	}

	public String getCCType(){
		return cCType;
	}

	public void setPaymentType(String paymentType){
		this.paymentType = paymentType;
	}

	public String getPaymentType(){
		return paymentType;
	}

	public void setTranDate(String tranDate){
		this.tranDate = tranDate;
	}

	public String getTranDate(){
		return tranDate;
	}

	public void setTranStoreNo2(String tranStoreNo2){
		this.tranStoreNo2 = tranStoreNo2;
	}

	public String getTranStoreNo2(){
		return tranStoreNo2;
	}

	public void setCCExprYear(String cCExprYear){
		this.cCExprYear = cCExprYear;
	}

	public String getCCExprYear(){
		return cCExprYear;
	}

	public void setTranStoreNo1(String tranStoreNo1){
		this.tranStoreNo1 = tranStoreNo1;
	}

	public String getTranStoreNo1(){
		return tranStoreNo1;
	}

	@Override
	public String toString(){
		return
				"Payment{" +
						"authorizationCode = '" + authorizationCode + '\'' +
						",tranUserID = '" + tranUserID + '\'' +
						",cCNameOnCard = '" + cCNameOnCard + '\'' +
						",paymentSequence = '" + paymentSequence + '\'' +
						",amount = '" + amount + '\'' +
						",cCNumber = '" + cCNumber + '\'' +
						",cCCode = '" + cCCode + '\'' +
						",tranNo = '" + tranNo + '\'' +
						",checkNumber = '" + checkNumber + '\'' +
						",cCExprMonth = '" + cCExprMonth + '\'' +
						",cCZip = '" + cCZip + '\'' +
						",cCCity = '" + cCCity + '\'' +
						",cCState = '" + cCState + '\'' +
						",tranResponse = '" + tranResponse + '\'' +
						",tranType = '" + tranType + '\'' +
						",cCAddress = '" + cCAddress + '\'' +
						",cCType = '" + cCType + '\'' +
						",paymentType = '" + paymentType + '\'' +
						",tranDate = '" + tranDate + '\'' +
						",tranStoreNo2 = '" + tranStoreNo2 + '\'' +
						",cCExprYear = '" + cCExprYear + '\'' +
						",tranStoreNo1 = '" + tranStoreNo1 + '\'' +
						"}";
	}

	protected Payment(Parcel in) {
		authorizationCode = in.readString();
		tranUserID = in.readString();
		cCNameOnCard = in.readString();
		paymentSequence = in.readString();
		amount = in.readString();
		cCNumber = in.readString();
		cCCode = in.readString();
		tranNo = in.readString();
		checkNumber = in.readString();
		cCExprMonth = in.readString();
		cCZip = in.readString();
		cCCity = in.readString();
		cCState = in.readString();
		tranResponse = in.readString();
		tranType = in.readString();
		cCAddress = in.readString();
		cCType = in.readString();
		paymentType = in.readString();
		tranDate = in.readString();
		tranStoreNo2 = in.readString();
		cCExprYear = in.readString();
		tranStoreNo1 = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(authorizationCode);
		dest.writeString(tranUserID);
		dest.writeString(cCNameOnCard);
		dest.writeString(paymentSequence);
		dest.writeString(amount);
		dest.writeString(cCNumber);
		dest.writeString(cCCode);
		dest.writeString(tranNo);
		dest.writeString(checkNumber);
		dest.writeString(cCExprMonth);
		dest.writeString(cCZip);
		dest.writeString(cCCity);
		dest.writeString(cCState);
		dest.writeString(tranResponse);
		dest.writeString(tranType);
		dest.writeString(cCAddress);
		dest.writeString(cCType);
		dest.writeString(paymentType);
		dest.writeString(tranDate);
		dest.writeString(tranStoreNo2);
		dest.writeString(cCExprYear);
		dest.writeString(tranStoreNo1);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Payment> CREATOR = new Parcelable.Creator<Payment>() {
		@Override
		public Payment createFromParcel(Parcel in) {
			return new Payment(in);
		}

		@Override
		public Payment[] newArray(int size) {
			return new Payment[size];
		}
	};
}