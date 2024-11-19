package com.example.admdigarfo.api;

import com.example.admdigarfo.model.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioAPI {

    //usado na funcao getUsuario (?)
    @GET("/usuario/email/{email}")//pegar usuario por id
    Call<Usuario> getUsuario(@Path("email") String email);

    @GET("/usuario/receita/{idReceita}")
    Call<Usuario> getUsuarioForReceita(@Path("idReceita") Long id_receita);
}
