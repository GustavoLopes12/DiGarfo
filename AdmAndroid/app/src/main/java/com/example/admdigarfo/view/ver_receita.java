package com.example.admdigarfo.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.admdigarfo.R;
import com.example.admdigarfo.api.ReceitaAPIController;
import com.example.admdigarfo.api.RetrofitClient;
import com.example.admdigarfo.api.UsuarioAPIController;
import com.example.admdigarfo.model.Receita;
import com.example.admdigarfo.model.Usuario;

import java.util.List;

import okhttp3.ResponseBody;

public class ver_receita extends AppCompatActivity {

    String emailUSUARIO;
    Long id_long;//teste

    //coisas do layout
    TextView nome_autor;
    TextView nome_receita;
    TextView ingredientes;
    TextView modo_prep;
    TextView custo;
    TextView categoria;
    TextView tempo;

    //TextView descricao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_receita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //*MEXER P PEGAR NOM AUTOR(?)-precisa desse cdg?
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
       // descricao = findViewById(R.id.descricao_autor);
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
            public void onSuccess(ResponseBody responseBody) {

            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ver_receita.this);
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
                //descricao.setText(usuario.getDescricao());
            }
            @Override
            public void onSuccess(ResponseBody responseBody) {
            }
            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ver_receita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Algo de errado não está certo...");
                alerta.setMessage("autor da receita não encontrado");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
    }




}