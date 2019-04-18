package com.example.root.educateappcontrolvisitas.api.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface formularioTecnicoClient {
    @POST("serviceweb/api/v2/technicalform/?obj_update")
    @FormUrlEncoded
    Call<Void> guardarFormulario(@Query("username") String username,
                                 @Query("api_key") String api_key,
                                 @Query("limit") int limit,
                                 @Field("visit") String visit,
                                 @Field("action_taken") String action_taken,
                                 @Field("observation") String observation,
                                 @Field("apci") String apci,
                                 @Field("electrico") String electrico,
                                 @Field("hardware") String hardware,
                                 @Field("internet") String internet,
                                 @Field("redes") String redes,
                                 @Field("software") String software,
                                 @Field("updated_by") String updated_by,
                                 @Field("created_by") String created_by

    );

    @GET("serviceweb/api/v2/technicalform/?format=json")
    Call<JsonObject> obtenerFormularios(@Query("username") String username,
                                        @Query("api_key") String api_key,
                                        @Query("limit") int limit);
}
