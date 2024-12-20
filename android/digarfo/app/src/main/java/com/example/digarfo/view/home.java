package com.example.digarfo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class home extends AppCompatActivity {
    String texto = "Não encontrada a receita";
    String emailUSUARIO;
    EditText valorPesquisa;
    String valuePesquisa;

   String idrct1;
   String idrct2;
   String idrct3;
   String idrct4;

    TextView tv_receita_bd;//nome
    TextView tv_receita_bd_dois;//nome
    TextView tv_receita_bd_tres;//nome
    TextView tv_receita_bd_quatro;//nome
    TextView tv_autor_receita_bd;
    TextView tv_autor_receita_bd_dois;
    TextView tv_autor_receita_bd_tres;
    TextView tv_autor_receita_bd_quatro;

    //imgs
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;

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
        tv_receita_bd_dois = findViewById(R.id.recebe_receita_dois);
        tv_receita_bd_tres = findViewById(R.id.recebe_receita_tres);
        tv_receita_bd_quatro = findViewById(R.id.recebe_receita_quatro);
        tv_autor_receita_bd = findViewById(R.id.autor_receita);
        tv_autor_receita_bd_dois = findViewById(R.id.autor_receita_dois);
        tv_autor_receita_bd_tres = findViewById(R.id.autor_receita_tres);
        tv_autor_receita_bd_quatro = findViewById(R.id.autor_receita_quatro);

        //imgs
        img1 = findViewById(R.id.id_home_img_one);
        img2 = findViewById(R.id.id_home_img_two);
        img3 = findViewById(R.id.id_home_img_tree);
        img4 = findViewById(R.id.id_home_img_four);



        //chamar mostrarReceita aqui 4x, cada uma com 1 id
        mostrarReceita(1L);
        mostrarReceita(2L);
        mostrarReceita(3L);
        mostrarReceita(4L);


    }//fim oncreate

    //-----------------------------------------------------

    public void mostrarReceita(Long id_receita_bd){
        if(id_receita_bd == 1L){
            //-PEGANDO NOME RECEITA

            //cliente retrofit
            RetrofitClient retrofitClient = new RetrofitClient();

            //receita api controller
            ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
            receitaAPIController.getReceitaAprovID(id_receita_bd, new ReceitaAPIController.ResponseCallback() {

                @Override
                public void onSuccess(Receita receita) {
                    if(receita != null){
                        tv_receita_bd.setText(receita.getNome_receita());
                        idrct1 = receita.getId_receita().toString();
                        carregarIMG(receita.getId_receita(), img1);
                    }else{
                        tv_receita_bd.setText(texto);
                        idrct1 = "0";
                    }
                }

                @Override
                public void onSuccess(ResponseBody responseBody) {}

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro", "Falha ao buscar receita: " + t.getMessage());
                }
            });

            //-PEGANDO AUTOR RECEITA

            //client retrofit
            RetrofitClient retrofitClient2 = new RetrofitClient();
            //api controller
            UsuarioAPIController usuarioAPIController2 = new UsuarioAPIController(retrofitClient2);
            // Bora pegar esse usuário
            usuarioAPIController2.getUsuarioForReceita(id_receita_bd, new UsuarioAPIController.ResponseCallback() {
                @Override
                public void onSuccess(Usuario usuario) {
                    if(usuario != null){
                        tv_autor_receita_bd.setText("Por: " + usuario.getNome_usuario());
                    }else{
                        tv_autor_receita_bd.setText("Não encontrado o autor");
                    }
                }
                @Override
                public void onSuccess(ResponseBody responseBody) {
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro", "Falha ao buscar autor da receita: " + t.getMessage());
                }
            });
        } else if (id_receita_bd == 2L) {
            //-PEGANDO NOME RECEITA

            RetrofitClient retrofitClient = new RetrofitClient();
            //receita api controller
            ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
            receitaAPIController.getReceitaAprovID(id_receita_bd, new ReceitaAPIController.ResponseCallback() {

                @Override
                public void onSuccess(Receita receita) {
                    if(receita != null){
                        tv_receita_bd_dois.setText(receita.getNome_receita());
                        idrct2 = receita.getId_receita().toString();
                        carregarIMG(receita.getId_receita(), img2);
                    }else{
                        tv_receita_bd_dois.setText(texto);
                        idrct2 = "0";
                    }
                }

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onSuccess(ResponseBody responseBody) {}

                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro", "Falha ao buscar receita: " + t.getMessage());
                }
            });
            //-PEGANDO AUTOR RECEITA

            //client retrofit
            RetrofitClient retrofitClient2 = new RetrofitClient();
            //api controller
            UsuarioAPIController usuarioAPIController2 = new UsuarioAPIController(retrofitClient2);
            // Bora pegar esse usuário
            usuarioAPIController2.getUsuarioForReceita(id_receita_bd, new UsuarioAPIController.ResponseCallback() {
                @Override
                public void onSuccess(Usuario usuario) {
                    if(usuario != null){
                        tv_autor_receita_bd_dois.setText("Por: " + usuario.getNome_usuario());
                    }else{
                        tv_autor_receita_bd_dois.setText("Não encontrado o autor");
                    }
                }
                @Override
                public void onSuccess(ResponseBody responseBody) {
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro", "Falha ao buscar autor da receita: " + t.getMessage());
                }
            });
        } else if (id_receita_bd == 3L) {
            //-PEGANDO NOME RECEITA

            RetrofitClient retrofitClient = new RetrofitClient();
            //receita api controller
            ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
            receitaAPIController.getReceitaAprovID(id_receita_bd, new ReceitaAPIController.ResponseCallback() {

                @Override
                public void onSuccess(Receita receita) {
                    if(receita != null) {
                        tv_receita_bd_tres.setText(receita.getNome_receita());
                        idrct3 = receita.getId_receita().toString();
                        carregarIMG(receita.getId_receita(), img3);
                    }else{
                        tv_receita_bd_tres.setText(texto);
                        idrct3 = "0";
                    }
                }

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onSuccess(ResponseBody responseBody) {}

                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro", "Falha ao buscar receita: " + t.getMessage());
                }
            });
            //-PEGANDO AUTOR RECEITA

            //client retrofit
            RetrofitClient retrofitClient2 = new RetrofitClient();
            //api controller
            UsuarioAPIController usuarioAPIController2 = new UsuarioAPIController(retrofitClient2);
            // Bora pegar esse usuário
            usuarioAPIController2.getUsuarioForReceita(id_receita_bd, new UsuarioAPIController.ResponseCallback() {
                @Override
                public void onSuccess(Usuario usuario) {
                    if(usuario != null) {
                        tv_autor_receita_bd_tres.setText("Por: " + usuario.getNome_usuario());
                    }else{
                        tv_autor_receita_bd_tres.setText("Não encontrado o autor");
                    }
                }
                @Override
                public void onSuccess(ResponseBody responseBody) {
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro", "Falha ao buscar autor: " + t.getMessage());
                }
            });
        } else if (id_receita_bd == 4L) {
            //-PEGANDO NOME RECEITA

            RetrofitClient retrofitClient = new RetrofitClient();
            //receita api controller
            ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
            receitaAPIController.getReceitaAprovID(id_receita_bd, new ReceitaAPIController.ResponseCallback() {

                @Override
                public void onSuccess(Receita receita) {
                    if(receita != null) {
                        tv_receita_bd_quatro.setText(receita.getNome_receita());
                        idrct4 = receita.getId_receita().toString();
                        carregarIMG(receita.getId_receita(), img4);
                    }else{
                        tv_receita_bd_quatro.setText(texto);
                        idrct4 = "0";
                    }
                }

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onSuccess(ResponseBody responseBody) {}

                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro", "Falha ao buscar receita: " + t.getMessage());
                }
            });
            //-PEGANDO AUTOR RECEITA

            //client retrofit
            RetrofitClient retrofitClient2 = new RetrofitClient();
            //api controller
            UsuarioAPIController usuarioAPIController2 = new UsuarioAPIController(retrofitClient2);
            // Bora pegar esse usuário
            usuarioAPIController2.getUsuarioForReceita(id_receita_bd, new UsuarioAPIController.ResponseCallback() {
                @Override
                public void onSuccess(Usuario usuario) {
                    if(usuario != null) {
                        tv_autor_receita_bd_quatro.setText("Por: " + usuario.getNome_usuario());
                    }else{
                        tv_autor_receita_bd_quatro.setText("Não encontrado o autor");
                    }
                }
                @Override
                public void onSuccess(ResponseBody responseBody) {
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro", "Falha ao buscar autor da receita: " + t.getMessage());
                }
            });
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
            alerta.setCancelable(false);
            alerta.setTitle("Algo de errado não está certo...");
            alerta.setMessage("apenas receitas com os IDs 1, 2, 3 ou 4 dão certo!!");
            alerta.setNegativeButton("Ok",null);
            alerta.create().show();
        }

    }

    //pegar img da receita
    public void carregarIMG(Long id, ImageView img) {
        // Define um fundo temporário (opcional) enquanto carrega
        img.setImageResource(R.drawable.placeholder); // Um drawable de carregamento ou fundo padrão

        RetrofitClient retrofitClient = new RetrofitClient();
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);

        receitaAPIController.buscarImagem(id, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    // Converte a resposta para Bitmap
                    InputStream inputStream = responseBody.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap != null) {
                        // Exibe o Bitmap e substitui o fundo
                        img.setImageBitmap(bitmap);
                    } else {
                        // Caso o Bitmap seja nulo, mantenha o placeholder
                        img.setImageResource(R.drawable.error_image); // Opcional: um drawable para erro
                    }
                } catch (Exception e) {
                    Log.e("Erro", "Erro ao processar imagem: " + e.getMessage());
                    img.setImageResource(R.drawable.error_image); // Opcional: drawable para erros
                }
            }

            @Override
            public void onSuccess(Receita receita) {}

            @Override
            public void onSuccessList(List<Receita> receitas) {}

            @Override
            public void onFailure(Throwable t) {
                Log.e("Erro", "Falha ao buscar imagem: " + t.getMessage());
                Toast.makeText(home.this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
                img.setImageResource(R.drawable.error_image); // Opcional: drawable para erro
            }
        });
    }



    //-----------------------------------------------------

    public void irpararesultadopesq(View view) {
        valuePesquisa = valorPesquisa.getText().toString();
        Intent outraTela = new Intent(getApplicationContext(), resultadopesquisa.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("Pesquisa", valuePesquisa);
        startActivity(outraTela);
        finish();
    }
    public void irParaPesquisaCategoria(String categoria) {
        Intent outraTela = new Intent(getApplicationContext(), resultadopesquisa.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("Pesquisa", categoria);
        startActivity(outraTela);
        finish();
    }

    // Métodos de onClick para cada categoria
    public void onClickDoces(View view) {
        irParaPesquisaCategoria("doces");
    }

    public void onClickSalgados(View view) {
        irParaPesquisaCategoria("salgada");
    }

    public void onClickSaudaveis(View view) {
        irParaPesquisaCategoria("saudaveis");
    }

    public void onClickBebidas(View view) {
        irParaPesquisaCategoria("bebidas");
    }

    public void onClickRapidas(View view) {
        irParaPesquisaCategoria("rapidas");
    }

    public void onClickMassas(View view) {
        irParaPesquisaCategoria("massas");
    }

    public void onClickFTM(View view) {
        irParaPesquisaCategoria("frutosdomar");
    }

    public void onClickAves(View view) {
        irParaPesquisaCategoria("aves");
    }

    public void onClickCV(View view) {
        irParaPesquisaCategoria("carnesV");
    }

    public void onClickOriental(View view) {
        irParaPesquisaCategoria("oriental");
    }

    public void onClickMexicana(View view) {
        irParaPesquisaCategoria("mexicana");
    }

    public void onClickSLVG(View view) {
        irParaPesquisaCategoria("saladaevege");
    }

    public void onClickSOPAS(View view) {
        irParaPesquisaCategoria("sopas");
    }

    //receitas sugeridas

   public void rct_view_one(View view){
        //lets go the other interface(recipe visualization)
        Intent outraTela = new Intent(getApplicationContext(), receitavisualizacao.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("id_rct", idrct1);
        startActivity(outraTela);
        finish();
    }

    public void rct_view_two(View view){
        //lets go the other interface(recipe visualization)
        Intent outraTela = new Intent(getApplicationContext(), receitavisualizacao.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("id_rct", idrct2);
        startActivity(outraTela);
        finish();
    }

    public void rct_view_tree(View view){
        //lets go the other interface(recipe visualization)
        Intent outraTela = new Intent(getApplicationContext(), receitavisualizacao.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("id_rct", idrct3);
        startActivity(outraTela);
        finish();
    }

    public void rct_view_four(View view){
        //lets go the other interface(recipe visualization)
        Intent outraTela = new Intent(getApplicationContext(), receitavisualizacao.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("id_rct", idrct4);
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
    public void verMinhasReceitas(View view){
        if(emailUSUARIO != null){
            Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
        }else{
            Toast.makeText(this, "Faça login para ver suas receitas :)", Toast.LENGTH_SHORT).show();
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
}