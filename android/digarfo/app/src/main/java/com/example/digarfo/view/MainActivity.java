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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void outrapagina(View view){//indo para outra pagina
        Intent outraTela = new Intent(getApplicationContext(), Cadastro.class);
        startActivity(outraTela);
    }
    boolean usuariologado = true; //variavel usada em todas as funções
    public void irparahomelogado(View view){
        if(usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), home.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você não está logado, faça login para entrar no app o entre sem login", Toast.LENGTH_SHORT).show();
            Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(outraTela);
        }
    }
    public void homesemlogin(View view){//indo para outra pagina
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
    }
}