package com.anandniketanbhadaj.skool360student.Models;

public class CircularModel {

    private String Subject;
    private String Date;
    private String Discription;
    private String CircularPDF;

    public String getCircularPDF() {
        return CircularPDF;
    }

    public void setCircularPDF(String circularPDF) {
        CircularPDF = circularPDF;
    }

    public CircularModel() {
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }
}
