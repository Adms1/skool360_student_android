package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Models.ResultModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class GetStudentResultAsyncTask extends AsyncTask<Void, Void, ArrayList<ResultModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetStudentResultAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<ResultModel> doInBackground(Void... params) {
            String responseString = null;
            ArrayList<ResultModel> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetStudentResult), param);
                result = ParseJSON.parseUnitTestJson(responseString);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<ResultModel> result) {
            super.onPostExecute(result);
        }
    }