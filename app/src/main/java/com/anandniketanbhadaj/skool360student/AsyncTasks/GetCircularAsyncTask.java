package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;


import com.anandniketanbhadaj.skool360student.Models.CircularModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class GetCircularAsyncTask extends AsyncTask<Void, Void, ArrayList<CircularModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetCircularAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<CircularModel> doInBackground(Void... params) {
            String responseString = null;
            ArrayList<CircularModel> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetCircularDetail), param);
                result = ParseJSON.parseCircularJson(responseString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<CircularModel> result) {
            super.onPostExecute(result);
        }
    }