package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioAPI {
    @POST("/usuario/login")
    Call<Usuario> loginUsuario(@Body Usuario usuario);
    @POST("/usuario")
    Call<Usuario> criarUsuario(@Body Usuario usuario);
}
