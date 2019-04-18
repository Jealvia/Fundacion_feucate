package com.example.root.educateappcontrolvisitas.api.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface escuelasClient {

    @GET("serviceweb/api/v2/school/?format=json")
    Call<JsonObject> obtenerEscuelas(@Query("username") String username,
                                     @Query("api_key") String api_key,
                                     @Query("limit") int limit,
                                     @Query("sector_name") String sector);


}
