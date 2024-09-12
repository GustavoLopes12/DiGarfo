package com.example.digarfo.model;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("email")
    private String email;
    @SerializedName("nome_usuario")
    private String nome_usuario;
    @SerializedName("banido")
    private boolean banido;
    @SerializedName("img_user")
    private String img_user;
    @SerializedName("senha")
    private String senha;
    @SerializedName("descricao")
    private String descricao;

    public Usuario() {
        //default
    }

    public Usuario(String email, String nome_usuario, String senha) {
        this.email = email;
        this.nome_usuario = nome_usuario;
        this.senha = senha;
        this.banido = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public boolean isBanido() {
        return banido;
    }

    public void setBanido(boolean banido) {
        this.banido = banido;
    }

    public String getImg_user() {
        return img_user;
    }

    public void setImg_user(String img_user) {
        this.img_user = img_user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}