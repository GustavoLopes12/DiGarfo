package com.example.admdigarfo.api;

import com.example.admdigarfo.model.Receita;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ReceitaAPI {

    @GET("/receita/aprovadaFalse")
    Call<List<Receita>> pegarReceitasNaoAprov();

    @GET("/receita/{id}")//pegar receita por id
    Call<Receita> getReceita(@Path("id") Long id);
}
