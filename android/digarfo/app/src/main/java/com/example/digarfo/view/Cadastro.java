package com.example.digarfo.view;

import static com.example.digarfo.conexao_spring.ApiConnection.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ApiConnection;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Usuario;

public class Cadastro extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText senha;
    EditText descricao;
    ImageView img;

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
        descricao = findViewById(R.id.descrição);
        img = findViewById(R.id.foto);
        //pegando img pela galeria
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Escolha sua Imagem"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1){
                img.setImageURI(data.getData());
            }
        }
    }

    //cadastro
    public void cadastrar(View view){
        //pegando valores para criação
        String emailString = email.getText().toString();
        String nomeString = name.getText().toString();
        String senhaString = senha.getText().toString();
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //cadastro
        usuarioAPIController.Cadastro(nomeString, emailString, senhaString, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Cadastro.this);
                alerta.setCancelable(false);
                alerta.setTitle("Cadastro Realizado");
                alerta.setMessage("Seu cadastro foi realizado com sucesso");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
                name.setText(null);
                email.setText(null);
                senha.setText(null);
                Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(outraTela);
            }
            @Override
            public void onFailure(Throwable t) {
                androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(Cadastro.this);
                alerta.setCancelable(false);
                alerta.setTitle("Conexão Falhou, não é possivel realizar cadastro, tente novamente");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
                name.setText(null);
                email.setText(null);
                senha.setText(null);
            }
        });
    }
    //botao home sem login
    public void homesemlogin(View view){//indo para outra pagina
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
    }
    //voltar login
    public void outrapagina(View view){//indo para outra pagina
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(outraTela);
    }
}