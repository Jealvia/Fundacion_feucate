package com.example.root.educateappcontrolvisitas.api.service;


import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.google.gson.JsonObject;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface VisitasClient {

    @GET("serviceweb/api/v2/visit/?format=json")
    Call<JsonObject> obtenerVisitas(@Query("username") String username,
                                    @Query("api_key") String api_key,
                                    @Query("limit") int limit);

    @POST("serviceweb/api/v2/visit/?obj_update")
    @FormUrlEncoded
    Call<Void> checkIn(@Query("username") String username,
                       @Query("api_key") String api_key,
                       @Query("limit") int limit,
                        @Field("user") String user,
                        @Field("requirement") String requirement,
                        @Field("date_planned") String date_planned,
                        @Field("check_in") String check_in,
                       @Field("coordinates_lat_in") double coordinates_lat_in,
                       @Field("coordinates_lon_in") double coordinates_lon_in,
                       @Field("state") int state,
                       @Field("type") int type,
                       @Field("id") int id

                       );

    @POST("serviceweb/api/v2/visit/?obj_update")
    @FormUrlEncoded
    Call<Void> checkOut(@Query("username") String username,
                       @Query("api_key") String api_key,
                       @Query("limit") int limit,
                       @Field("user") String user,
                       @Field("requirement") String requirement,
                       @Field("date_planned") String date_planned,
                       @Field("check_out") String check_out,
                       @Field("coordinates_lat_out") double coordinates_lat_out,
                       @Field("coordinates_lon_out") double coordinates_lon_out,
                       @Field("state") int state,
                       @Field("type") int type,
                        @Field("check_in") String check_in,
                        @Field("coordinates_lat_in") double coordinates_lat_in,
                        @Field("coordinates_lon_in") double coordinates_lon_in,
                       @Field("id") int id,
                        @Field("created_by") String created_by
                        );
}

