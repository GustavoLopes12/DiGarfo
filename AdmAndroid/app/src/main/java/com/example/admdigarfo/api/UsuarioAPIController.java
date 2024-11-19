package com.example.admdigarfo.api;

import com.example.admdigarfo.model.Usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioAPIController {

    //atributos
    private RetrofitClient retrofitClient;
    public static UsuarioAPIController.ResponseCallback ResponseCallback;
    //private String status;
    private Usuario usuario;
    private UsuarioAPI usuarioAPI;

    //interface de response callback
    public interface ResponseCallback {
        void onSuccess(Usuario usuario);
        void onSuccess(ResponseBody responseBody);
        void onFailure(Throwable t);
    }
    public UsuarioAPIController(RetrofitClient retrofitClient) { //construtor
        this.retrofitClient = retrofitClient;
        this.usuarioAPI = RetrofitClient.getRetrofitInstance().create(UsuarioAPI.class);
        //this.status = "";
    }

    //-pegar usuario por id(email)
    public void getUsuario(String email, UsuarioAPIController.ResponseCallback responseCallback){
        Call<Usuario> call = this.usuarioAPI.getUsuario(email);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel pegar o usuario pelo email"));
            }
        });
    }

    //-pegar usuario por receita
    public void getUsuarioForReceita(Long id_receita, UsuarioAPIController.ResponseCallback responseCallback){
        Call<Usuario> call = this.usuarioAPI.getUsuarioForReceita(id_receita);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel pegar o usuario pela sua receita"));
            }
        });

    }
}
