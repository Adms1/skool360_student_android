package com.anandniketanbhadaj.skool360student.Models.ExamSyllabus;

import com.anandniketanbhadaj.skool360student.Models.Suggestion.InboxFinalArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateLeaveModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<InboxFinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<InboxFinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<InboxFinalArray> finalArray) {
        this.finalArray = finalArray;
    }

}