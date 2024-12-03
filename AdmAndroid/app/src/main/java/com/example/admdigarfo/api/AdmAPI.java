package com.example.admdigarfo.api;

import com.example.admdigarfo.model.Adm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdmAPI {
    //copiei do user e mudei p adm
    @POST("/adm/loginAdm")
    Call<Adm> loginAdm(@Body Adm adm);

    @GET("/adm/{email}")
    Call<Adm> buscar(@Path("id") String email);
}
