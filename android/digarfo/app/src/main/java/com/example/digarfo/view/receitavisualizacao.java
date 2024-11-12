package com.example.digarfo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class receitavisualizacao extends AppCompatActivity {
   // String pesquisaString;//valor da pesquisa
    String emailUSUARIO;
    Long id_long;

    //coisas do layout
    TextView nome_autor;
    TextView nome_receita;
    TextView ingredientes;
    TextView modo_prep;
    TextView custo;
    TextView categoria;
    TextView tempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_receitavisualizacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //oque pesquisou?
        //String pesquisaGuardada = getIntent().getStringExtra("Pesquisa");
       // pesquisaString = pesquisaGuardada;
        //quem tá logado?
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        //pegar id da receita
        String id_guardado = getIntent().getStringExtra("id_rct");
        id_long = Long.parseLong(id_guardado);
        //relacion
        nome_autor = findViewById(R.id.autor_name);
        nome_receita = findViewById(R.id.nome_r);
        ingredientes = findViewById(R.id.ingredientes);
        modo_prep = findViewById(R.id.modo_prep);
        custo = findViewById(R.id.custo);
        categoria = findViewById(R.id.categoria);
        tempo = findViewById(R.id.tempo);
        carregar_rct();
    }
    public void carregar_rct(){
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //receita api controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        receitaAPIController.getReceita(id_long, new ReceitaAPIController.ResponseCallback() {

            @Override
            public void onSuccess(Receita receita) {
                nome_receita.setText(receita.getNome_receita());
                ingredientes.setText(receita.getIngredientes());
                modo_prep.setText(receita.getModo_prep());
                custo.setText(receita.getCusto());
                categoria.setText(receita.getCategoria());
                tempo.setText(receita.getTempo_prep());
                carregarAutor(receita.getId_receita());
            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(receitavisualizacao.this);
                alerta.setCancelable(false);
                alerta.setTitle("Não foi possivel carregar sua receita");
                alerta.setMessage("Tente novamente mais tarde");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
    }

    public void carregarAutor(Long id){
        //client retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //pegando
        usuarioAPIController.getUsuarioForReceita(id, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                nome_autor.setText(usuario.getNome_usuario());
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(receitavisualizacao.this);
                alerta.setCancelable(false);
                alerta.setTitle("Algo de errado não está certo...");
                alerta.setMessage("autor da receita não encontrado");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
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
    public void verMinhasReceitas(View view){
        if(emailUSUARIO != null){
            Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
        }else{
            Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
        }
    }
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
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
    /*public void voltar(View view){
        Intent outraTela = new Intent(getApplicationContext(), resultadopesquisa.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("Pesquisa", pesquisaString);
        startActivity(outraTela);
        finish();
    }*/
}