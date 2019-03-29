package com.anandniketanbhadaj.skool360student.Models.ExamSyllabus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamFinalArray {
    @SerializedName("TestName")
    @Expose
    private String testName;
    @SerializedName("TestDate")
    @Expose
    private String testDate;
    @SerializedName("Data")
    @Expose
    private List<ExamDatum> data = null;
    @SerializedName("EventName")
    @Expose
    private String eventName;
    @SerializedName("Photos")
    @Expose
    private List<PhotoModel> photos = null;
    //====Leave Data===========
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //    =================


    //================holiday==================
    @SerializedName("MonthName")
    @Expose
    private String monthName;
    @SerializedName("MonthImage")
    @Expose
    private String monthImage;
    @SerializedName("Year")
    @Expose
    private String year;
    public String getMonthName() {
        return monthName;
    }
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonthImage() {
        return monthImage;
    }

    public void setMonthImage(String monthImage) {
        this.monthImage = monthImage;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    //=========================================
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<PhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public List<ExamDatum> getData() {
        return data;
    }

    public void setData(List<ExamDatum> data) {
        this.data = data;
    }


    //================Announcement=======================

    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("AnnoucementDescription")
    @Expose
    private String annoucementDescription;
    @SerializedName("AnnoucementPDF")
    @Expose
    private String annoucementPDF;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAnnoucementDescription() {
        return annoucementDescription;
    }

    public void setAnnoucementDescription(String annoucementDescription) {
        this.annoucementDescription = annoucementDescription;
    }

    public String getAnnoucementPDF() {
        return annoucementPDF;
    }

    public void setAnnoucementPDF(String annoucementPDF) {
        this.annoucementPDF = annoucementPDF;
    }

    //===================================================

    //==================StandardSection============
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StandardID")
    @Expose
    private Integer standardID;
    @SerializedName("SectionDetail")
    @Expose
    private List<SectionDetailModel> sectionDetail = null;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Integer getStandardID() {
        return standardID;
    }

    public void setStandardID(Integer standardID) {
        this.standardID = standardID;
    }

    public List<SectionDetailModel> getSectionDetail() {
        return sectionDetail;
    }

    public void setSectionDetail(List<SectionDetailModel> sectionDetail) {
        this.sectionDetail = sectionDetail;
    }
//===================studentList================

    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("StudentID")
    @Expose
    private String studentID;
    @SerializedName("FamilyID")
    @Expose
    private String familyID;

    @SerializedName("ClassID")
    @Expose
    private String classID;
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("TermID")
    @Expose
    private Integer termID;
    @SerializedName("RegisterStatus")
    @Expose
    private Boolean registerStatus;


    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFamilyID() {
        return familyID;
    }

    public void setFamilyID(String familyID) {
        this.familyID = familyID;
    }
    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getTermID() {
        return termID;
    }

    public void setTermID(Integer termID) {
        this.termID = termID;
    }

    public Boolean getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

}
