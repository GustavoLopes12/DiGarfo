package com.example.digarfo.conexao_spring;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceitaAPIController {
    //atributos
    private RetrofitClient retrofitClient;
    public static ReceitaAPIController.ResponseCallback ResponseCallback;
    private String status;
    private Receita receita;
    private ReceitaAPI receitaAPI;

    //interface de response callback
    public interface ResponseCallback {
        void onSuccess(ResponseBody responseBody);
        void onSuccess(Receita receita);
       void onSuccessList(List<Receita> receitas);
        void onFailure(Throwable t);
    }
    public ReceitaAPIController(RetrofitClient retrofitClient) { //construtor
        this.retrofitClient = retrofitClient;
        this.receitaAPI = RetrofitClient.getRetrofitInstance().create(ReceitaAPI.class);
        this.status = "";
    }
    public String getMessage() {
        return status;
    }
    //inserir receitas
    public void enviarReceita(String nome_receita, String custo_selecionado, String categoria, String dific_selecionada,String tempo_prep, String ingredientes, String modo_prep, boolean aprovada, String motivo_desaprovacao, String id_autor, ReceitaAPIController.ResponseCallback responseCallback){
        Usuario usuario = new Usuario(id_autor);
        Receita receita = new Receita(nome_receita,  custo_selecionado,  categoria,  dific_selecionada, tempo_prep,  ingredientes,  modo_prep, aprovada, motivo_desaprovacao, usuario);
        Call<Receita> call = this.receitaAPI.criarReceita(receita);
        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Call<Receita> call, Response<Receita> response) {
                responseCallback.onSuccess(response.body());
            }
            @Override
            public void onFailure(Call<Receita> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi inserir a receita"));
            }
        });
    }
    //buscar todas as receitas
    public void BuscarReceitas(ReceitaAPIController.ResponseCallback responseCallback){
        Call <List<Receita>> call = this.receitaAPI.all_receitas();
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Call<List<Receita>> call, Response<List<Receita>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessList(response.body());
                } else {
                    responseCallback.onSuccessList(new ArrayList<>()); // Passa lista vazia se a resposta é nula
                }
            }

            @Override
            public void onFailure(Call<List<Receita>> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel pegar todas as receitas"));
            }
        });
    }
    //buscar todas as receitas aprovadas
    public void buscarReceitasAprovadas(ReceitaAPIController.ResponseCallback responseCallback){
        Call <List<Receita>> call = this.receitaAPI.getReceitasAprovadas();
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Call<List<Receita>> call, Response<List<Receita>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessList(response.body());
                } else {
                    responseCallback.onSuccessList(new ArrayList<>()); // Passa lista vazia se a resposta é nula
                }
            }

            @Override
            public void onFailure(Call<List<Receita>> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel pegar todas as receitas aprovadas"));
            }
        });
    }
    //buscar receitas por termo que pode ser categoria ou nome
    public void buscarReceitaForTermoCN(String termo, ReceitaAPIController.ResponseCallback responseCallback){
        Call<List<Receita>> call = this.receitaAPI.getReceitasSearchCN(termo);
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Call<List<Receita>> call, Response<List<Receita>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessList(response.body());
                } else {
                    responseCallback.onSuccessList(new ArrayList<>()); // Passa lista vazia se a resposta é nula
                }
            }

            @Override
            public void onFailure(Call<List<Receita>> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel pegar todas as receitas por esse termo que pode ser o nome dela ou a categoria dela"));
            }
        });
    }
    //buscar receita por id APROVADA
    public void getReceitaAprovID(Long id, ReceitaAPIController.ResponseCallback responseCallback){
        Call<Receita> call = this.receitaAPI.getReceitaAprovID(id);
        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Call<Receita> call, Response<Receita> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Receita> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel pegar a receita pelo id"));
            }
        });
    }

    //buscar receitas pelo usuario
    public void getReceitaForUser(String email, ReceitaAPIController.ResponseCallback responseCallback){
        Call <List<Receita>> call = this.receitaAPI.pegarReceitasForUsuario(email);
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Call<List<Receita>> call, Response<List<Receita>> response) {
                responseCallback.onSuccessList(response.body());
            }

            @Override
            public void onFailure(Call<List<Receita>> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possível pegar as receitas desse usuario"));
            }
        });
    }

    //buscar receita por id
    public void getReceita(Long id, ReceitaAPIController.ResponseCallback responseCallback){
        Call<Receita> call = this.receitaAPI.getReceita(id);
        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Call<Receita> call, Response<Receita> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Receita> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel pegar a receita pelo id"));
            }
        });
    }
    //buscar receita por nome
    public void getReceitaForName(String nome, ReceitaAPIController.ResponseCallback responseCallback){
            Call <List<Receita>> call = this.receitaAPI.getReceitaForName(nome);
            call.enqueue(new Callback<List<Receita>>() {
                @Override
                public void onResponse(Call<List<Receita>> call, Response<List<Receita>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        responseCallback.onSuccessList(response.body());
                    } else {
                        responseCallback.onSuccessList(new ArrayList<>()); // Passa lista vazia se a resposta é nula
                    }
                }

                @Override
                public void onFailure(Call<List<Receita>> call, Throwable t) {
                    responseCallback.onFailure(new Exception("Não foi possivel pegar a receita pelo nome"));
                }
            });
    }
    public void deletarReceita(Long id_receita, ReceitaAPIController.ResponseCallback responseCallback) {
        Call<ResponseBody> call = this.receitaAPI.deletarReceita(id_receita);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String message = response.body().string(); // Pega a mensagem da resposta
                        Log.d("Sucesso", "Resposta do servidor: " + message);
                        responseCallback.onSuccess(response.body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Erro", "Falha ao deletar: " + response.message());
                    responseCallback.onFailure(new Exception("Falha ao deletar: " + response.message()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Falha", "Erro ao deletar receita: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

}
