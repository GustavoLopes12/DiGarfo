package com.example.digarfo.conexao_spring;

import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ReceitaAPI {

    @GET("/receita/allRecipesForUser/{email}")
    Call<List<Receita>> pegarReceitasForUsuario(@Path("email") String email);

    @POST("/receita")
    Call<Receita> criarReceita(@Body Receita receita);

    //post com imagem da receita
    @Multipart
    @POST("/receita/ReceitaWithImage")
    Call<Receita> criarReceitaComImagem(
            @PartMap Map<String, RequestBody> receitaPartes,
            @Part MultipartBody.Part arquivo
    );
    //put com imagem da receita
    @Multipart
    @PUT("/receita/receitaImage/{id_receita}")
    Call<Receita> atualizarReceitaComImagem(
            @Path("id_receita")Long id_receita,
            @PartMap Map<String, RequestBody> receitaPartes,
            @Part MultipartBody.Part arquivo
    );

    @GET("/receita")
    Call <List<Receita>> all_receitas();

    @GET("/receita/{id}")//pegar receita por id
    Call<Receita> getReceita(@Path("id") Long id);

    //pegar img de uma receita
    @GET("/receita/buscarImagem/{id_receita}")
    Call<ResponseBody> getImagemReceita(@Path("id_receita") Long idReceita);

    @GET("/receita/receitaIDtrue/{id}")//pegar receita aprovada por id
    Call<Receita> getReceitaAprovID(@Path("id") Long id);

   @GET("/receita/nome/{nome}")
    Call<List<Receita>> getReceitaForName(@Path("nome")String nome);

   @GET("/receita/aprovada")
    Call<List<Receita>> getReceitasAprovadas();

   @GET("/receita/search/{termo}")
   Call<List<Receita>> getReceitasSearchCN(@Path("termo") String termo);

    @DELETE("/receita/{id}")
    Call<ResponseBody> deletarReceita(@Path("id") Long id);

    @PUT("/receita/{id}")
    Call<Receita> attReceita(@Path("id") Long id, @Body Receita receita);
}

