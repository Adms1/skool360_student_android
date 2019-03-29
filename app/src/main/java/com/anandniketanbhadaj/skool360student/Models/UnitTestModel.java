package com.anandniketanbhadaj.skool360student.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/4/2017.
 */

public class UnitTestModel {
    private String TestDate;
    private String TestName;
    private ArrayList<Data> dataArrayList;

    public UnitTestModel() {
    }

    public String getTestDate() {
        return TestDate;
    }

    public void setTestDate(String testDate) {
        TestDate = testDate;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

}

