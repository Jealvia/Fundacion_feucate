package com.example.root.educateappcontrolvisitas.api.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IncidenciaClient {
    @POST("serviceweb/api/v2/requirement/?obj_update")
    @FormUrlEncoded
    Call<Void> guardarIncidencia(@Query("username") String username,
                                 @Query("api_key") String api_key,
                                 @Query("limit") int limit,
                                 @Field("school") String school,
                                 @Field("reason") String reason,
                                 @Field("state") int state,
                                 @Field("type") int type,
                                 @Field("user") String user,
                                 @Field("is_active") String is_active,
                                 @Field("updated_by") String updated_by,
                                 @Field("created_by") String created_by

    );


}
