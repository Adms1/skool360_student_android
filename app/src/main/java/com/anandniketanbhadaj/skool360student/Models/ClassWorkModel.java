package com.anandniketanbhadaj.skool360student.Models;

import java.util.ArrayList;

public class ClassWorkModel {

    private String ClassWorkDate;
    private ArrayList<ClassWorkData> classWorkDatas = new ArrayList<>();

    public String getClassWorkDate() {
        return ClassWorkDate;
    }

    public void setClassWorkDate(String classWorkDate) {
        ClassWorkDate = classWorkDate;
    }

    public ArrayList<ClassWorkData> getClassWorkDatas() {
        return classWorkDatas;
    }

    public void setClassWorkDatas(ArrayList<ClassWorkData> classWorkDatas) {
        this.classWorkDatas = classWorkDatas;
    }

    public ClassWorkModel() {
    }

    public class ClassWorkData {

        private String Subject;
        private String Classwork;
        private String ProxyStatus;

        public ClassWorkData() {
        }

        public String getClasswork() {
            return Classwork;
        }

        public void setClasswork(String classwork) {
            Classwork = classwork;
        }


        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getProxyStatus() {
            return ProxyStatus;
        }

        public void setProxyStatus(String proxyStatus) {
            ProxyStatus = proxyStatus;
        }
    }
}
