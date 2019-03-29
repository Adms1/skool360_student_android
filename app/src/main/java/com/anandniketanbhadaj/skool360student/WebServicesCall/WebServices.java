package com.anandniketanbhadaj.skool360student.WebServicesCall;

import com.anandniketanbhadaj.skool360student.SelectChildModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Url;

/**
 * Created by admsandroid on 11/20/2017.
 */

public interface WebServices {

    @retrofit2.http.GET()
    Call<SelectChildModel> getParentLogin(@Url String url);

    @retrofit2.http.GET()
    Call<JsonObject> getBaseUrl(@Url String url);

}
