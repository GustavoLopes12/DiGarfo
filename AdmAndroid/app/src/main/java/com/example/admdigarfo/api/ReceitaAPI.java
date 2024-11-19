package com.example.admdigarfo.api;

import com.example.admdigarfo.model.Receita;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ReceitaAPI {

    @GET("/receita/aprovadaFalse")
    Call<List<Receita>> pegarReceitasNaoAprov();
}
