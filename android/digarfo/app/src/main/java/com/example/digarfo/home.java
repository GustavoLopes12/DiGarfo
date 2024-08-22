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
    public void inserir_rct_deslog(View view){//botao notificacao inserir receita deslogado
        Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
    }
    public void notificacaofavoritosdeslogado(View view) {//notificacao botao favoritos deslogado
        Toast.makeText(this, "Você ainda não esta logado", Toast.LENGTH_SHORT).show();
    }
    //botao de usuario
    public void usuariodeslogado(View view){//notificacao deslogado botao usuario
        Toast.makeText(this, "Você não está logado, faça login para editar seu perfil!!!", Toast.LENGTH_SHORT).show();
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(outraTela);
    }
    //botao home
    public void botaohome(View view){//botao home na home
        Toast.makeText(this, "Você já está na home", Toast.LENGTH_SHORT).show();
    }
}