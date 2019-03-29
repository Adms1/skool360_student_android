package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Models.ReportCardModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admsandroid on 9/4/2017.
 */

public class GetReportcardAsyncTask extends AsyncTask<Void, Void, ArrayList<ReportCardModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetReportcardAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<ReportCardModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<ReportCardModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetReportcard), param);
            result = ParseJSON.parseReportCardJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<ReportCardModel> result) {
        super.onPostExecute(result);
    }
}

