package com.example.digarfo.model;

import com.google.gson.annotations.SerializedName;

public class Receita {

    @SerializedName("id_receita")
    private Long id_receita;
    @SerializedName("nome_receita")
    private String nome_receita;
    @SerializedName("custo")
    private String custo;
    @SerializedName("categoria")
    private String categoria;
    @SerializedName("dificuldade")
    private String dificuldade;
    @SerializedName("tempo_prep")
    private String tempo_prep;
    @SerializedName("ingredientes")
    private String ingredientes;
    @SerializedName("modo_prep")
    private String modo_prep;
    @SerializedName("img_receita")
    private String img_receita;
    @SerializedName("aprovada")
    private boolean aprovada;

    @SerializedName("motivo_desaprovacao")
    private String motivo_desaprovacao;

    @SerializedName("usuario")
    private Usuario usuario;

    public Receita() {
        //default
    }

    //construtor 2 (sem id)
    public Receita( String nome_receita, String custo, String categoria, String dificuldade, String tempo_prep, String ingredientes, String modo_prep, boolean aprovada,String motivo_desaprovacao, Usuario usuario) {
        this.nome_receita = nome_receita;
        this.custo = custo;
        this.categoria = categoria;
        this.dificuldade = dificuldade;
        this.tempo_prep = tempo_prep;
        this.ingredientes = ingredientes;
        this.modo_prep = modo_prep;
        this.img_receita = null;
        this.aprovada = aprovada;
        this.motivo_desaprovacao = motivo_desaprovacao;
        this.usuario = usuario;
    }
    //getters e setters
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Long getId_receita() {
        return id_receita;
    }
    public void setId_receita(Long id_receita) {
        this.id_receita = id_receita;
    }
    public String getNome_receita() {
        return nome_receita;
    }
    public void setNome_receita(String nome_receita) {
        this.nome_receita = nome_receita;
    }
    public String getCusto() {
        return custo;
    }
    public void setCusto(String custo) {
        this.custo = custo;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getDificuldade() {
        return dificuldade;
    }
    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }
    public String getTempo_prep() {
        return tempo_prep;
    }
    public void setTempo_prep(String tempo_prep) {
        this.tempo_prep = tempo_prep;
    }
    public String getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }
    public String getModo_prep() {
        return modo_prep;
    }
    public void setModo_prep(String modo_prep) {
        this.modo_prep = modo_prep;
    }
    public String getImg_receita() {
        return img_receita;
    }
    public void setImg_receita(String img_receita) {
        this.img_receita = img_receita;
    }
    public boolean getAprovada() {
        return aprovada;
    }
    public void setAprovada(boolean aprovada) {
        this.aprovada = aprovada;
    }
    public String getMotivo_desaprovacao() {
        return motivo_desaprovacao;
    }
    public void setMotivo_desaprovacao(String motivo_desaprovacao) {
        this.motivo_desaprovacao = motivo_desaprovacao;
    }
    @Override
    public String toString() {
        return "Receita{" +
                "id_receita=" + id_receita +
                ", nome_receita='" + nome_receita + '\'' +
                ", custo='" + custo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", dificuldade='" + dificuldade + '\'' +
                ", tempo_prep='" + tempo_prep + '\'' +
                ", ingredientes='" + ingredientes + '\'' +
                ", modo_prep='" + modo_prep + '\'' +
                ", img_receita='" + img_receita + '\'' +
                ", aprovada=" + aprovada +
                ", usuario=" + usuario +
                '}';
    }
}
