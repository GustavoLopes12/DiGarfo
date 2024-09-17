package com.example.digarfo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;

public class escreverreceita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_escreverreceita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //atributos qe vao receber os campos da activity
    //img
    EditText titulo;
    EditText tempo;
    //dificuldade
    EditText ingredientes;
    EditText modo_prep;








    //*********************** FAZENDO CAMPOS FUNCIONAREM ***************************
    //******************************************************************************

    //-campo titulo


    //-botao enviar receita

    //*********************** FIM-FAZENDO CAMPOS FUNCIONAREM ***********************
    //******************************************************************************






    //************************ PARTE DE BAIXO ********************************************
    //************************************************************************************
    boolean usuariologado = true; //variavel usada em todas as funções
    public void irparaperfil(View view){
        if(usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), editarperfil.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você não está logado, faça login para editar seu perfil!!!", Toast.LENGTH_SHORT).show();
            Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(outraTela);
        }
    }
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
    }
    public void irparainserir(View view){
        if(usuariologado){
            Toast.makeText(this, "Você já está em escrever receitas :)", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
        }

    }
    public void irparafavoritos(View view){
        if (usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), favoritoslogado.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você ainda não esta logado", Toast.LENGTH_SHORT).show();
        }
    }
    //************************ FIM-PARTE DE BAIXO ********************************************
    //************************************************************************************
}