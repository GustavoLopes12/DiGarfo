package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioAPIController {
    //atributos
    private RetrofitClient retrofitClient;
    public static UsuarioAPIController.ResponseCallback ResponseCallback;
    private String status;
    private Usuario usuario;
    private UsuarioAPI usuarioAPI;

    //interface de response callback
    public interface ResponseCallback {
        void onSuccess(Usuario usuario);
        void onFailure(Throwable t);
    }
    public UsuarioAPIController(RetrofitClient retrofitClient) { //construtor
        this.retrofitClient = retrofitClient;
        this.usuarioAPI = RetrofitClient.getRetrofitInstance().create(UsuarioAPI.class);
        this.status = "";
    }
    public String getMessage() {
        return status;
    }
    //login
    public void Login(String email, String password, UsuarioAPIController.ResponseCallback responseCallback) {

        Usuario user = new Usuario(email, password);

        Call<Usuario> call = this.usuarioAPI.loginUsuario(user);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                responseCallback.onSuccess(response.body());
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel fazer login"));
            }
        });
    }
    //cadastro
    public void Cadastro(String nome, String email, String senha, UsuarioAPIController.ResponseCallback responseCallback){
        Usuario user = new Usuario(email, nome, senha);
        Call<Usuario> call = this.usuarioAPI.criarUsuario(user);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel cadastrar o usuario"));
            }
        });
    }
}
