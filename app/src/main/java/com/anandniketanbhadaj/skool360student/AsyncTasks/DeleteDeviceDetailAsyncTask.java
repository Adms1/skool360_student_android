package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.CreateLeaveModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

public class DeleteDeviceDetailAsyncTask extends AsyncTask<Void, Void,CreateLeaveModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public DeleteDeviceDetailAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected CreateLeaveModel doInBackground(Void... params) {
        String responseString = null;
        CreateLeaveModel response =null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.DeleteDeviceDetail), param);
            Gson gson = new Gson();
            response = gson.fromJson(responseString, CreateLeaveModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(CreateLeaveModel result) {
        super.onPostExecute(result);
    }
}

