package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Models.HomeWorkModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class GetStudHomeworkAsyncTask extends AsyncTask<Void, Void, ArrayList<HomeWorkModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetStudHomeworkAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<HomeWorkModel> doInBackground(Void... params) {
            String responseString = null;
            ArrayList<HomeWorkModel> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetHomework), param);
                result = ParseJSON.parseStudHomeworkJson(responseString);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<HomeWorkModel> result) {
            super.onPostExecute(result);
        }
    }