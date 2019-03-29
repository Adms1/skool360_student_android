package com.anandniketanbhadaj.skool360student.Models.ExamSyllabus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionDetailModel {
    @SerializedName("Section")
    @Expose
    private String section;
    @SerializedName("SectionID")
    @Expose
    private Integer sectionID;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

}
