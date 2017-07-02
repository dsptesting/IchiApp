package com.ichi.inspection.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Payment implements Parcelable {

    @SerializedName("AuthorizationCode")
    @Expose
    private String authorizationCode;
    private boolean authorizatinCodeSynced;

    @SerializedName("tranUserID")
    @Expose
    private String tranUserID;

    @SerializedName("CCNameOnCard")
    @Expose
    private String cCNameOnCard;
    private boolean ccNameOnCardSynced;

    @SerializedName("PaymentSequence")
    @Expose
    private String paymentSequence;

    @SerializedName("Amount")
    @Expose
    private String amount;

    private boolean amountSynced;

    @SerializedName("CCNumber")
    @Expose
    private String cCNumber;
    private boolean ccNumberSynced;

    @SerializedName("CCCode")
    @Expose
    private String cCCode;
    private boolean ccCodeSynced;

    @SerializedName("tranNo")
    @Expose
    private String tranNo;

    @SerializedName("CheckNumber")
    @Expose
    private String checkNumber;
    private boolean checkNumberSynced;

    @SerializedName("CCExprMonth")
    @Expose
    private String cCExprMonth;
    private boolean ccExprMonthSynced;

    @SerializedName("CCZip")
    @Expose
    private String cCZip;
    private boolean ccZipSynced;

    @SerializedName("CCCity")
    @Expose
    private String cCCity;
    private boolean ccCitySynced;

    @SerializedName("CCState")
    @Expose
    private String cCState;
    private boolean ccStateSynced;

    @SerializedName("tranResponse")
    @Expose
    private String tranResponse;

    @SerializedName("tranType")
    @Expose
    private String tranType;
    private boolean tranTypeSynced;

    @SerializedName("CCAddress")
    @Expose
    private String cCAddress;
    private boolean ccAddressSynced;

    @SerializedName("CCType")
    @Expose
    private String cCType;
    private boolean cCTypeSynced;

    @SerializedName("PaymentType")
    @Expose
    private String paymentType;
    private boolean paymentTypeSynced;

    @SerializedName("tranDate")
    @Expose
    private String tranDate;

    @SerializedName("tranStoreNo2")
    @Expose
    private String tranStoreNo2;

    @SerializedName("CCExprYear")
    @Expose
    private String cCExprYear;
    private boolean cCExprYearSynced;

    @SerializedName("tranStoreNo1")
    @Expose
    private String tranStoreNo1;

    @SerializedName("InspectionId")
    @Expose
    private String InspectionId;


    @Override
    public String toString() {
        return "Payment{" +
                "authorizationCode='" + authorizationCode + '\'' +
                ", authorizatinCodeSynced=" + authorizatinCodeSynced +
                ", tranUserID='" + tranUserID + '\'' +
                ", cCNameOnCard='" + cCNameOnCard + '\'' +
                ", ccNameOnCardSynced=" + ccNameOnCardSynced +
                ", paymentSequence='" + paymentSequence + '\'' +
                ", amount='" + amount + '\'' +
                ", amountSynced=" + amountSynced +
                ", cCNumber='" + cCNumber + '\'' +
                ", ccNumberSynced=" + ccNumberSynced +
                ", cCCode='" + cCCode + '\'' +
                ", ccCodeSynced=" + ccCodeSynced +
                ", tranNo='" + tranNo + '\'' +
                ", checkNumber='" + checkNumber + '\'' +
                ", checkNumberSynced=" + checkNumberSynced +
                ", cCExprMonth='" + cCExprMonth + '\'' +
                ", ccExprMonthSynced=" + ccExprMonthSynced +
                ", cCZip='" + cCZip + '\'' +
                ", ccZipSynced=" + ccZipSynced +
                ", cCCity='" + cCCity + '\'' +
                ", ccCitySynced=" + ccCitySynced +
                ", cCState='" + cCState + '\'' +
                ", ccStateSynced=" + ccStateSynced +
                ", tranResponse='" + tranResponse + '\'' +
                ", tranType='" + tranType + '\'' +
                ", tranTypeSynced=" + tranTypeSynced +
                ", cCAddress='" + cCAddress + '\'' +
                ", ccAddressSynced=" + ccAddressSynced +
                ", cCType='" + cCType + '\'' +
                ", cCTypeSynced=" + cCTypeSynced +
                ", paymentType='" + paymentType + '\'' +
                ", paymentTypeSynced=" + paymentTypeSynced +
                ", tranDate='" + tranDate + '\'' +
                ", tranStoreNo2='" + tranStoreNo2 + '\'' +
                ", cCExprYear='" + cCExprYear + '\'' +
                ", cCExprYearSynced=" + cCExprYearSynced +
                ", tranStoreNo1='" + tranStoreNo1 + '\'' +
                ", InspectionId='" + InspectionId + '\'' +
                '}';
    }

    public String getInspectionId() {
        return InspectionId;
    }

    public void setInspectionId(String inspectionId) {
        InspectionId = inspectionId;
    }

    public boolean isPaymentTypeSynced() {
        return paymentTypeSynced;
    }

    public void setPaymentTypeSynced(boolean paymentTypeSynced) {
        this.paymentTypeSynced = paymentTypeSynced;
    }

    public boolean iscCExprYearSynced() {
        return cCExprYearSynced;
    }

    public void setcCExprYearSynced(boolean cCExprYearSynced) {
        this.cCExprYearSynced = cCExprYearSynced;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public boolean isAuthorizatinCodeSynced() {
        return authorizatinCodeSynced;
    }

    public void setAuthorizatinCodeSynced(boolean authorizatinCodeSynced) {
        this.authorizatinCodeSynced = authorizatinCodeSynced;
    }

    public String getTranUserID() {
        return tranUserID;
    }

    public void setTranUserID(String tranUserID) {
        this.tranUserID = tranUserID;
    }

    public String getcCNameOnCard() {
        return cCNameOnCard;
    }

    public void setcCNameOnCard(String cCNameOnCard) {
        this.cCNameOnCard = cCNameOnCard;
    }

    public boolean isCcNameOnCardSynced() {
        return ccNameOnCardSynced;
    }

    public void setCcNameOnCardSynced(boolean ccNameOnCardSynced) {
        this.ccNameOnCardSynced = ccNameOnCardSynced;
    }

    public String getPaymentSequence() {
        return paymentSequence;
    }

    public void setPaymentSequence(String paymentSequence) {
        this.paymentSequence = paymentSequence;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isAmountSynced() {
        return amountSynced;
    }

    public void setAmountSynced(boolean amountSynced) {
        this.amountSynced = amountSynced;
    }

    public String getcCNumber() {
        return cCNumber;
    }

    public void setcCNumber(String cCNumber) {
        this.cCNumber = cCNumber;
    }

    public boolean isCcNumberSynced() {
        return ccNumberSynced;
    }

    public void setCcNumberSynced(boolean ccNumberSynced) {
        this.ccNumberSynced = ccNumberSynced;
    }

    public String getcCCode() {
        return cCCode;
    }

    public void setcCCode(String cCCode) {
        this.cCCode = cCCode;
    }

    public boolean isCcCodeSynced() {
        return ccCodeSynced;
    }

    public void setCcCodeSynced(boolean ccCodeSynced) {
        this.ccCodeSynced = ccCodeSynced;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public boolean isCheckNumberSynced() {
        return checkNumberSynced;
    }

    public void setCheckNumberSynced(boolean checkNumberSynced) {
        this.checkNumberSynced = checkNumberSynced;
    }

    public String getcCExprMonth() {
        return cCExprMonth;
    }

    public void setcCExprMonth(String cCExprMonth) {
        this.cCExprMonth = cCExprMonth;
    }

    public boolean isCcExprMonthSynced() {
        return ccExprMonthSynced;
    }

    public void setCcExprMonthSynced(boolean ccExprMonthSynced) {
        this.ccExprMonthSynced = ccExprMonthSynced;
    }

    public String getcCZip() {
        return cCZip;
    }

    public void setcCZip(String cCZip) {
        this.cCZip = cCZip;
    }

    public boolean isCcZipSynced() {
        return ccZipSynced;
    }

    public void setCcZipSynced(boolean ccZipSynced) {
        this.ccZipSynced = ccZipSynced;
    }

    public String getcCCity() {
        return cCCity;
    }

    public void setcCCity(String cCCity) {
        this.cCCity = cCCity;
    }

    public boolean isCcCitySynced() {
        return ccCitySynced;
    }

    public void setCcCitySynced(boolean ccCitySynced) {
        this.ccCitySynced = ccCitySynced;
    }

    public String getcCState() {
        return cCState;
    }

    public void setcCState(String cCState) {
        this.cCState = cCState;
    }

    public boolean isCcStateSynced() {
        return ccStateSynced;
    }

    public void setCcStateSynced(boolean ccStateSynced) {
        this.ccStateSynced = ccStateSynced;
    }

    public String getTranResponse() {
        return tranResponse;
    }

    public void setTranResponse(String tranResponse) {
        this.tranResponse = tranResponse;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public boolean isTranTypeSynced() {
        return tranTypeSynced;
    }

    public void setTranTypeSynced(boolean tranTypeSynced) {
        this.tranTypeSynced = tranTypeSynced;
    }

    public String getcCAddress() {
        return cCAddress;
    }

    public void setcCAddress(String cCAddress) {
        this.cCAddress = cCAddress;
    }

    public boolean isCcAddressSynced() {
        return ccAddressSynced;
    }

    public void setCcAddressSynced(boolean ccAddressSynced) {
        this.ccAddressSynced = ccAddressSynced;
    }

    public String getcCType() {
        return cCType;
    }

    public void setcCType(String cCType) {
        this.cCType = cCType;
    }

    public boolean iscCTypeSynced() {
        return cCTypeSynced;
    }

    public void setcCTypeSynced(boolean cCTypeSynced) {
        this.cCTypeSynced = cCTypeSynced;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranStoreNo2() {
        return tranStoreNo2;
    }

    public void setTranStoreNo2(String tranStoreNo2) {
        this.tranStoreNo2 = tranStoreNo2;
    }

    public String getcCExprYear() {
        return cCExprYear;
    }

    public void setcCExprYear(String cCExprYear) {
        this.cCExprYear = cCExprYear;
    }

    public String getTranStoreNo1() {
        return tranStoreNo1;
    }

    public void setTranStoreNo1(String tranStoreNo1) {
        this.tranStoreNo1 = tranStoreNo1;
    }

    protected Payment(Parcel in) {
        authorizationCode = in.readString();
        authorizatinCodeSynced = in.readByte() != 0x00;
        tranUserID = in.readString();
        cCNameOnCard = in.readString();
        ccNameOnCardSynced = in.readByte() != 0x00;
        paymentSequence = in.readString();
        amount = in.readString();
        amountSynced = in.readByte() != 0x00;
        cCNumber = in.readString();
        ccNumberSynced = in.readByte() != 0x00;
        cCCode = in.readString();
        ccCodeSynced = in.readByte() != 0x00;
        tranNo = in.readString();
        checkNumber = in.readString();
        checkNumberSynced = in.readByte() != 0x00;
        cCExprMonth = in.readString();
        ccExprMonthSynced = in.readByte() != 0x00;
        cCZip = in.readString();
        ccZipSynced = in.readByte() != 0x00;
        cCCity = in.readString();
        ccCitySynced = in.readByte() != 0x00;
        cCState = in.readString();
        ccStateSynced = in.readByte() != 0x00;
        tranResponse = in.readString();
        tranType = in.readString();
        tranTypeSynced = in.readByte() != 0x00;
        cCAddress = in.readString();
        ccAddressSynced = in.readByte() != 0x00;
        cCType = in.readString();
        cCTypeSynced = in.readByte() != 0x00;
        paymentType = in.readString();
        paymentTypeSynced = in.readByte() != 0x00;
        tranDate = in.readString();
        tranStoreNo2 = in.readString();
        cCExprYear = in.readString();
        cCExprYearSynced = in.readByte() != 0x00;
        tranStoreNo1 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authorizationCode);
        dest.writeByte((byte) (authorizatinCodeSynced ? 0x01 : 0x00));
        dest.writeString(tranUserID);
        dest.writeString(cCNameOnCard);
        dest.writeByte((byte) (ccNameOnCardSynced ? 0x01 : 0x00));
        dest.writeString(paymentSequence);
        dest.writeString(amount);
        dest.writeByte((byte) (amountSynced ? 0x01 : 0x00));
        dest.writeString(cCNumber);
        dest.writeByte((byte) (ccNumberSynced ? 0x01 : 0x00));
        dest.writeString(cCCode);
        dest.writeByte((byte) (ccCodeSynced ? 0x01 : 0x00));
        dest.writeString(tranNo);
        dest.writeString(checkNumber);
        dest.writeByte((byte) (checkNumberSynced ? 0x01 : 0x00));
        dest.writeString(cCExprMonth);
        dest.writeByte((byte) (ccExprMonthSynced ? 0x01 : 0x00));
        dest.writeString(cCZip);
        dest.writeByte((byte) (ccZipSynced ? 0x01 : 0x00));
        dest.writeString(cCCity);
        dest.writeByte((byte) (ccCitySynced ? 0x01 : 0x00));
        dest.writeString(cCState);
        dest.writeByte((byte) (ccStateSynced ? 0x01 : 0x00));
        dest.writeString(tranResponse);
        dest.writeString(tranType);
        dest.writeByte((byte) (tranTypeSynced ? 0x01 : 0x00));
        dest.writeString(cCAddress);
        dest.writeByte((byte) (ccAddressSynced ? 0x01 : 0x00));
        dest.writeString(cCType);
        dest.writeByte((byte) (cCTypeSynced ? 0x01 : 0x00));
        dest.writeString(paymentType);
        dest.writeByte((byte) (paymentTypeSynced ? 0x01 : 0x00));
        dest.writeString(tranDate);
        dest.writeString(tranStoreNo2);
        dest.writeString(cCExprYear);
        dest.writeByte((byte) (cCExprYearSynced ? 0x01 : 0x00));
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