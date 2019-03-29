package com.anandniketanbhadaj.skool360student.Models.ExamSyllabus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("FinalArray")
    @Expose
    private List<ExamFinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ExamFinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<ExamFinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
