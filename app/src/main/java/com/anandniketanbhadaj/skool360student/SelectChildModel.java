package com.anandniketanbhadaj.skool360student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectChildModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("StudentCount")
    @Expose
    private int studentcount;

    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalarray;

    @SerializedName("FamilyDetail")
    @Expose
    private ArrayList<FamilyDetail> familyDetails;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStudentcount() {
        return studentcount;
    }

    public void setStudentcount(int studentcount) {
        this.studentcount = studentcount;
    }

    public ArrayList<FinalArray> getFinalarray() {
        return finalarray;
    }

    public void setFinalarray(ArrayList<FinalArray> finalarray) {
        this.finalarray = finalarray;
    }

    public ArrayList<FamilyDetail> getFamilyDetails() {
        return familyDetails;
    }

    public void setFamilyDetails(ArrayList<FamilyDetail> familyDetails) {
        this.familyDetails = familyDetails;
    }

    public class FinalArray implements Serializable {

        @SerializedName("StudentID")
        @Expose
        private String studentid;

        @SerializedName("FamilyID")
        @Expose
        private String familyid;

        @SerializedName("StandardID")
        @Expose
        private String standardid;

        @SerializedName("ClassID")
        @Expose
        private String classid;

        @SerializedName("StudentImage")
        @Expose
        private String studentimage;

        @SerializedName("StudentName")
        @Expose
        private String studentname;

        @SerializedName("GradeSection")
        @Expose
        private String gradesection;

        @SerializedName("Term")
        @Expose
        private String term;

        @SerializedName("TermID")
        @Expose
        private String termid;

        @SerializedName("RegisterStatus")
        @Expose
        private String registerstatus;

        @SerializedName("LocationID")
        @Expose
        private String locationid;

        public String getStudentid() {
            return studentid;
        }

        public void setStudentid(String studentid) {
            this.studentid = studentid;
        }

        public String getFamilyid() {
            return familyid;
        }

        public void setFamilyid(String familyid) {
            this.familyid = familyid;
        }

        public String getStandardid() {
            return standardid;
        }

        public void setStandardid(String standardid) {
            this.standardid = standardid;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getStudentimage() {
            return studentimage;
        }

        public void setStudentimage(String studentimage) {
            this.studentimage = studentimage;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }

        public String getGradesection() {
            return gradesection;
        }

        public void setGradesection(String gradesection) {
            this.gradesection = gradesection;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }



        public String getTermid() {
            return termid;
        }

        public void setTermid(String termid) {
            this.termid = termid;
        }

        public String getRegisterstatus() {
            return registerstatus;
        }

        public void setRegisterstatus(String registerstatus) {
            this.registerstatus = registerstatus;
        }

        public String getLocationid() {
            return locationid;
        }

        public void setLocationid(String locationid) {
            this.locationid = locationid;
        }
    }

    public class FamilyDetail implements Serializable{

        @SerializedName("FamilyName")
        @Expose
        private String familyname;

        @SerializedName("ContactNo")
        @Expose
        private String contactno;

        @SerializedName("EmailAddress")
        @Expose
        private String emailaddress;

        public String getFamilyname() {
            return familyname;
        }

        public void setFamilyname(String familyname) {
            this.familyname = familyname;
        }

        public String getContactno() {
            return contactno;
        }

        public void setContactno(String contactno) {
            this.contactno = contactno;
        }

        public String getEmailaddress() {
            return emailaddress;
        }

        public void setEmailaddress(String emailaddress) {
            this.emailaddress = emailaddress;
        }
    }

}
