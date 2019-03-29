package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Models.StudProfileModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class GetUserProfileAsyncTask extends AsyncTask<Void, Void, ArrayList<StudProfileModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetUserProfileAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<StudProfileModel> doInBackground(Void... params) {
            String responseString = null;
            ArrayList<StudProfileModel> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetUserProfile), param);
                result = ParseJSON.parseUserProfileJson(responseString);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<StudProfileModel> result) {
            super.onPostExecute(result);
        }
    }