package com.example.digarfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterReceita extends RecyclerView.Adapter<AdapterReceita.MyViewHolder> {

    private List<resultadopesquisa> listaReceita;

    // Construtor do adaptador
    public AdapterReceita(List<resultadopesquisa> lista) {
        this.listaReceita = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item e cria o MyViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_resultadopesquisa, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        resultadopesquisa resultadopesquisa = listaReceita.get(position);
        holder.rct_result_name.setText(resultadopesquisa.getNome_rct());
        holder.rct_result_ratingBar.setRating(resultadopesquisa.getRating());
        holder.rct_result_img.setImageResource(resultadopesquisa.getImagemId());//*********
        // Configurar a ImageView se necessário
    }

    @Override
    public int getItemCount() {
        return listaReceita.size();
    }

    // Classe ViewHolder interna
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rct_result_name;
        RatingBar rct_result_ratingBar;
        ImageView rct_result_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            rct_result_name = itemView.findViewById(R.id.rct_result_name);
            rct_result_ratingBar = itemView.findViewById(R.id.rct_result_ratingBar);
            rct_result_img = itemView.findViewById(R.id.rct_result_img);
        }
    }
}

