package com.example.digarfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    boolean usuariologado = true; //variavel usada em todas as funções


    //***************************************************************
    //****************** BOTAO USUÁRIO **********************
    public void irparaperfil(View view){
        if(usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), visualizacaodeperfil.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você não está logado, faça login para editar seu perfil!!!", Toast.LENGTH_SHORT).show();
            Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(outraTela);
        }
    }
    //****************** FIM-BOTAO USUÁRIO **********************


    //***************************************************************

    //****************** BOTAO HOME **********************
    public void botaohome(View view){
        Toast.makeText(this, "Você já está na home :)", Toast.LENGTH_SHORT).show();
    }
    //****************** FIM-BOTAO HOME **********************


    //***************************************************************
    //****************** BOTAO INSERIR RECEITA **********************
    public void irparainserir(View view){
        if(usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), escreverreceita.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
        }

    }
    //****************** FIM-BOTAO INSERIR RECEITA **********************



    //***************************************************************
    //****************** BOTAO FAVORITOS **********************
    public void irparafavoritos(View view){
        if (usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), favoritoslogado.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você ainda não esta logado", Toast.LENGTH_SHORT).show();
        }
    }
    //****************** FIM-BOTAO FAVORITOS **********************


//--------------------------------------------------------------------------------------------------

    //CATEGORIAS IR PARA PAGINA DE PESQUISA [ainda nao funciona]------------------
    public void categ_to_resultpesq(View view) {
        Intent outraTela = new Intent(getApplicationContext(), resultadopesquisa.class);
        startActivity(outraTela);
    }
}