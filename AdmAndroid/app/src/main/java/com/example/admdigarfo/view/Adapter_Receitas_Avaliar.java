package com.example.admdigarfo.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admdigarfo.api.RetrofitClient;
import com.example.admdigarfo.api.UsuarioAPIController;
import com.example.admdigarfo.model.Receita;
import com.example.admdigarfo.R;
import com.example.admdigarfo.model.Usuario;

import java.util.List;

import okhttp3.ResponseBody;

public class Adapter_Receitas_Avaliar extends RecyclerView.Adapter<Adapter_Receitas_Avaliar.MyViewHolder> {//ok

    private List<Receita> lista_receita_minhas;//ok

    public Adapter_Receitas_Avaliar(List<Receita> lista_receita_minhas){this.lista_receita_minhas = lista_receita_minhas;} //ok-mudar minhas

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemlista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_receitas_avaliar, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemlista);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Receita receita = lista_receita_minhas.get(position);
        holder.nome_receita.setText(receita.getNome_receita());
        holder.id_rct.setText(receita.getId_receita().toString());

        //ADICIONEI AGORA, N TINHA QND TAVA DANDO ERRO ABRIR INTENT
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
        return this.lista_receita_minhas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{//pra q isso?

        //ImageView imagem_receita;
        TextView nome_receita;
        TextView nome_autor_receita;

        TextView id_rct;

        public MyViewHolder(View itemView) {
            super(itemView);
            //imagem_receita = itemView.findViewById(R.id.image_rct);
            id_rct = itemView.findViewById(R.id.id_rct);
            nome_receita = itemView.findViewById(R.id.nome_rct);
            nome_autor_receita = itemView.findViewById(R.id.nome_aut); //VER ISSO LYT
        }

    }//fim classe holder
}
