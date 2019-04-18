package com.example.root.educateappcontrolvisitas.api.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataAPCIclient {
    @POST("serviceweb/api/v2/dataapciacademico/?obj_update")
    @FormUrlEncoded
    Call<Void> crearDataAPCI(@Query("username") String username,
                                 @Query("api_key") String api_key,
                                 @Field("no_prof_capacitados") int no_prof_capacitados,
                                 @Field("minutos_uso") int minutos_uso,
                                 @Field("updated_by") String updated_by,
                                 @Field("created_by") String created_by

    );

    @GET("serviceweb/api/v2/dataapciacademico/?format=json")
    Call<JsonObject> obtenerDataAPCI(@Query("username") String username,
                                        @Query("api_key") String api_key,
                                        @Query("limit") int limit);


}
