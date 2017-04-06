package com.ichi.inspection.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderListItem implements Parcelable {

	@SerializedName("Locale")
	@Expose
	private String locale;

	@SerializedName("BookedBy")
	@Expose
	private String bookedBy;

	@SerializedName("Email")
	@Expose
	private String email;

	@SerializedName("Address")
	@Expose
	private String address;

	@SerializedName("UtilOther")
	@Expose
	private boolean utilOther;

	@SerializedName("Spa")
	@Expose
	private boolean spa;

	@SerializedName("EscrowDate")
	@Expose
	private String escrowDate;

	@SerializedName("ListingAgentPhone")
	@Expose
	private String listingAgentPhone;

	@SerializedName("UtilGas")
	@Expose
	private boolean utilGas;

	@SerializedName("BackView")
	@Expose
	private String backView;

	@SerializedName("WhoPlacedOrder")
	@Expose
	private String whoPlacedOrder;

	@SerializedName("DateOrdered")
	@Expose
	private String dateOrdered;

	@SerializedName("EmailLog")
	@Expose
	private String emailLog;

	@SerializedName("FeeCommisionable")
	@Expose
	private int feeCommisionable;

	@SerializedName("BuildingType")
	@Expose
	private String buildingType;

	@SerializedName("UtilPower")
	@Expose
	private boolean utilPower;

	@SerializedName("EscrowOfficer")
	@Expose
	private String escrowOfficer;

	@SerializedName("Zipcode")
	@Expose
	private String zipcode;

	@SerializedName("payment")
	@Expose
	private Payment payment;

	@SerializedName("Pool")
	@Expose
	private boolean pool;

	@SerializedName("BuyerName")
	@Expose
	private String buyerName;

	@SerializedName("AgentName")
	@Expose
	private String agentName;

	@SerializedName("AgentId")
	@Expose
	private int agentId;

	@SerializedName("BuyerAgentPhone")
	@Expose
	private String buyerAgentPhone;

	@SerializedName("InspectionAgreement")
	@Expose
	private boolean inspectionAgreement;

	@SerializedName("EscrowPhones")
	@Expose
	private String escrowPhones;

	@SerializedName("YearBuilt")
	@Expose
	private int yearBuilt;

	@SerializedName("Status")
	@Expose
	private String status;

	@SerializedName("Assigned")
	@Expose
	private boolean assigned;

	@SerializedName("EscrowCompany")
	@Expose
	private String escrowCompany;

	@SerializedName("Occupied")
	@Expose
	private String occupied;

	@SerializedName("BuyerAgentEmail")
	@Expose
	private String buyerAgentEmail;

	@SerializedName("BedRooms")
	@Expose
	private int bedRooms;

	@SerializedName("IONum")
	@Expose
	private int iONum;

	@SerializedName("ListingAgentName")
	@Expose
	private String listingAgentName;

	@SerializedName("Sequence")
	@Expose
	private int sequence;

	@SerializedName("City")
	@Expose
	private String city;

	@SerializedName("TimeStamp")
	@Expose
	private String timeStamp;

	@SerializedName("PropertyState")
	@Expose
	private String propertyState;

	@SerializedName("PropertyAccess")
	@Expose
	private String propertyAccess;

	@SerializedName("CrossStreets")
	@Expose
	private String crossStreets;

	@SerializedName("InspectorId")
	@Expose
	private int inspectorId;

	@SerializedName("SMSSent")
	@Expose
	private String sMSSent;

	@SerializedName("PropertyAddress")
	@Expose
	private String propertyAddress;

	@SerializedName("PayInFull")
	@Expose
	private boolean payInFull;

	@SerializedName("ListingAgentAgencyName")
	@Expose
	private String listingAgentAgencyName;

	@SerializedName("State")
	@Expose
	private String state;

	@SerializedName("EveningPhone")
	@Expose
	private String eveningPhone;

	@SerializedName("TimeData")
	@Expose
	private String timeData;

	@SerializedName("PropertyZip")
	@Expose
	private String propertyZip;

	@SerializedName("FeeGrossPay")
	@Expose
	private double feeGrossPay;

	@SerializedName("LastName")
	@Expose
	private String lastName;

	@SerializedName("PropertyCity")
	@Expose
	private String propertyCity;

	@SerializedName("PropertyCode")
	@Expose
	private String propertyCode;

	@SerializedName("SpecialInstruction")
	@Expose
	private String specialInstruction;

	@SerializedName("ImageMerged")
	@Expose
	private boolean imageMerged;

	@SerializedName("BuyerEmail")
	@Expose
	private String buyerEmail;

	@SerializedName("NumberOfSlots")
	@Expose
	private int numberOfSlots;

	@SerializedName("BuyerAgentAgencyName")
	@Expose
	private String buyerAgentAgencyName;

	@SerializedName("InspectorName")
	@Expose
	private String inspectorName;

	@SerializedName("OrigInspection")
	@Expose
	private int origInspection;

	@SerializedName("TimeEstimate")
	@Expose
	private int timeEstimate;

	@SerializedName("Phone")
	@Expose
	private String phone;

	@SerializedName("WhoTookOrder")
	@Expose
	private String whoTookOrder;

	@SerializedName("ListingAgentEmail")
	@Expose
	private String listingAgentEmail;

	@SerializedName("Email2")
	@Expose
	private String email2;

	@SerializedName("FeeCharged")
	@Expose
	private int feeCharged;

	@SerializedName("BathRooms")
	@Expose
	private int bathRooms;

	@SerializedName("BuyerAgentName")
	@Expose
	private String buyerAgentName;

	@SerializedName("NearCrossStreet")
	@Expose
	private String nearCrossStreet;

	@SerializedName("FrontView")
	@Expose
	private String frontView;

	@SerializedName("AgentAttending")
	@Expose
	private boolean agentAttending;

	@SerializedName("AgencyName")
	@Expose
	private String agencyName;

	@SerializedName("SpecialRequest")
	@Expose
	private boolean specialRequest;

	@SerializedName("DeliverTo")
	@Expose
	private String deliverTo;

	@SerializedName("ListingAgentId")
	@Expose
	private int listingAgentId;

	@SerializedName("CompanyId")
	@Expose
	private int companyId;

	@SerializedName("FirstName")
	@Expose
	private String firstName;

	@SerializedName("UtilWater")
	@Expose
	private boolean utilWater;

	@SerializedName("Charge")
	@Expose
	private int charge;

	@SerializedName("Fees")
	@Expose
	private String fees;

	@SerializedName("EscrowNumber")
	@Expose
	private String escrowNumber;

	@SerializedName("SqFoot")
	@Expose
	private int sqFoot;

	@SerializedName("EscrowBillSent")
	@Expose
	private boolean escrowBillSent;

	@SerializedName("BuyerAttending")
	@Expose
	private boolean buyerAttending;

	@SerializedName("AgentPhone")
	@Expose
	private String agentPhone;

	@SerializedName("ReInspection")
	@Expose
	private boolean reInspection;

	@SerializedName("ReferredBy")
	@Expose
	private String referredBy;

	@SerializedName("HonSubmitted")
	@Expose
	private int honSubmitted;

	public void setLocale(String locale){
		this.locale = locale;
	}

	public String getLocale(){
		return locale;
	}

	public void setBookedBy(String bookedBy){
		this.bookedBy = bookedBy;
	}

	public String getBookedBy(){
		return bookedBy;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setUtilOther(boolean utilOther){
		this.utilOther = utilOther;
	}

	public boolean isUtilOther(){
		return utilOther;
	}

	public void setSpa(boolean spa){
		this.spa = spa;
	}

	public boolean isSpa(){
		return spa;
	}

	public void setEscrowDate(String escrowDate){
		this.escrowDate = escrowDate;
	}

	public String getEscrowDate(){
		return escrowDate;
	}

	public void setListingAgentPhone(String listingAgentPhone){
		this.listingAgentPhone = listingAgentPhone;
	}

	public String getListingAgentPhone(){
		return listingAgentPhone;
	}

	public void setUtilGas(boolean utilGas){
		this.utilGas = utilGas;
	}

	public boolean isUtilGas(){
		return utilGas;
	}

	public void setBackView(String backView){
		this.backView = backView;
	}

	public String getBackView(){
		return backView;
	}

	public void setWhoPlacedOrder(String whoPlacedOrder){
		this.whoPlacedOrder = whoPlacedOrder;
	}

	public String getWhoPlacedOrder(){
		return whoPlacedOrder;
	}

	public void setDateOrdered(String dateOrdered){
		this.dateOrdered = dateOrdered;
	}

	public String getDateOrdered(){
		return dateOrdered;
	}

	public void setEmailLog(String emailLog){
		this.emailLog = emailLog;
	}

	public String getEmailLog(){
		return emailLog;
	}

	public void setFeeCommisionable(int feeCommisionable){
		this.feeCommisionable = feeCommisionable;
	}

	public int getFeeCommisionable(){
		return feeCommisionable;
	}

	public void setBuildingType(String buildingType){
		this.buildingType = buildingType;
	}

	public String getBuildingType(){
		return buildingType;
	}

	public void setUtilPower(boolean utilPower){
		this.utilPower = utilPower;
	}

	public boolean isUtilPower(){
		return utilPower;
	}

	public void setEscrowOfficer(String escrowOfficer){
		this.escrowOfficer = escrowOfficer;
	}

	public String getEscrowOfficer(){
		return escrowOfficer;
	}

	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}

	public String getZipcode(){
		return zipcode;
	}

	public void setPayment(Payment payment){
		this.payment = payment;
	}

	public Payment getPayment(){
		return payment;
	}

	public void setPool(boolean pool){
		this.pool = pool;
	}

	public boolean isPool(){
		return pool;
	}

	public void setBuyerName(String buyerName){
		this.buyerName = buyerName;
	}

	public String getBuyerName(){
		return buyerName;
	}

	public void setAgentName(String agentName){
		this.agentName = agentName;
	}

	public String getAgentName(){
		return agentName;
	}

	public void setAgentId(int agentId){
		this.agentId = agentId;
	}

	public int getAgentId(){
		return agentId;
	}

	public void setBuyerAgentPhone(String buyerAgentPhone){
		this.buyerAgentPhone = buyerAgentPhone;
	}

	public String getBuyerAgentPhone(){
		return buyerAgentPhone;
	}

	public void setInspectionAgreement(boolean inspectionAgreement){
		this.inspectionAgreement = inspectionAgreement;
	}

	public boolean isInspectionAgreement(){
		return inspectionAgreement;
	}

	public void setEscrowPhones(String escrowPhones){
		this.escrowPhones = escrowPhones;
	}

	public String getEscrowPhones(){
		return escrowPhones;
	}

	public void setYearBuilt(int yearBuilt){
		this.yearBuilt = yearBuilt;
	}

	public int getYearBuilt(){
		return yearBuilt;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setAssigned(boolean assigned){
		this.assigned = assigned;
	}

	public boolean isAssigned(){
		return assigned;
	}

	public void setEscrowCompany(String escrowCompany){
		this.escrowCompany = escrowCompany;
	}

	public String getEscrowCompany(){
		return escrowCompany;
	}

	public void setOccupied(String occupied){
		this.occupied = occupied;
	}

	public String getOccupied(){
		return occupied;
	}

	public void setBuyerAgentEmail(String buyerAgentEmail){
		this.buyerAgentEmail = buyerAgentEmail;
	}

	public String getBuyerAgentEmail(){
		return buyerAgentEmail;
	}

	public void setBedRooms(int bedRooms){
		this.bedRooms = bedRooms;
	}

	public int getBedRooms(){
		return bedRooms;
	}

	public void setIONum(int iONum){
		this.iONum = iONum;
	}

	public int getIONum(){
		return iONum;
	}

	public void setListingAgentName(String listingAgentName){
		this.listingAgentName = listingAgentName;
	}

	public String getListingAgentName(){
		return listingAgentName;
	}

	public void setSequence(int sequence){
		this.sequence = sequence;
	}

	public int getSequence(){
		return sequence;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setTimeStamp(String timeStamp){
		this.timeStamp = timeStamp;
	}

	public String getTimeStamp(){
		return timeStamp;
	}

	public void setPropertyState(String propertyState){
		this.propertyState = propertyState;
	}

	public String getPropertyState(){
		return propertyState;
	}

	public void setPropertyAccess(String propertyAccess){
		this.propertyAccess = propertyAccess;
	}

	public String getPropertyAccess(){
		return propertyAccess;
	}

	public void setCrossStreets(String crossStreets){
		this.crossStreets = crossStreets;
	}

	public String getCrossStreets(){
		return crossStreets;
	}

	public void setInspectorId(int inspectorId){
		this.inspectorId = inspectorId;
	}

	public int getInspectorId(){
		return inspectorId;
	}

	public void setSMSSent(String sMSSent){
		this.sMSSent = sMSSent;
	}

	public String getSMSSent(){
		return sMSSent;
	}

	public void setPropertyAddress(String propertyAddress){
		this.propertyAddress = propertyAddress;
	}

	public String getPropertyAddress(){
		return propertyAddress;
	}

	public void setPayInFull(boolean payInFull){
		this.payInFull = payInFull;
	}

	public boolean isPayInFull(){
		return payInFull;
	}

	public void setListingAgentAgencyName(String listingAgentAgencyName){
		this.listingAgentAgencyName = listingAgentAgencyName;
	}

	public String getListingAgentAgencyName(){
		return listingAgentAgencyName;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setEveningPhone(String eveningPhone){
		this.eveningPhone = eveningPhone;
	}

	public String getEveningPhone(){
		return eveningPhone;
	}

	public void setTimeData(String timeData){
		this.timeData = timeData;
	}

	public String getTimeData(){
		return timeData;
	}

	public void setPropertyZip(String propertyZip){
		this.propertyZip = propertyZip;
	}

	public String getPropertyZip(){
		return propertyZip;
	}

	public void setFeeGrossPay(double feeGrossPay){
		this.feeGrossPay = feeGrossPay;
	}

	public double getFeeGrossPay(){
		return feeGrossPay;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setPropertyCity(String propertyCity){
		this.propertyCity = propertyCity;
	}

	public String getPropertyCity(){
		return propertyCity;
	}

	public void setPropertyCode(String propertyCode){
		this.propertyCode = propertyCode;
	}

	public String getPropertyCode(){
		return propertyCode;
	}

	public void setSpecialInstruction(String specialInstruction){
		this.specialInstruction = specialInstruction;
	}

	public String getSpecialInstruction(){
		return specialInstruction;
	}

	public void setImageMerged(boolean imageMerged){
		this.imageMerged = imageMerged;
	}

	public boolean isImageMerged(){
		return imageMerged;
	}

	public void setBuyerEmail(String buyerEmail){
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerEmail(){
		return buyerEmail;
	}

	public void setNumberOfSlots(int numberOfSlots){
		this.numberOfSlots = numberOfSlots;
	}

	public int getNumberOfSlots(){
		return numberOfSlots;
	}

	public void setBuyerAgentAgencyName(String buyerAgentAgencyName){
		this.buyerAgentAgencyName = buyerAgentAgencyName;
	}

	public String getBuyerAgentAgencyName(){
		return buyerAgentAgencyName;
	}

	public void setInspectorName(String inspectorName){
		this.inspectorName = inspectorName;
	}

	public String getInspectorName(){
		return inspectorName;
	}

	public void setOrigInspection(int origInspection){
		this.origInspection = origInspection;
	}

	public int getOrigInspection(){
		return origInspection;
	}

	public void setTimeEstimate(int timeEstimate){
		this.timeEstimate = timeEstimate;
	}

	public int getTimeEstimate(){
		return timeEstimate;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setWhoTookOrder(String whoTookOrder){
		this.whoTookOrder = whoTookOrder;
	}

	public String getWhoTookOrder(){
		return whoTookOrder;
	}

	public void setListingAgentEmail(String listingAgentEmail){
		this.listingAgentEmail = listingAgentEmail;
	}

	public String getListingAgentEmail(){
		return listingAgentEmail;
	}

	public void setEmail2(String email2){
		this.email2 = email2;
	}

	public String getEmail2(){
		return email2;
	}

	public void setFeeCharged(int feeCharged){
		this.feeCharged = feeCharged;
	}

	public int getFeeCharged(){
		return feeCharged;
	}

	public void setBathRooms(int bathRooms){
		this.bathRooms = bathRooms;
	}

	public int getBathRooms(){
		return bathRooms;
	}

	public void setBuyerAgentName(String buyerAgentName){
		this.buyerAgentName = buyerAgentName;
	}

	public String getBuyerAgentName(){
		return buyerAgentName;
	}

	public void setNearCrossStreet(String nearCrossStreet){
		this.nearCrossStreet = nearCrossStreet;
	}

	public String getNearCrossStreet(){
		return nearCrossStreet;
	}

	public void setFrontView(String frontView){
		this.frontView = frontView;
	}

	public String getFrontView(){
		return frontView;
	}

	public void setAgentAttending(boolean agentAttending){
		this.agentAttending = agentAttending;
	}

	public boolean isAgentAttending(){
		return agentAttending;
	}

	public void setAgencyName(String agencyName){
		this.agencyName = agencyName;
	}

	public String getAgencyName(){
		return agencyName;
	}

	public void setSpecialRequest(boolean specialRequest){
		this.specialRequest = specialRequest;
	}

	public boolean isSpecialRequest(){
		return specialRequest;
	}

	public void setDeliverTo(String deliverTo){
		this.deliverTo = deliverTo;
	}

	public String getDeliverTo(){
		return deliverTo;
	}

	public void setListingAgentId(int listingAgentId){
		this.listingAgentId = listingAgentId;
	}

	public int getListingAgentId(){
		return listingAgentId;
	}

	public void setCompanyId(int companyId){
		this.companyId = companyId;
	}

	public int getCompanyId(){
		return companyId;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setUtilWater(boolean utilWater){
		this.utilWater = utilWater;
	}

	public boolean isUtilWater(){
		return utilWater;
	}

	public void setCharge(int charge){
		this.charge = charge;
	}

	public int getCharge(){
		return charge;
	}

	public void setFees(String fees){
		this.fees = fees;
	}

	public String getFees(){
		return fees;
	}

	public void setEscrowNumber(String escrowNumber){
		this.escrowNumber = escrowNumber;
	}

	public String getEscrowNumber(){
		return escrowNumber;
	}

	public void setSqFoot(int sqFoot){
		this.sqFoot = sqFoot;
	}

	public int getSqFoot(){
		return sqFoot;
	}

	public void setEscrowBillSent(boolean escrowBillSent){
		this.escrowBillSent = escrowBillSent;
	}

	public boolean isEscrowBillSent(){
		return escrowBillSent;
	}

	public void setBuyerAttending(boolean buyerAttending){
		this.buyerAttending = buyerAttending;
	}

	public boolean isBuyerAttending(){
		return buyerAttending;
	}

	public void setAgentPhone(String agentPhone){
		this.agentPhone = agentPhone;
	}

	public String getAgentPhone(){
		return agentPhone;
	}

	public void setReInspection(boolean reInspection){
		this.reInspection = reInspection;
	}

	public boolean isReInspection(){
		return reInspection;
	}

	public void setReferredBy(String referredBy){
		this.referredBy = referredBy;
	}

	public String getReferredBy(){
		return referredBy;
	}

	public void setHonSubmitted(int honSubmitted){
		this.honSubmitted = honSubmitted;
	}

	public int getHonSubmitted(){
		return honSubmitted;
	}

	@Override
	public String toString(){
		return
				"OrderListItem{" +
						"locale = '" + locale + '\'' +
						",bookedBy = '" + bookedBy + '\'' +
						",email = '" + email + '\'' +
						",address = '" + address + '\'' +
						",utilOther = '" + utilOther + '\'' +
						",spa = '" + spa + '\'' +
						",escrowDate = '" + escrowDate + '\'' +
						",listingAgentPhone = '" + listingAgentPhone + '\'' +
						",utilGas = '" + utilGas + '\'' +
						",backView = '" + backView + '\'' +
						",whoPlacedOrder = '" + whoPlacedOrder + '\'' +
						",dateOrdered = '" + dateOrdered + '\'' +
						",emailLog = '" + emailLog + '\'' +
						",feeCommisionable = '" + feeCommisionable + '\'' +
						",buildingType = '" + buildingType + '\'' +
						",utilPower = '" + utilPower + '\'' +
						",escrowOfficer = '" + escrowOfficer + '\'' +
						",zipcode = '" + zipcode + '\'' +
						",payment = '" + payment + '\'' +
						",pool = '" + pool + '\'' +
						",buyerName = '" + buyerName + '\'' +
						",agentName = '" + agentName + '\'' +
						",agentId = '" + agentId + '\'' +
						",buyerAgentPhone = '" + buyerAgentPhone + '\'' +
						",inspectionAgreement = '" + inspectionAgreement + '\'' +
						",escrowPhones = '" + escrowPhones + '\'' +
						",yearBuilt = '" + yearBuilt + '\'' +
						",status = '" + status + '\'' +
						",assigned = '" + assigned + '\'' +
						",escrowCompany = '" + escrowCompany + '\'' +
						",occupied = '" + occupied + '\'' +
						",buyerAgentEmail = '" + buyerAgentEmail + '\'' +
						",bedRooms = '" + bedRooms + '\'' +
						",iONum = '" + iONum + '\'' +
						",listingAgentName = '" + listingAgentName + '\'' +
						",sequence = '" + sequence + '\'' +
						",city = '" + city + '\'' +
						",timeStamp = '" + timeStamp + '\'' +
						",propertyState = '" + propertyState + '\'' +
						",propertyAccess = '" + propertyAccess + '\'' +
						",crossStreets = '" + crossStreets + '\'' +
						",inspectorId = '" + inspectorId + '\'' +
						",sMSSent = '" + sMSSent + '\'' +
						",propertyAddress = '" + propertyAddress + '\'' +
						",payInFull = '" + payInFull + '\'' +
						",listingAgentAgencyName = '" + listingAgentAgencyName + '\'' +
						",state = '" + state + '\'' +
						",eveningPhone = '" + eveningPhone + '\'' +
						",timeData = '" + timeData + '\'' +
						",propertyZip = '" + propertyZip + '\'' +
						",feeGrossPay = '" + feeGrossPay + '\'' +
						",lastName = '" + lastName + '\'' +
						",propertyCity = '" + propertyCity + '\'' +
						",propertyCode = '" + propertyCode + '\'' +
						",specialInstruction = '" + specialInstruction + '\'' +
						",imageMerged = '" + imageMerged + '\'' +
						",buyerEmail = '" + buyerEmail + '\'' +
						",numberOfSlots = '" + numberOfSlots + '\'' +
						",buyerAgentAgencyName = '" + buyerAgentAgencyName + '\'' +
						",inspectorName = '" + inspectorName + '\'' +
						",origInspection = '" + origInspection + '\'' +
						",timeEstimate = '" + timeEstimate + '\'' +
						",phone = '" + phone + '\'' +
						",whoTookOrder = '" + whoTookOrder + '\'' +
						",listingAgentEmail = '" + listingAgentEmail + '\'' +
						",email2 = '" + email2 + '\'' +
						",feeCharged = '" + feeCharged + '\'' +
						",bathRooms = '" + bathRooms + '\'' +
						",buyerAgentName = '" + buyerAgentName + '\'' +
						",nearCrossStreet = '" + nearCrossStreet + '\'' +
						",frontView = '" + frontView + '\'' +
						",agentAttending = '" + agentAttending + '\'' +
						",agencyName = '" + agencyName + '\'' +
						",specialRequest = '" + specialRequest + '\'' +
						",deliverTo = '" + deliverTo + '\'' +
						",listingAgentId = '" + listingAgentId + '\'' +
						",companyId = '" + companyId + '\'' +
						",firstName = '" + firstName + '\'' +
						",utilWater = '" + utilWater + '\'' +
						",charge = '" + charge + '\'' +
						",fees = '" + fees + '\'' +
						",escrowNumber = '" + escrowNumber + '\'' +
						",sqFoot = '" + sqFoot + '\'' +
						",escrowBillSent = '" + escrowBillSent + '\'' +
						",buyerAttending = '" + buyerAttending + '\'' +
						",agentPhone = '" + agentPhone + '\'' +
						",reInspection = '" + reInspection + '\'' +
						",referredBy = '" + referredBy + '\'' +
						",honSubmitted = '" + honSubmitted + '\'' +
						"}";
	}

	protected OrderListItem(Parcel in) {
		locale = in.readString();
		bookedBy = in.readString();
		email = in.readString();
		address = in.readString();
		utilOther = in.readByte() != 0x00;
		spa = in.readByte() != 0x00;
		escrowDate = in.readString();
		listingAgentPhone = in.readString();
		utilGas = in.readByte() != 0x00;
		backView = in.readString();
		whoPlacedOrder = in.readString();
		dateOrdered = in.readString();
		emailLog = in.readString();
		feeCommisionable = in.readInt();
		buildingType = in.readString();
		utilPower = in.readByte() != 0x00;
		escrowOfficer = in.readString();
		zipcode = in.readString();
		payment = (Payment) in.readValue(Payment.class.getClassLoader());
		pool = in.readByte() != 0x00;
		buyerName = in.readString();
		agentName = in.readString();
		agentId = in.readInt();
		buyerAgentPhone = in.readString();
		inspectionAgreement = in.readByte() != 0x00;
		escrowPhones = in.readString();
		yearBuilt = in.readInt();
		status = in.readString();
		assigned = in.readByte() != 0x00;
		escrowCompany = in.readString();
		occupied = in.readString();
		buyerAgentEmail = in.readString();
		bedRooms = in.readInt();
		iONum = in.readInt();
		listingAgentName = in.readString();
		sequence = in.readInt();
		city = in.readString();
		timeStamp = in.readString();
		propertyState = in.readString();
		propertyAccess = in.readString();
		crossStreets = in.readString();
		inspectorId = in.readInt();
		sMSSent = in.readString();
		propertyAddress = in.readString();
		payInFull = in.readByte() != 0x00;
		listingAgentAgencyName = in.readString();
		state = in.readString();
		eveningPhone = in.readString();
		timeData = in.readString();
		propertyZip = in.readString();
		feeGrossPay = in.readDouble();
		lastName = in.readString();
		propertyCity = in.readString();
		propertyCode = in.readString();
		specialInstruction = in.readString();
		imageMerged = in.readByte() != 0x00;
		buyerEmail = in.readString();
		numberOfSlots = in.readInt();
		buyerAgentAgencyName = in.readString();
		inspectorName = in.readString();
		origInspection = in.readInt();
		timeEstimate = in.readInt();
		phone = in.readString();
		whoTookOrder = in.readString();
		listingAgentEmail = in.readString();
		email2 = in.readString();
		feeCharged = in.readInt();
		bathRooms = in.readInt();
		buyerAgentName = in.readString();
		nearCrossStreet = in.readString();
		frontView = in.readString();
		agentAttending = in.readByte() != 0x00;
		agencyName = in.readString();
		specialRequest = in.readByte() != 0x00;
		deliverTo = in.readString();
		listingAgentId = in.readInt();
		companyId = in.readInt();
		firstName = in.readString();
		utilWater = in.readByte() != 0x00;
		charge = in.readInt();
		fees = in.readString();
		escrowNumber = in.readString();
		sqFoot = in.readInt();
		escrowBillSent = in.readByte() != 0x00;
		buyerAttending = in.readByte() != 0x00;
		agentPhone = in.readString();
		reInspection = in.readByte() != 0x00;
		referredBy = in.readString();
		honSubmitted = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(locale);
		dest.writeString(bookedBy);
		dest.writeString(email);
		dest.writeString(address);
		dest.writeByte((byte) (utilOther ? 0x01 : 0x00));
		dest.writeByte((byte) (spa ? 0x01 : 0x00));
		dest.writeString(escrowDate);
		dest.writeString(listingAgentPhone);
		dest.writeByte((byte) (utilGas ? 0x01 : 0x00));
		dest.writeString(backView);
		dest.writeString(whoPlacedOrder);
		dest.writeString(dateOrdered);
		dest.writeString(emailLog);
		dest.writeInt(feeCommisionable);
		dest.writeString(buildingType);
		dest.writeByte((byte) (utilPower ? 0x01 : 0x00));
		dest.writeString(escrowOfficer);
		dest.writeString(zipcode);
		dest.writeValue(payment);
		dest.writeByte((byte) (pool ? 0x01 : 0x00));
		dest.writeString(buyerName);
		dest.writeString(agentName);
		dest.writeInt(agentId);
		dest.writeString(buyerAgentPhone);
		dest.writeByte((byte) (inspectionAgreement ? 0x01 : 0x00));
		dest.writeString(escrowPhones);
		dest.writeInt(yearBuilt);
		dest.writeString(status);
		dest.writeByte((byte) (assigned ? 0x01 : 0x00));
		dest.writeString(escrowCompany);
		dest.writeString(occupied);
		dest.writeString(buyerAgentEmail);
		dest.writeInt(bedRooms);
		dest.writeInt(iONum);
		dest.writeString(listingAgentName);
		dest.writeInt(sequence);
		dest.writeString(city);
		dest.writeString(timeStamp);
		dest.writeString(propertyState);
		dest.writeString(propertyAccess);
		dest.writeString(crossStreets);
		dest.writeInt(inspectorId);
		dest.writeString(sMSSent);
		dest.writeString(propertyAddress);
		dest.writeByte((byte) (payInFull ? 0x01 : 0x00));
		dest.writeString(listingAgentAgencyName);
		dest.writeString(state);
		dest.writeString(eveningPhone);
		dest.writeString(timeData);
		dest.writeString(propertyZip);
		dest.writeDouble(feeGrossPay);
		dest.writeString(lastName);
		dest.writeString(propertyCity);
		dest.writeString(propertyCode);
		dest.writeString(specialInstruction);
		dest.writeByte((byte) (imageMerged ? 0x01 : 0x00));
		dest.writeString(buyerEmail);
		dest.writeInt(numberOfSlots);
		dest.writeString(buyerAgentAgencyName);
		dest.writeString(inspectorName);
		dest.writeInt(origInspection);
		dest.writeInt(timeEstimate);
		dest.writeString(phone);
		dest.writeString(whoTookOrder);
		dest.writeString(listingAgentEmail);
		dest.writeString(email2);
		dest.writeInt(feeCharged);
		dest.writeInt(bathRooms);
		dest.writeString(buyerAgentName);
		dest.writeString(nearCrossStreet);
		dest.writeString(frontView);
		dest.writeByte((byte) (agentAttending ? 0x01 : 0x00));
		dest.writeString(agencyName);
		dest.writeByte((byte) (specialRequest ? 0x01 : 0x00));
		dest.writeString(deliverTo);
		dest.writeInt(listingAgentId);
		dest.writeInt(companyId);
		dest.writeString(firstName);
		dest.writeByte((byte) (utilWater ? 0x01 : 0x00));
		dest.writeInt(charge);
		dest.writeString(fees);
		dest.writeString(escrowNumber);
		dest.writeInt(sqFoot);
		dest.writeByte((byte) (escrowBillSent ? 0x01 : 0x00));
		dest.writeByte((byte) (buyerAttending ? 0x01 : 0x00));
		dest.writeString(agentPhone);
		dest.writeByte((byte) (reInspection ? 0x01 : 0x00));
		dest.writeString(referredBy);
		dest.writeInt(honSubmitted);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<OrderListItem> CREATOR = new Parcelable.Creator<OrderListItem>() {
		@Override
		public OrderListItem createFromParcel(Parcel in) {
			return new OrderListItem(in);
		}

		@Override
		public OrderListItem[] newArray(int size) {
			return new OrderListItem[size];
		}
	};
}