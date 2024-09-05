package com.example.digarfo.conexao_spring;

public class Conexao {
    interface RequestUsuario{
        //endpoints
    }
    interface RequestAdm{
        //endpoints
    }
    String urlBase = "http://localhost:8080";
    RequestUsuario requestUsuario;
    RequestAdm requestAdm;
}
