package com.example.admdigarfo.api;



import android.content.Intent;

import com.example.admdigarfo.model.Adm;
import com.example.admdigarfo.view.receitas_avaliar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdmAPIController {
    //atributos
    private RetrofitClient retrofitClient;
    private AdmAPI admAPI;

    //interface de response callback
    public interface ResponseCallback {
        void onSuccess(Adm adm);
        void onFailure(Throwable t);
    }
    public AdmAPIController(RetrofitClient retrofitClient) { //construtor
        this.retrofitClient = retrofitClient;
        this.admAPI = RetrofitClient.getRetrofitInstance().create(AdmAPI.class);
    }

    //------------------------
    public void Login(String email, String senha, AdmAPIController.ResponseCallback responseCallback) {

        Adm adm = new Adm(email, senha);//construtor na adm

        Call<Adm> call = this.admAPI.loginAdm(adm); //tava dando erro aq, mas Adriano arrumou
        call.enqueue(new Callback<Adm>() {
            @Override
            public void onResponse(Call<Adm> call, Response<Adm> response) {
                responseCallback.onSuccess(response.body());
            }
            @Override
            public void onFailure(Call<Adm> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel fazer login"));
            }
        });

    }
}
