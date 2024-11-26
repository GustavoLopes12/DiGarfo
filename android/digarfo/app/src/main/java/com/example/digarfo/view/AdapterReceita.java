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
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;

public class AdapterReceita extends RecyclerView.Adapter<AdapterReceita.MyViewHolder> {
    private List<Receita> lista_receita;
    public AdapterReceita(List<Receita> lista_receita){
        this.lista_receita = lista_receita;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemlista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_receita, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemlista);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Receita receita = lista_receita.get(position);
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
        //pegando o autor dessa receita shall we go
        //client retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        // Bora pegar esse usuário
        usuarioAPIController.getUsuarioForReceita(receita.getId_receita(), new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                if (usuario != null && holder.getAdapterPosition() == position) {
                    holder.nome_autor_receita.setText(usuario.getNome_usuario());
                } else {
                    holder.nome_autor_receita.setText("Autor desconhecido");
                }
            }
            @Override
            public void onSuccess(ResponseBody responseBody){}
            @Override
            public void onFailure(Throwable t) {
                if (holder.getAdapterPosition() == position) {
                    holder.nome_autor_receita.setText("Erro ao carregar autor");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.lista_receita.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagem_receita;
        TextView nome_receita;
        TextView nome_autor_receita;

        TextView id_rct;

        public MyViewHolder(View itemView) {
            super(itemView);
            imagem_receita = itemView.findViewById(R.id.imageView833333333333333);
            id_rct = itemView.findViewById(R.id.id_rct);
            nome_receita = itemView.findViewById(R.id.nome_rct);
            nome_autor_receita = itemView.findViewById(R.id.nome_aut);
        }

    }//fim classe holder
}//fim recicla view kkk
