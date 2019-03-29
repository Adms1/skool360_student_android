package com.anandniketanbhadaj.skool360student.Models;

import java.util.ArrayList;

public class HomeWorkModel {

    private String HomeWorkDate;
    private ArrayList<HomeWorkData> homeWorkDatas = new ArrayList<>();

    public String getHomeWorkDate() {
        return HomeWorkDate;
    }

    public void setHomeWorkDate(String homeWorkDate) {
        HomeWorkDate = homeWorkDate;
    }

    public ArrayList<HomeWorkData> getHomeWorkDatas() {
        return homeWorkDatas;
    }

    public void setHomeWorkDatas(ArrayList<HomeWorkData> homeWorkDatas) {
        this.homeWorkDatas = homeWorkDatas;
    }

    public HomeWorkModel() {
    }

    public class HomeWorkData {

        public HomeWorkData() {
        }

        private String Subject;
        private String Homework;
        private String ChapterName;
        private String Objective;
        private String AssessmentQue;
        private String Font;
        private String HomeWorkStatus;
        private Boolean isVisible = false;

        public String getHomework() {
            return Homework;
        }

        public void setHomework(String homework) {
            Homework = homework;
        }


        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getChapterName() {
            return ChapterName;
        }

        public void setChapterName(String chapterName) {
            ChapterName = chapterName;
        }

        public String getObjective() {
            return Objective;
        }

        public void setObjective(String objective) {
            Objective = objective;
        }

        public String getAssessmentQue() {
            return AssessmentQue;
        }

        public void setAssessmentQue(String assessmentQue) {
            AssessmentQue = assessmentQue;
        }

        public String getFont() {
            return Font;
        }

        public void setFont(String font) {
            Font = font;
        }

        public Boolean getVisible() {
            return isVisible;
        }

        public void setVisible(Boolean visible) {
            isVisible = visible;
        }

        public String getHomeWorkStatus() {
            return HomeWorkStatus;
        }

        public void setHomeWorkStatus(String homeWorkStatus) {
            HomeWorkStatus = homeWorkStatus;
        }
    }
}
