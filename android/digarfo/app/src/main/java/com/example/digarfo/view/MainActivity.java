package com.example.digarfo.view;

import android.app.AlertDialog;
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
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Usuario;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText senha;

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
        email = findViewById(R.id.email);
        senha = findViewById(R.id.password);
    }

    // login api
    public void login(View view){
        //pegando valores
        String emailString = email.getText().toString();
        String senhaString = senha.getText().toString();
        //cliente
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //login
        usuarioAPIController.Login(emailString, senhaString, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setCancelable(false);
                if(usuario.getEmail() == null){
                    alerta.setTitle("Esse usuario não existe");
                    alerta.setMessage("Faça login com um usuario existente ou crie um usuario novo");
                    alerta.setNegativeButton("Voltar",null);
                    alerta.create().show();
                    email.setText(null);
                    senha.setText(null);
                }else{
                    String emailGuardado = email.getText().toString();//para guardar o email do usuario
                    alerta.setTitle("Login feito com sucesso");
                    alerta.setMessage("Login realizado com sucesso, desfrute o DiGarfo :)");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
                    email.setText(null);
                    senha.setText(null);
                    //levar o email do usuario para a activitie de editar perfil para quando clicado poder editar seu perfil
                    Intent intent = new Intent(MainActivity.this, home.class);
                    intent.putExtra("Email", emailGuardado);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                alerta.setCancelable(false);
                alerta.setTitle("Conexão Falhou, não é possivel realizar login");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
            }
        });
    }
    //Cadastro
    public void outrapagina(View view){//indo para outra pagina
        Intent outraTela = new Intent(getApplicationContext(), Cadastro.class);
        startActivity(outraTela);
    }
    //login sem api "improvisado"
    /*boolean usuariologado = true; //variavel usada em todas as funções
    public void irparahomelogado(View view){
        if(usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), home.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você não está logado, faça login para entrar no app o entre sem login", Toast.LENGTH_SHORT).show();
            Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(outraTela);
        }
    }*/
    //home sem login
    public void homesemlogin(View view){//indo para outra pagina
        String emailGuardado = null;//para guardar o email do usuario
        //levar o email do usuario para a activitie de editar perfil para quando clicado poder editar seu perfil
        Intent intent = new Intent(MainActivity.this, home.class);
        intent.putExtra("Email", emailGuardado);
        startActivity(intent);
        finish();
    }
}