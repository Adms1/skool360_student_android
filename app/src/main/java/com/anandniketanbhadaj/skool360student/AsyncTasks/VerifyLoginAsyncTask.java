package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.SelectChildModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

public class VerifyLoginAsyncTask extends AsyncTask<Void, Void, SelectChildModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public VerifyLoginAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected SelectChildModel doInBackground(Void... params) {
            String responseString = null;
            SelectChildModel result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.parentLogin), param);
                Gson gson = new Gson();
                result = gson.fromJson(responseString, SelectChildModel.class);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(SelectChildModel result) {
            super.onPostExecute(result);
        }
    }