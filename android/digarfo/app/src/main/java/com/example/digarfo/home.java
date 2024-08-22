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
<<<<<<< HEAD

<<<<<<< HEAD
    public void inserir_rct_deslog(View view){
        Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
    }

=======
    public void notificacaofavoritosdeslogado(View view){//indo para outra pagina
        Toast.makeText(this,"Você ainda não esta logado", Toast.LENGTH_SHORT).show();
>>>>>>> 3800cdb106394c2366d4405714a6f22254e317ad
=======
    //botao de usuario
    public void usuariodeslogado(View view){//indo para pagina de login
        Toast.makeText(this, "Você não está logado, faça login para editar seu perfil!!!", Toast.LENGTH_SHORT).show();
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(outraTela);
    }
    //botao home
    public void botaohome(View view){//indo para pagina de login
        Toast.makeText(this, "Você já está na home", Toast.LENGTH_SHORT).show();
>>>>>>> a3fe1a6df5d91ceda22c21306391d982310c5177
    }
>>>>>>> a3fe1a6df5d91ceda22c21306391d982310c5177
}