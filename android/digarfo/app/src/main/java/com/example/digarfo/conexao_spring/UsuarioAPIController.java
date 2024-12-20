package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
        void onSuccess(ResponseBody responseBody);
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

    //pegar usuario por receita
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


    //pegar usuario por id(email)
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
    public void Cadastro(String nome, String email, String senha, String descricao, UsuarioAPIController.ResponseCallback responseCallback){
        Usuario user = new Usuario(email, nome, senha, descricao);

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
    //atualizar usuario
    /*public void atualizar(Usuario usuario, String email, UsuarioAPIController.ResponseCallback responseCallback){
        Call<Usuario> call = this.usuarioAPI.attUsuario(email,usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                responseCallback.onFailure(new Exception("nao foi possivel atualizar o usuario"));
            }
        });
    }*/
    //pegar imagem de usuario
    public void buscarImagem(String email, UsuarioAPIController.ResponseCallback responseCallback){
        Call<ResponseBody> call = this.usuarioAPI.getImagemUsuario(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccess(response.body());
                } else {
                    responseCallback.onFailure(new Exception("Erro ao buscar imagem: " + response.message()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }
    //att usuario com imagem
    public void atualizar(String email, Usuario usuario, File file, UsuarioAPIController.ResponseCallback responseCallback){
        // Mapear os atributos da receita para form-data
        Map<String, RequestBody> usuarioPartes = new HashMap<>();
        usuarioPartes.put("nome_usuario", RequestBody.create(MediaType.parse("text/plain"), usuario.getNome_usuario()));
        usuarioPartes.put("descricao", RequestBody.create(MediaType.parse("text/plain"), usuario.getDescricao()));
        usuarioPartes.put("img_user", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(usuario.getImg_user())));
        usuarioPartes.put("senha", RequestBody.create(MediaType.parse("text/plain"), usuario.getSenha()));
        usuarioPartes.put("email", RequestBody.create(MediaType.parse("text/plain"), usuario.getSenha()));
        // Arquivo da imagem
        MultipartBody.Part filePart = null;
        if (file != null) {
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
        }
        //chamada retrofit
        Call<Usuario> call = this.usuarioAPI.atualizarUsuarioComImagem(email, usuarioPartes, filePart);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                responseCallback.onFailure(new Exception("Erro ao atualizar Usuario"));
            }
        });
    }
    //DELETAR USUARIO
    public void deletar(String email, UsuarioAPIController.ResponseCallback responseCallback){
        Call<ResponseBody> call = this.usuarioAPI.deletUsuario(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                responseCallback.onFailure(new Exception("nao foi possivel deletar o usuario"));
            }
        });
    }

}
