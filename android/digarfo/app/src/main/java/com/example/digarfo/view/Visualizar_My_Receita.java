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

public class Visualizar_My_Receita extends AppCompatActivity {
    String emailUSUARIO;
    Long id_long;
    //ELEMENTOS DA PAGINA
    TextView nome_receita;
    TextView autor_receita;
    TextView categoria;
    TextView tempo;
    TextView custo;
    TextView ingredientes;
    TextView modo_prep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visualizar_my_receita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //elementos
        nome_receita = findViewById(R.id.nome_RECEITA);
        autor_receita = findViewById(R.id.autor_nameeeee);
        categoria = findViewById(R.id.CATEGORIA);
        custo = findViewById(R.id.CUSTO);
        tempo = findViewById(R.id.TEMPO);
        ingredientes = findViewById(R.id.INGREDIENTES);
        modo_prep = findViewById(R.id.MODO_PREP);
        //email usuario
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        //pegar id da receita
        String id_guardado = getIntent().getStringExtra("id_rct");
        id_long = Long.parseLong(id_guardado);
        //chamando metodo para buscar minha receita
        buscar_minha_receita(id_long);
    }
    //buscarMinhaReceitaClicadaPorId
    public void buscar_minha_receita(Long id_receita){
        //RetroFitClient
        RetrofitClient retrofitClient = new RetrofitClient();
        //Api Controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        //chamando metodo
        receitaAPIController.getReceita(id_receita, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {
                nome_receita.setText(receita.getNome_receita());
                categoria.setText(receita.getCategoria());
                custo.setText(receita.getCusto());
                ingredientes.setText(receita.getIngredientes());
                modo_prep.setText(receita.getModo_prep());
                carregarAutor(receita.getId_receita());
            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Visualizar_My_Receita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Não foi possivel carregar sua receita");
                alerta.setMessage("Tente novamente mais tarde");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
    }
    //pegar autor
    public void carregarAutor(Long id){
        //client retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //pegando
        usuarioAPIController.getUsuarioForReceita(id, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                autor_receita.setText(usuario.getNome_usuario());
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Visualizar_My_Receita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Algo de errado não está certo...");
                alerta.setMessage("autor da receita não encontrado");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
    }
    //funcoes rodape
    public void irparaperfil(View view){
        Intent outraTela = new Intent(getApplicationContext(), editarperfil.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
    public void irparainserir(View view){
        Intent outraTela = new Intent(getApplicationContext(), escreverreceita.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
    public void verMinhasReceitas(View view){
        Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
}