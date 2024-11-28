package com.example.admdigarfo.view;

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

import com.example.admdigarfo.R;
import com.example.admdigarfo.api.AdmAPIController;
import com.example.admdigarfo.api.RetrofitClient;
import com.example.admdigarfo.model.Adm;

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

        //pegando valores inseridos activity
        email = findViewById(R.id.email);
        senha = findViewById(R.id.password);
    }

    //login adm
    public void login(View view){
        //pegando valores
        String emailString = email.getText().toString();
        String senhaString = senha.getText().toString();
        //cliente
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        AdmAPIController admAPIController = new AdmAPIController(retrofitClient);
        //login
        admAPIController.Login(emailString, senhaString, new AdmAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Adm adm) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setCancelable(false);
                if(adm.getEmail() == null){
                    alerta.setTitle("Esse adm não existe");
                    alerta.setMessage("Faça login com um adm existente");
                    alerta.setNegativeButton("Voltar",null);
                    alerta.create().show();
                    email.setText(null);
                    senha.setText(null);
                }else{
                    String emailGuardado = email.getText().toString();//para guardar o email do adm
                    alerta.setTitle("Login feito com sucesso");
                    alerta.setMessage("Login realizado com sucesso ADM");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
                    email.setText(null);
                    senha.setText(null);
                    //levar o email do adm para a activitie de editar perfil para quando clicado poder editar seu perfil
                    Intent intent = new Intent(MainActivity.this, receitas_avaliar.class);
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
                //alerta.setMessage(t.getMessage());

                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
            }
        });
    }
}