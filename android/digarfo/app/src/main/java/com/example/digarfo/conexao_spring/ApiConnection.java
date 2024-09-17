package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Adm;
import com.example.digarfo.model.AdmList;
import com.example.digarfo.model.Usuario;
import com.example.digarfo.model.UsuarioList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiConnection {
    //interface com os endpoints
    interface RequestUser{
        //usuario
       @GET("/usuario")
       Call<UsuarioList> getListaUsuarios();
       @GET("/usuario/{email}")
       Call<Usuario> getUsuario(@Path("email") String email);
       @POST("/usuario")
        Call<Usuario> criarUsuario(@Body Usuario usuario);
       @PUT("/usuario/{email}")
        Call<Usuario> attUsuario(@Path("email") String email, Usuario usuario);
       @DELETE("/usuario/{email}")
        Call<Usuario> deleteUsuario(@Path("email") String email);
       //adm
        @GET("/adm")
        Call<AdmList> getListaAdms();
        @GET("/adm/{email}")
        Call<Adm> getAdm(@Path("email") String email);
        @POST("/adm")
        Call<Adm> criarAdm(@Body Adm adm);
        @PUT("/adm/{email}")
        Call<Adm> attAdm(@Path("email") String email);
        @DELETE("/adm/{email}")
        Call<Adm> deleteAdm(@Path("email") String email);
    }
    //link do banco
    String urlBase = "http://localhost:8080";//do banco
    //requestUser objeto
    RequestUser requestUser;
    //construtor
    public ApiConnection() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestUser = retrofit.create(RequestUser.class);
    }
    //criar usuario //cadastro
    public interface UsuarioCallback {
        void onSuccess(Usuario usuario);
        void onFailure(Throwable t);
    }
    public void criarUsuario(Usuario usuario, UsuarioCallback usuariocallback){
        Call<Usuario> call = requestUser.criarUsuario(usuario);
        call.enqueue(new Callback<Usuario>(){
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                usuariocallback.onSuccess(response.body());
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                usuariocallback.onFailure(new Exception("Request failed"));
            }
        });
    }
}
