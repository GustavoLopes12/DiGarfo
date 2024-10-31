package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ReceitaAPI {

    @POST("/receita")
    Call<Receita> criarReceita(@Body Receita receita);

    @GET("/receita")
    Call <List<Receita>> all_receitas();

    @GET("/receita/{id}")//pegar receita por id
    Call<Receita> getReceita(@Path("id") Long id);

   @GET("/receita/nome/{nome}")
    Call<List<Receita>> getReceitaForName(@Path("nome")String nome);

}

