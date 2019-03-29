package com.anandniketanbhadaj.skool360student.Models;

import java.util.ArrayList;

public class AttendanceModel {

    private String TotalAbsent;
    private String TotalPresent;
    private String HolidayCount;
    private String TotalHolidayCount;
    private ArrayList<Attendance> eventsList;
    private ArrayList<HolidayAtt> holidayAtt;

    public AttendanceModel() {

    }

    public String getTotalAbsent() {
        return TotalAbsent;
    }

    public String getHolidayCount() {
        return HolidayCount;
    }

    public void setHolidayCount(String holidayCount) {
        HolidayCount = holidayCount;
    }

    public void setTotalAbsent(String totalAbsent) {
        TotalAbsent = totalAbsent;
    }

    public String getTotalPresent() {
        return TotalPresent;
    }

    public void setTotalPresent(String totalPresent) {
        TotalPresent = totalPresent;
    }

    public ArrayList<Attendance> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<Attendance> eventsList) {
        this.eventsList = eventsList;
    }

    public ArrayList<HolidayAtt> getHolidayAtt() {
        return holidayAtt;
    }

    public void setHolidayAtt(ArrayList<HolidayAtt> holidayAtt) {
        this.holidayAtt = holidayAtt;
    }

    public String getTotalHolidayCount() {
        return TotalHolidayCount;
    }

    public void setTotalHolidayCount(String totalHolidayCount) {
        TotalHolidayCount = totalHolidayCount;
    }

    public class Attendance {

        private String AttendanceDate;
        private String Comment;
        private String AttendenceStatus;

        public String getAttendanceDate() {
            return AttendanceDate;
        }

        public void setAttendanceDate(String attendanceDate) {
            AttendanceDate = attendanceDate;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public String getAttendenceStatus() {
            return AttendenceStatus;
        }

        public void setAttendenceStatus(String attendenceStatus) {
            AttendenceStatus = attendenceStatus;
        }

        public Attendance() {
        }
    }

    public class HolidayAtt{
        private String HolidayName;
        private String Date;
        private String Count;

        public String getHolidayName() {
            return HolidayName;
        }

        public void setHolidayName(String holidayName) {
            HolidayName = holidayName;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getCount() {
            return Count;
        }

        public void setCount(String count) {
            Count = count;
        }
    }
}
