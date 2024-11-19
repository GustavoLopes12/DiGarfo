package com.example.admdigarfo.api;

import com.example.admdigarfo.model.Receita;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceitaAPIController {
    private RetrofitClient retrofitClient;
    public static ReceitaAPIController.ResponseCallback ResponseCallback;
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
    }

    //-pegar receitas nao aprov
    public void getReceitasNaoApdv(ReceitaAPIController.ResponseCallback responseCallback){
        Call<List<Receita>> call = this.receitaAPI.pegarReceitasNaoAprov();
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


    //-buscar receita por id
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

}
