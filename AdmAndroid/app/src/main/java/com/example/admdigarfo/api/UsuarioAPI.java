package com.example.admdigarfo.api;

import com.example.admdigarfo.model.Usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioAPI {
    @GET("/usuario/email/{email}")//pegar usuario por id
    Call<Usuario> getUsuario(@Path("email") String email);

    @GET("/usuario/receitaF/{idReceita}")
    Call<Usuario> getUsuarioForReceita(@Path("idReceita") Long idReceita);

    @GET("/usuario/buscarImagem/{email}")//pegar imagem do usuario
    Call<ResponseBody> getImagemUsuario(@Path("email") String email);
}
