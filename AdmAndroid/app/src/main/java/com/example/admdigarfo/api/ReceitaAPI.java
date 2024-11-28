package com.example.admdigarfo.api;

import com.example.admdigarfo.model.Receita;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ReceitaAPI {

    @GET("/receita/aprovadaFalse")
    Call<List<Receita>> pegarReceitasNaoAprov();

    @GET("/receita/{id}")//pegar receita por id
    Call<Receita> getReceita(@Path("id") Long id);

    @GET("/receita/buscarImagem/{id_receita}")
    Call<ResponseBody> getImagemReceita(@Path("id_receita") Long idReceita);
    @PUT("/receita/{id_receita}")
    Call<Receita> attReceita(@Path("id_receita") Long id_receita, @Body Receita receita);
}
