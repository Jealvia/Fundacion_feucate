package com.example.root.educateappcontrolvisitas.api.service;

import com.example.root.educateappcontrolvisitas.api.model.PostRequirement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Requerimiento {
    //https://feducate.herokuapp.com/serviceweb/api/v2/requirement/set/1/
    //@Query("username") String username,
    //                                 @Query("api_key") String api_key,
    @POST("serviceweb/api/v2/requirement/")
    Call<PostRequirement> createRequirement(@Body PostRequirement postRequirement);
    //https://feducate.herokuapp.com/serviceweb/api/v2/requirement/
    @GET("serviceweb/api/v2/requirement/")
    Call<JsonObject> obtenerRequerimiento(@Query("username") String username,
                                     @Query("api_key") String api_key,
                                     @Query("limit") int limit);

}
