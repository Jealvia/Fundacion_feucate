package com.example.root.educateappcontrolvisitas.api.service;

import com.example.root.educateappcontrolvisitas.api.model.PatchVisita;
import com.example.root.educateappcontrolvisitas.api.model.PostApiKey;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuariosClient {
    @GET("serviceweb/api/v2/usuario/?format=json")
    Call<JsonObject> obtenerNombreUsuario(@Query("username") String username,
                                          @Query("api_key") String api_key,
                                          @Query("user_type") int tipo_usuario
    );


    @POST("serviceweb/api/v2/usuario/login/?format=json")
    @FormUrlEncoded
    Call<JsonObject> login(@Field("us") String us,
                       @Field("ps") String ps

   );

    @GET("api/v2/usuario/?format=json")
    Call<JsonObject> obtenerUsuarioId(@Query("username") String username);

    @PATCH("serviceweb/api/v2/usuario/{user_id}/")
    Call<PostApiKey> actualizarToken(@Path(value = "user_id",encoded = false) String user_id,
                                     @Body PostApiKey postVisita);

    @GET("serviceweb/api/v2/usuario/?format=json")
    Call<JsonObject> obtenerNombreUsuarios();

}
