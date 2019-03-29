package com.anandniketanbhadaj.skool360student.Models.ExamSyllabus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoModel {
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
