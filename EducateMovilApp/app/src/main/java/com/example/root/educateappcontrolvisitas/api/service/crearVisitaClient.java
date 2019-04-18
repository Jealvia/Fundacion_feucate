package com.example.root.educateappcontrolvisitas.api.service;

import com.example.root.educateappcontrolvisitas.api.model.PatchVisita;
import com.example.root.educateappcontrolvisitas.api.model.PostRequirement;
import com.example.root.educateappcontrolvisitas.api.model.PostVisita;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface crearVisitaClient {
    @POST("serviceweb/api/v2/visit/")
    Call<PostVisita> createVisit(@Body PostVisita postVisita);

    @PATCH("serviceweb/api/v2/visit/{visit_id}/")//https://feducate.herokuapp.com/serviceweb/api/v2/visit/10/
    Call<PatchVisita> updateVisit(@Path(value = "visit_id",encoded = false) String visit,//@Path(value = "user_id", encoded = true) String userId
                                  @Body PatchVisita postVisita);

}
