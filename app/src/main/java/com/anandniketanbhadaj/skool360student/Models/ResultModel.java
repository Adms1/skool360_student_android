package com.anandniketanbhadaj.skool360student.Models;

import java.util.ArrayList;

public class ResultModel {

    private String TestName;
    private String total_Marks;
    private String totalMarksGained;
    private String totalPercentage;
    private ArrayList<Data> dataArrayList;

    public ResultModel() {
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getTotal_Marks() {
        return total_Marks;
    }

    public void setTotal_Marks(String total_Marks) {
        this.total_Marks = total_Marks;
    }

    public String getTotalMarksGained() {
        return totalMarksGained;
    }

    public void setTotalMarksGained(String totalMarksGained) {
        this.totalMarksGained = totalMarksGained;
    }

    public String getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(String totalPercentage) {
        this.totalPercentage = totalPercentage;
    }

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    public class Data {

        private String SubjectName;
        private String TestMark;
        private String MarkGained;
        private String Percentage;
        private String Date;

        public Data() {
        }

        public String getSubjectName() {
            return SubjectName;
        }

        public void setSubjectName(String subjectName) {
            SubjectName = subjectName;
        }

        public String getTestMark() {
            return TestMark;
        }

        public void setTestMark(String testMark) {
            TestMark = testMark;
        }

        public String getMarkGained() {
            return MarkGained;
        }

        public void setMarkGained(String markGained) {
            MarkGained = markGained;
        }

        public String getPercentage() {
            return Percentage;
        }

        public void setPercentage(String percentage) {
            Percentage = percentage;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }
    }
}
