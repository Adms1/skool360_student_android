package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;

import java.util.HashMap;

public class GetFeesStatusTask extends AsyncTask<Void, Void, String> {
    HashMap<String, String> param = new HashMap<String, String>();
    private Context context;

    public GetFeesStatusTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseString = null;

        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetFeesStatus),param);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
