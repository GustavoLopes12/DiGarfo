package com.example.digarfo.conexao_spring;

import android.view.View;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceitaAPIController {

    /*

    //atributos
    private RetrofitClient retrofitClient;
    public static ReceitaAPIController.ResponseCallback ResponseCallback;
    private String status;
    private Receita receita;
    private ReceitaAPI receitaAPI;

    //interface de response callback
    public interface ResponseCallback {
        void onSuccess(Receita receita);
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
    //login

    //cadastro
    //cadastro
    public void enviarReceita(String nome_receita, String custo_selecionado, String categoria, String dific_selecionada,String tempo_prep, String ingredientes, String modo_prep, String img_receita, boolean aprovacao, ReceitaAPIController.ResponseCallback responseCallback){
        Receita receita = new Receita(nome_receita,  custo_selecionado,  categoria,  dific_selecionada, tempo_prep,  ingredientes,  modo_prep, img_receita, false );
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


    /* --------------------- codigo de antes--------------------------------------------------------------

    public void enviarReceita(String nome_receita, String custo, String categoria, String dificuldade,String tempo_prep, String ingredientes, String modo_prep, ReceitaAPIController.ResponseCallback responseCallback){

        Receita receita = new Receita(nome_receita,  custo,  categoria,  dificuldade, tempo_prep,  ingredientes,  modo_prep);




        Call<Receita> call = this.receitaAPI.criarReceita(receita, arquivoPart);
        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Call<Receita> call, Response<Receita> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Receita> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel inserir a receita"));
            }
        });
    }

     */

}