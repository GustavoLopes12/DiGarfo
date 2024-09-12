package com.example.digarfo.view;

import static com.example.digarfo.conexao_spring.ApiConnection.*;
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
import com.example.digarfo.conexao_spring.ApiConnection;
import com.example.digarfo.model.Usuario;

public class Cadastro extends AppCompatActivity {
    ApiConnection conexao;
    UsuarioCallback usuarioCallback;
    EditText name;
    EditText email;
    EditText senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.nameinput);
        email = findViewById(R.id.emailinput);
        senha = findViewById(R.id.senhainput);
    }
    //cadastro
    public void cadastrar(View view){
        Usuario user = new Usuario(email.getText().toString(),name.getText().toString(),senha.getText().toString());
        conexao.criarUsuario(user, usuarioCallback);
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(outraTela);
    }
}