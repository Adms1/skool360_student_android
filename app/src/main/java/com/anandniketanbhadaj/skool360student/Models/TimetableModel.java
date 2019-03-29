package com.anandniketanbhadaj.skool360student.Models;

import java.util.ArrayList;

public class TimetableModel {

    private ArrayList<Timetable> timetables;

    public ArrayList<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(ArrayList<Timetable> timetables) {
        this.timetables = timetables;
    }

    public TimetableModel() {

    }

    public class Timetable {

        private String Day;
        private ArrayList<TimetableData> timetableDatas;

        public String getDay() {
            return Day;
        }

        public void setDay(String day) {
            Day = day;
        }

        public ArrayList<TimetableData> getTimetableDatas() {
            return timetableDatas;
        }

        public void setTimetableDatas(ArrayList<TimetableData> timetableDatas) {
            this.timetableDatas = timetableDatas;
        }

        public Timetable() {
        }

        public class TimetableData{
            private String Lecture;
            private String Subject;
            private String Teacher;

            public TimetableData() {
            }

            public String getLecture() {
                return Lecture;
            }

            public void setLecture(String lecture) {
                Lecture = lecture;
            }

            public String getSubject() {
                return Subject;
            }

            public void setSubject(String subject) {
                Subject = subject;
            }

            public String getTeacher() {
                return Teacher;
            }

            public void setTeacher(String teacher) {
                Teacher = teacher;
            }
        }
    }
}
