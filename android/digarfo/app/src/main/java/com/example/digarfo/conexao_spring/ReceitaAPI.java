package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ReceitaAPI {

    @POST("/receita")
    Call<Receita> criarReceita(@Body Receita receita);

    

}

