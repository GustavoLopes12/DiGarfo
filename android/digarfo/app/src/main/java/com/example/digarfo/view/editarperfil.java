package com.example.digarfo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;

public class editarperfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editarperfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    boolean usuariologado = true; //variavel usada em todas as funções
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
    }
    public void irparainserir(View view){
        if(usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), escreverreceita.class);
            startActivity(outraTela);
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
}