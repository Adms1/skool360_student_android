package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Models.AttendanceModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class GetAttendanceAsyncTask extends AsyncTask<Void, Void, ArrayList<AttendanceModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetAttendanceAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<AttendanceModel> doInBackground(Void... params) {
            String responseString = null;
            ArrayList<AttendanceModel> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetAttendence), param);
                result = ParseJSON.parseAttendanceJson(responseString);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<AttendanceModel> result) {
            super.onPostExecute(result);
        }
    }