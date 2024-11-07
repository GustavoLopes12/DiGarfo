package com.example.admdigarfo.model;
import com.google.gson.annotations.SerializedName;


public class Adm {
    //atributos
    @SerializedName("email")
    private String email;

    @SerializedName("senha")
    private String senha;

    //construtores
    public Adm(){
        //default
    }
    public Adm(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    //getters e setters
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

    //toString (copiei do user do digarfo)
    @Override
    public String toString() {
        return "Adm{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }


}
