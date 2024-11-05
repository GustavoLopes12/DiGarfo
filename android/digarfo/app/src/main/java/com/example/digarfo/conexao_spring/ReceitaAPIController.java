package com.example.digarfo.conexao_spring;

import android.view.View;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    public void enviarReceita(String nome_receita, String custo_selecionado, String categoria, String dific_selecionada,String tempo_prep, String ingredientes, String modo_prep, String id_autor, ReceitaAPIController.ResponseCallback responseCallback){
        Usuario usuario = new Usuario(id_autor);
        Receita receita = new Receita(nome_receita,  custo_selecionado,  categoria,  dific_selecionada, tempo_prep,  ingredientes,  modo_prep, usuario);
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

}
