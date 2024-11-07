package com.example.digarfo.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digarfo.R;
import com.example.digarfo.model.Receita;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return this.lista_receita_minhas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //ImageView imagem_receita;
        TextView nome_receita;
        TextView id_rct;

        public MyViewHolder(View itemView) {
            super(itemView);
            //imagem_receita = itemView.findViewById(R.id.image_rct);
            id_rct = itemView.findViewById(R.id.id_rct_my);
            nome_receita = itemView.findViewById(R.id.nome_rct);
        }
    }//fim classe holder
}
