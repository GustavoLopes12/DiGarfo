package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    //put com imagem do usuario
    @Multipart
    @PUT("/usuario/UsuarioImage/{email}")
    Call<Usuario> atualizarUsuarioComImagem(
            @Path("email")String email,
            @PartMap Map<String, RequestBody> usuarioPartes,
            @Part MultipartBody.Part arquivo
    );
    @GET("/usuario/buscarImagem/{email}")//pegar imagem do usuario
    Call<ResponseBody> getImagemUsuario(@Path("email") String email);
}
