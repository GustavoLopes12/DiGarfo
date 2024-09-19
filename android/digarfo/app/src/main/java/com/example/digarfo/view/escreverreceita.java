package com.example.digarfo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ApiConnection;
import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

public class escreverreceita extends AppCompatActivity {
    //ApiConnection conexao;
    //ApiConnection.UsuarioCallback usuarioCallback;

    //img
    EditText titulo; //certo
    EditText tempo; //certo
   //dificuldade
    EditText ingredientes; //certo
    EditText modo_prep; //certo
    //dificuldade-----!!!!! colocar radio (bolinhas opção, pq ta como img) !!!!!!! -----
    EditText custo;
    EditText categoria;

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

        // Referenciar o Spinner no layout
        Spinner spinner = findViewById(R.id.my_spinner);

        // Criar uma lista de opções
        String[] options = {"Nenhum", "Doces", "Salgados", "Saladas"};

        // Criar um ArrayAdapter usando a lista de opções e o layout padrão do Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplicar o ArrayAdapter ao Spinner
        spinner.setAdapter(adapter);

        // Definir o item selecionado como "Nenhum"
        spinner.setSelection(0); // Seleciona o primeiro item, que é "Nenhum"


        //img
        titulo =  findViewById(R.id.title_input_rct);
        tempo =  findViewById(R.id.time_input_rct);
        ingredientes = findViewById(R.id.ingred_input_rct);
        modo_prep = findViewById(R.id.prep_input_rct);
        //dificuldade
        custo = findViewById(R.id.custo_input_rct);
    }

    //*********************** FAZENDO CAMPOS FUNCIONAREM ***************************
    //******************************************************************************

    //-botao enviar receita
    public void enviar_rct(View view){
        Receita receita = new Receita(null, titulo.getText().toString(), custo.getText().toString(), categoria.getText().toString(), null, tempo.getText().toString(),
                ingredientes.getText().toString(), modo_prep.getText().toString(), null, true);

        //conexao.criarUsuario(user, usuarioCallback);----!!! CRIAR NO APCONNECTION !!!------
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(outraTela);

    }
    //-FIM-botao enviar receita

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