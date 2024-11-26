package com.example.digarfo.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ReceitaAPIController;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.model.Receita;

import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;

public class Adapter_Minhas_Receitas extends RecyclerView.Adapter<Adapter_Minhas_Receitas.MyViewHolder> {
    private List<Receita> lista_receita_minhas;

    public Adapter_Minhas_Receitas(List<Receita> lista_receita_minhas){this.lista_receita_minhas = lista_receita_minhas;}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemlista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_minhas_receitas, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemlista);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Receita receita = lista_receita_minhas.get(position);
        holder.nome_receita.setText(receita.getNome_receita());
        holder.id_rct.setText(receita.getId_receita().toString());
        //id da receita
        Long id_rct = receita.getId_receita();
        //pegando a imagem da receita e colocando no holder
        RetrofitClient retrofitClient2 = new RetrofitClient();
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient2);
        //colocando a img
        receitaAPIController.buscarImagem(id_rct, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    // Converta a resposta para Bitmap
                    InputStream inputStream = responseBody.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if(bitmap != null){
                        // Exiba o Bitmap em uma ImageView
                        holder.imagem_receita.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    Log.e("Erro", "Erro ao processar imagem: " + e.getMessage());
                }
            }

            @Override
            public void onSuccess(Receita receita) {

            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Erro", "Falha ao buscar imagem: " + t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.lista_receita_minhas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imagem_receita;
        TextView nome_receita;
        TextView id_rct;

        public MyViewHolder(View itemView) {
            super(itemView);
            imagem_receita = itemView.findViewById(R.id.imageView833333333333333);
            id_rct = itemView.findViewById(R.id.id_rct_my);
            nome_receita = itemView.findViewById(R.id.nome_rct);
        }
    }//fim classe holder
}
