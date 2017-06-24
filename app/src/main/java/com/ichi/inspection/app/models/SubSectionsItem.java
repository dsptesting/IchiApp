package com.ichi.inspection.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubSectionsItem{

    @SerializedName("IsHead")
    private String isHead;

    @SerializedName("Poor")
    private String poor;

    @SerializedName("Fair")
    private String fair;

    @SerializedName("Comments")
    private String comments;

    @SerializedName("InspectionId")
    private String inspectionId;

    @SerializedName("SuppressPrint")
    private String suppressPrint;

    @SerializedName("VeryPoor")
    private String veryPoor;

    @SerializedName("LineNumber")
    private String lineNumber;

    @SerializedName("Name")
    private String name;

    @SerializedName("UsedHead")
    private String usedHead;

    @SerializedName("PageBreak")
    private String pageBreak;

    @SerializedName("IOLineId")
    private String iOLineId;

    @SerializedName("LineOrder")
    private String lineOrder;

    @SerializedName("Good")
    private String good;

    @SerializedName("SectionId")
    private String sectionId;

    @SerializedName("NumberOfExposures")
    private String numberOfExposures;

    @SerializedName("NotInspected")
    private String notInspected;

    private ArrayList<String> imageURIs;

	@SerializedName("TemplateId")
    private String templateId;
	private int contentType;
	private int status;

    public SubSectionsItem() {
        this.imageURIs = new ArrayList<>();
    }

	public SubSectionsItem(String name, int contentType) {
		this.name = name;
		this.contentType = contentType;
		this.imageURIs = new ArrayList<>();
	}

    public ArrayList<String> getImageURIs() {
        return imageURIs;
    }

    public void setImageURIs(ArrayList<String> imageURIs) {
        this.imageURIs = imageURIs;
    }

	@Override
	public String toString() {
		return "SubSectionsItem{" +
				"isHead='" + isHead + '\'' +
				", poor='" + poor + '\'' +
				", fair='" + fair + '\'' +
				", comments='" + comments + '\'' +
				", inspectionId='" + inspectionId + '\'' +
				", suppressPrint='" + suppressPrint + '\'' +
				", veryPoor='" + veryPoor + '\'' +
				", lineNumber='" + lineNumber + '\'' +
				", name='" + name + '\'' +
				", usedHead='" + usedHead + '\'' +
				", pageBreak='" + pageBreak + '\'' +
				", iOLineId='" + iOLineId + '\'' +
				", lineOrder='" + lineOrder + '\'' +
				", good='" + good + '\'' +
				", sectionId='" + sectionId + '\'' +
				", numberOfExposures='" + numberOfExposures + '\'' +
				", notInspected='" + notInspected + '\'' +
				", imageURIs=" + imageURIs +
				", templateId='" + templateId + '\'' +
				", contentType=" + contentType +
				", status=" + status +
				'}';
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public String getTemplatedId() {
        return templateId;
    }

    public void setTemplatedId(String templateId) {
        this.templateId = templateId;
    }

    public void setIsHead(String isHead){
		this.isHead = isHead;
	}

	public String getIsHead(){
		return isHead;
	}

	public void setPoor(String poor){
		this.poor = poor;
	}

	public String getPoor(){
		return poor;
	}

	public void setFair(String fair){
		this.fair = fair;
	}

	public String getFair(){
		return fair;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public String getComments(){
		return comments;
	}

	public void setInspectionId(String inspectionId){
		this.inspectionId = inspectionId;
	}

	public String getInspectionId(){
		return inspectionId;
	}

	public void setSuppressPrint(String suppressPrint){
		this.suppressPrint = suppressPrint;
	}

	public String getSuppressPrint(){
		return suppressPrint;
	}

	public void setVeryPoor(String veryPoor){
		this.veryPoor = veryPoor;
	}

	public String getVeryPoor(){
		return veryPoor;
	}

	public void setLineNumber(String lineNumber){
		this.lineNumber = lineNumber;
	}

	public String getLineNumber(){
		return lineNumber;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUsedHead(String usedHead){
		this.usedHead = usedHead;
	}

	public String getUsedHead(){
		return usedHead;
	}

	public void setPageBreak(String pageBreak){
		this.pageBreak = pageBreak;
	}

	public String getPageBreak(){
		return pageBreak;
	}

	public void setIOLineId(String iOLineId){
		this.iOLineId = iOLineId;
	}

	public String getIOLineId(){
		return iOLineId;
	}

	public void setLineOrder(String lineOrder){
		this.lineOrder = lineOrder;
	}

	public String getLineOrder(){
		return lineOrder;
	}

	public void setGood(String good){
		this.good = good;
	}

	public String getGood(){
		return good;
	}

	public void setSectionId(String sectionId){
		this.sectionId = sectionId;
	}

	public String getSectionId(){
		return sectionId;
	}

	public void setNumberOfExposures(String numberOfExposures){
		this.numberOfExposures = numberOfExposures;
	}

	public String getNumberOfExposures(){
		return numberOfExposures;
	}

	public void setNotInspected(String notInspected){
		this.notInspected = notInspected;
	}

	public String getNotInspected(){
		return notInspected;
	}

}
