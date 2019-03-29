package com.anandniketanbhadaj.skool360student.Models;

/*
 * Created by admsandroid on 1/3/2018.
 */

public class Data {

    private String Subject;
    private String Detail;
    private Boolean isVisible = false;

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Data() {
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }


}
