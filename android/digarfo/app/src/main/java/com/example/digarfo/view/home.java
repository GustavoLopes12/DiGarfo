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
    TextView tv_receita_bd_dois;//nome
    TextView tv_receita_bd_tres;//nome
    TextView tv_receita_bd_quatro;//nome
    TextView tv_autor_receita_bd;
    TextView tv_autor_receita_bd_dois;
    TextView tv_autor_receita_bd_tres;
    TextView tv_autor_receita_bd_quatro;
    Long id_receita_bd;

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
            receitaAPIController.getReceita(id_receita_bd, new ReceitaAPIController.ResponseCallback() {

                @Override
                public void onSuccess(Receita receita) {
                    tv_receita_bd.setText(receita.getNome_receita());
                }

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("receita nao encontrada");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
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
                    tv_autor_receita_bd.setText("Por: " + usuario.getNome_usuario());
                }
                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("autor receita nao encontrado");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
                }
            });
        } else if (id_receita_bd == 2L) {
            //-PEGANDO NOME RECEITA

            RetrofitClient retrofitClient = new RetrofitClient();
            //receita api controller
            ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
            receitaAPIController.getReceita(id_receita_bd, new ReceitaAPIController.ResponseCallback() {

                @Override
                public void onSuccess(Receita receita) {
                    tv_receita_bd_dois.setText(receita.getNome_receita());
                }

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("receita nao encontrada");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
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
                    tv_autor_receita_bd_dois.setText("Por: " + usuario.getNome_usuario());
                }
                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("autor receita nao encontrado");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
                }
            });
        } else if (id_receita_bd == 3L) {
            //-PEGANDO NOME RECEITA

            RetrofitClient retrofitClient = new RetrofitClient();
            //receita api controller
            ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
            receitaAPIController.getReceita(id_receita_bd, new ReceitaAPIController.ResponseCallback() {

                @Override
                public void onSuccess(Receita receita) {
                    tv_receita_bd_tres.setText(receita.getNome_receita());
                }

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("receita nao encontrada");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
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
                    tv_autor_receita_bd_tres.setText("Por: " + usuario.getNome_usuario());
                }
                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("autor receita nao encontrado");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
                }
            });
        } else if (id_receita_bd == 4L) {
            //-PEGANDO NOME RECEITA

            RetrofitClient retrofitClient = new RetrofitClient();
            //receita api controller
            ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
            receitaAPIController.getReceita(id_receita_bd, new ReceitaAPIController.ResponseCallback() {

                @Override
                public void onSuccess(Receita receita) {
                    tv_receita_bd_quatro.setText(receita.getNome_receita());
                }

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("receita nao encontrada");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
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
                    tv_autor_receita_bd_quatro.setText("Por: " + usuario.getNome_usuario());
                }
                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(home.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("autor receita nao encontrado");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
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
        irParaPesquisaCategoria("salgados");
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
}