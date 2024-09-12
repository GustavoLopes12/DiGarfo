package com.example.digarfo.model;

import com.google.gson.annotations.SerializedName;

public class Comentario {
    @SerializedName("id_comentario")
    private long id_comentario;
    @SerializedName("conteudo")
    private String conteudo;
    public Comentario(){
        //default
    }
    public Comentario(long id_comentario, String conteudo) {
        this.id_comentario = id_comentario;
        this.conteudo = conteudo;
    }

    public long getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(long id_comentario) {
        this.id_comentario = id_comentario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
