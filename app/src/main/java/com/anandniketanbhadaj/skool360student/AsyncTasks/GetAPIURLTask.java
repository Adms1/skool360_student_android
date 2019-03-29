package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;

import org.json.JSONObject;

import java.util.HashMap;

public class GetAPIURLTask extends AsyncTask<Void, Void, Boolean> {
    HashMap<String, String> param = new HashMap<String, String>();
    private Context context;

    public GetAPIURLTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        String responseString = null;
        boolean success = false;
        try {
            responseString = WebServicesCall.RunScript("http://anandniketanbhadaj.org/appService/5b9a72856992e144c74fc836ed6e76a2/appsUrl",0);
            success = parseJson(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean parseJson(String responseString) {
        try {
            JSONObject reader = new JSONObject(responseString);
            String readerString = reader.getString("succcess");
            String apiUrl  =  reader.getString("appsUrl");

            AppConfiguration.DOMAIN_LIVE = apiUrl +"MobileApp_Service.asmx/";
            AppConfiguration.LIVE_BASE_URL = apiUrl;

            Utility.setPref(context, "live_base_url",AppConfiguration.LIVE_BASE_URL);//result.get("TermID"));

            if (readerString.equalsIgnoreCase("1")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
