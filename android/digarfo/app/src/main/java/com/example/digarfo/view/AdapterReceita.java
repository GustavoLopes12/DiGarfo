package com.example.digarfo.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.util.List;

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

        //ImageView imagem_receita;
        TextView nome_receita;
        TextView nome_autor_receita;

        public MyViewHolder(View itemView) {
            super(itemView);
            //imagem_receita = itemView.findViewById(R.id.image_rct);
            nome_receita = itemView.findViewById(R.id.nome_rct);
            nome_autor_receita = itemView.findViewById(R.id.nome_aut);
        }

    }//fim classe holder
}//fim recicla view kkk
