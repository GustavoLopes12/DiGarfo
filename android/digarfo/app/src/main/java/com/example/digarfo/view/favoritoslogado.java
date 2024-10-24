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

public class favoritoslogado extends AppCompatActivity {
    String emailUSUARIO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favoritoslogado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //pegando o email do usuario logado
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
    }
    public void irparaperfil(View view){
        Intent intent = new Intent(favoritoslogado.this, editarperfil.class);
        intent.putExtra("Email", emailUSUARIO);
        startActivity(intent);
        finish();
    }
    public void botaohome(View view){
        Intent intent = new Intent(favoritoslogado.this, home.class);
        intent.putExtra("Email", emailUSUARIO);
        startActivity(intent);
        finish();
    }
    public void irparainserir(View view){
        Intent intent = new Intent(favoritoslogado.this, escreverreceita.class);
        intent.putExtra("Email", emailUSUARIO);
        startActivity(intent);
        finish();
    }
    public void irparafavoritos(View view){
        Toast.makeText(this, "Você já está em favoritos :)", Toast.LENGTH_SHORT).show();
    }
}