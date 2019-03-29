package com.anandniketanbhadaj.skool360student.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360student.Models.FeesModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by admsandroid on 9/5/2017.
 */

public class FeesAsyncTask extends AsyncTask<Void, Void,FeesModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public FeesAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected FeesModel doInBackground(Void... params) {
        String responseString = null;
        FeesModel result=null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetFeesStatus),param);


            try {
                JSONObject jsonObject = new JSONObject(responseString);

                String success = jsonObject.getString("Success");

            }catch (Exception ex){
                ex.printStackTrace();
            }

            Gson gson = new Gson();
            result = gson.fromJson(responseString,FeesModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(FeesModel result) {
        super.onPostExecute(result);
    }


}

