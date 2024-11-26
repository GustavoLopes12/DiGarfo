package com.example.digarfo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;

public class receitavisualizacao extends AppCompatActivity {
   // String pesquisaString;//valor da pesquisa
    String emailUSUARIO;
    Long id_long;

    //coisas do layout
    ImageView img;
    TextView nome_autor;
    TextView nome_receita;
    TextView ingredientes;
    TextView modo_prep;
    TextView custo;
    TextView categoria;
    TextView tempo;

    TextView descricao;
    ImageView exibicao_img;
    TextView dif2;



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
        dif2 = findViewById(R.id.dif2);
        nome_autor = findViewById(R.id.autor_name);
        nome_receita = findViewById(R.id.nome_r);
        ingredientes = findViewById(R.id.ingredientes);
        modo_prep = findViewById(R.id.modo_prep);
        custo = findViewById(R.id.custo);
        categoria = findViewById(R.id.categoria);
        img = findViewById(R.id.imageView);
        tempo = findViewById(R.id.tempo);
        descricao = findViewById(R.id.descricao_autor);
        exibicao_img = findViewById(R.id.imageView6);
        carregar_rct();
    }
    //carregar img do autor
    //aparecer imagem
    public void pegar_img(String email){
        RetrofitClient retrofitClient = new RetrofitClient();
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        usuarioAPIController.buscarImagem(email, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    // Converta a resposta para Bitmap
                    InputStream inputStream = responseBody.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if(bitmap != null){
                        // Exiba o Bitmap em uma ImageView
                        img.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    Log.e("Erro", "Erro ao processar imagem: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Erro", "Falha ao buscar imagem: " + t.getMessage());
            }
        });
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
                dif2.setText(receita.getDificuldade());
                carregarAutor(receita.getId_receita());
                carregarIMG(receita.getId_receita());
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {}

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
                descricao.setText(usuario.getDescricao());
                pegar_img(usuario.getEmail());
            }
            @Override
            public void onSuccess(ResponseBody responseBody) {
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

    //pegar img da receita
    public void carregarIMG(Long id){
        RetrofitClient retrofitClient = new RetrofitClient();
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        receitaAPIController.buscarImagem(id, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    // Converta a resposta para Bitmap
                    InputStream inputStream = responseBody.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if(bitmap != null){
                        // Exiba o Bitmap em uma ImageView
                        exibicao_img.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    Log.e("Erro", "Erro ao processar imagem: " + e.getMessage());
                }
            }

            @Override
            public void onSuccess(Receita receita) {

            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Erro", "Falha ao buscar imagem: " + t.getMessage());
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