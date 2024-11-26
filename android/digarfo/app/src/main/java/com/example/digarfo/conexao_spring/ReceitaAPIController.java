package com.example.digarfo.conexao_spring;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import retrofit2.http.Multipart;

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

    //pegar img receita
    public void buscarImagem(Long id, ReceitaAPIController.ResponseCallback responseCallback){
        Call<ResponseBody> call = this.receitaAPI.getImagemReceita(id);
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
    //atualizar receita com imagem
    public void attReceitaWithImage(Long id, Receita receita, File file, ReceitaAPIController.ResponseCallback responseCallback){
        // Mapear os atributos da receita para form-data
        Map<String, RequestBody> receitaPartes = new HashMap<>();
        receitaPartes.put("nome_receita", RequestBody.create(MediaType.parse("text/plain"), receita.getNome_receita()));
        receitaPartes.put("categoria", RequestBody.create(MediaType.parse("text/plain"), receita.getCategoria()));
        receitaPartes.put("custo", RequestBody.create(MediaType.parse("text/plain"), receita.getCusto()));
        receitaPartes.put("dificuldade", RequestBody.create(MediaType.parse("text/plain"), receita.getDificuldade()));
        receitaPartes.put("tempo_prep", RequestBody.create(MediaType.parse("text/plain"), receita.getTempo_prep()));
        receitaPartes.put("ingredientes", RequestBody.create(MediaType.parse("text/plain"), receita.getIngredientes()));
        receitaPartes.put("modo_prep", RequestBody.create(MediaType.parse("text/plain"), receita.getModo_prep()));
        receitaPartes.put("aprovada", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receita.getAprovada())));
        receitaPartes.put("img_receita", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receita.getImg_receita())));
        receitaPartes.put("usuario.email", RequestBody.create(MediaType.parse("text/plain"), receita.getUsuario().getEmail()));
        receitaPartes.put("adm", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receita.getAdm())));

        // Arquivo da imagem
        MultipartBody.Part filePart = null;
        if (file != null) {
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
        }
        //chamada retrofit
        Call<Receita> call = this.receitaAPI.atualizarReceitaComImagem(id, receitaPartes, filePart);
        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Call<Receita> call, Response<Receita> response) {
                responseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Receita> call, Throwable t) {
                responseCallback.onFailure(new Exception("Não foi possivel atualizar a receita"));
            }
        });
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
    public void atualizarReceita(Receita receita, Long id, ReceitaAPIController.ResponseCallback responseCallback){
        Call<Receita> call = this.receitaAPI.attReceita(id, receita);
        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Call<Receita> call, Response<Receita> response) {
                responseCallback.onSuccess(response.body());
            }
            @Override
            public void onFailure(Call<Receita> call, Throwable t) {
                responseCallback.onFailure(new Exception("nao foi possivel atualizar a receita"));
            }
        });
    }

    public void criarReceitaComImagem(Receita receita, File file, ReceitaAPIController.ResponseCallback responseCallback) {
        // Mapear os atributos da receita para form-data
        Map<String, RequestBody> receitaPartes = new HashMap<>();
        receitaPartes.put("nome_receita", RequestBody.create(MediaType.parse("text/plain"), receita.getNome_receita()));
        receitaPartes.put("categoria", RequestBody.create(MediaType.parse("text/plain"), receita.getCategoria()));
        receitaPartes.put("custo", RequestBody.create(MediaType.parse("text/plain"), receita.getCusto()));
        receitaPartes.put("dificuldade", RequestBody.create(MediaType.parse("text/plain"), receita.getDificuldade()));
        receitaPartes.put("tempo_prep", RequestBody.create(MediaType.parse("text/plain"), receita.getTempo_prep()));
        receitaPartes.put("ingredientes", RequestBody.create(MediaType.parse("text/plain"), receita.getIngredientes()));
        receitaPartes.put("modo_prep", RequestBody.create(MediaType.parse("text/plain"), receita.getModo_prep()));
        receitaPartes.put("aprovada", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receita.getAprovada())));
        receitaPartes.put("img_receita", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receita.getImg_receita())));
        receitaPartes.put("usuario.email", RequestBody.create(MediaType.parse("text/plain"), receita.getUsuario().getEmail()));
        receitaPartes.put("adm", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receita.getAdm())));

        // Arquivo da imagem
        MultipartBody.Part filePart = null;
        if (file != null) {
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
        }

        // Fazer a chamada Retrofit
        Call<Receita> call = this.receitaAPI.criarReceitaComImagem(receitaPartes, filePart);
        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Call<Receita> call, Response<Receita> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Sucesso", "Receita criada com sucesso: " + response.body());
                    responseCallback.onSuccess(response.body());
                } else {
                    try {
                        // Captura a resposta de erro
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";
                        Log.e("Erro", "Erro ao criar receita: " + errorMessage);
                        responseCallback.onFailure(new Exception("Erro ao criar receita: " + errorMessage));
                    } catch (IOException e) {
                        Log.e("Erro", "Erro ao processar erro do servidor", e);
                        responseCallback.onFailure(new Exception("Erro ao processar erro do servidor"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Receita> call, Throwable t) {
                Log.e("Falha", "Erro na comunicação: " + t.getMessage());
                responseCallback.onFailure(new Exception("Erro na comunicação: " + t.getMessage()));
            }
        });
    }


}
