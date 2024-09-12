package com.example.digarfo.model;

import com.google.gson.annotations.SerializedName;

public class Adm {
    @SerializedName("email")
    private String email;
    @SerializedName("senha")
    private String senha;
    public Adm() {
        //default
    }

    public Adm(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
