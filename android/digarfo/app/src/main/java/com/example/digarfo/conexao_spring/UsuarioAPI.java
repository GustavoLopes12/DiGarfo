package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Usuario;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UsuarioAPI {
    @POST("/usuario/login")
    Call<Usuario> loginUsuario(@Body Usuario usuario);
    @POST("/usuario")
    Call<Usuario> criarUsuario(@Part("usuario") Usuario usuario, @Part MultipartBody.Part arquivo);
}
