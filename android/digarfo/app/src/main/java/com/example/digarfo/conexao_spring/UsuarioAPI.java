package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Usuario;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UsuarioAPI {

    @GET("/usuario/receita/{idReceita}")
    Call<Usuario> getUsuarioForReceita(@Path("idReceita") Long id_receita);
    @GET("/usuario/email/{email}")//pegar usuario por id
    Call<Usuario> getUsuario(@Path("email") String email);

    @POST("/usuario/login")//login
    Call<Usuario> loginUsuario(@Body Usuario usuario);
    @POST("/usuario")
    Call<Usuario> criarUsuario(@Body Usuario usuario);
    @PUT("/usuario/{email}")
    Call<Usuario> attUsuario(@Path("email") String email, @Body Usuario usuario);

    @DELETE("/usuario/{email}")
    Call<ResponseBody> deletUsuario(@Path("email") String email);
}
