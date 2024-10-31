package com.example.digarfo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ReceitaAPIController;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.util.List;

public class home extends AppCompatActivity {
    String emailUSUARIO;
    EditText valorPesquisa;
    String valuePesquisa;

    TextView tv_receita_bd;//nome
    TextView tv_autor_receita_bd;
    Long id_receita_bd;//emailUSER
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
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;

        valorPesquisa = findViewById(R.id.barradepesquisa);


        tv_receita_bd = findViewById(R.id.recebe_receita);
        tv_autor_receita_bd = findViewById(R.id.autor_receita);

        //pegando email de usuario que deverá ser atualizado
        id_receita_bd = 1L;
        //pego usuario do email

        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        receitaAPIController.getReceita(id_receita_bd, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {
                tv_receita_bd.setText(receita.getNome_receita());
                //pega usuario
                //tv_autor_receita_bd.setText(receita.getNomeUsuario());

            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                alerta.setCancelable(false);
                alerta.setTitle("Algo de errado não está certo...");
                alerta.setMessage("Tente editar seu usuario mais tarde!!!");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });


    }

    //-----------------------------------------------------


    //-----------------------------------------------------

    public void irpararesultadopesq(View view) {
        valuePesquisa = valorPesquisa.getText().toString();
        Intent outraTela = new Intent(getApplicationContext(), resultadopesquisa.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("Pesquisa", valuePesquisa);
        startActivity(outraTela);
        finish();
    }

    //ir para perfil se logado senão não
    public void irparaperfil(View view){
        if(emailUSUARIO != null){
            Intent outraTela = new Intent(getApplicationContext(), editarperfil.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
        }else{
            Toast.makeText(this, "Você não está logado, faça login para editar seu perfil!!!", Toast.LENGTH_SHORT).show();
            Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(outraTela);
        }
    }
    public void botaohome(View view){
        Toast.makeText(this, "Você já está na home :)", Toast.LENGTH_SHORT).show();
    }
    public void irparainserir(View view){
        if(emailUSUARIO != null){
            Intent outraTela = new Intent(getApplicationContext(), escreverreceita.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
        }else{
            Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
        }
    }
    /*public void irparafavoritos(View view){
        if (emailUSUARIO != null){
            Intent outraTela = new Intent(getApplicationContext(), favoritoslogado.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
        }else{
            Toast.makeText(this, "Você ainda não esta logado, faça login para ter favoritos", Toast.LENGTH_SHORT).show();
        }
    }*/
    public void resultadopesquisa(View view) {
        Intent outraTela = new Intent(getApplicationContext(), resultadopesquisa.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
}