package com.example.digarfo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ReceitaAPIController;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.model.Receita;

import java.util.List;

public class resultadopesquisa extends AppCompatActivity {
    RecyclerView recycler_view_resultado_pesquisa;//recycler view

    List<Receita> lista_de_receitas;

    String pesquisaString;//valor da pesquisa
    String emailUSUARIO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultadopesquisa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //oque pesquisou?
        String pesquisaGuardada = getIntent().getStringExtra("Pesquisa");
        pesquisaString = pesquisaGuardada;
        //quem tá logado?
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        //obtem acesso ao elemento grafico recycler view
        this.recycler_view_resultado_pesquisa = findViewById(R.id.receitas_pesquisadas);
        //configurar recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_resultado_pesquisa.setLayoutManager(layoutManager);
        recycler_view_resultado_pesquisa.setHasFixedSize(true);
        recycler_view_resultado_pesquisa.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        //metodo para pegar lista de receitas pela pesquisa
        buscar_receitas();
    }
    //pegar receita com base na pesquisa
    public void buscar_receitas(){

        if (pesquisaString != null){
            buscar_receita_pesquisada();
        }else{
            buscar_all_receitas();
        }

    }
    //caso ele clique em mais receitas, por exemplo vem nulo eai busca tds receitas, ou se ele n digitar nd e clicar
    public void buscar_all_receitas(){
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        //chamando metodo
        receitaAPIController.BuscarReceitas(new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {

            }

            @Override
            public void onSuccessList(List<Receita> receitas) {
                //fazer aparecer no recycler view a lista
                lista_de_receitas.addAll(receitas);
                AdapterReceita adapterReceita = new AdapterReceita(lista_de_receitas);
                recycler_view_resultado_pesquisa.setAdapter(adapterReceita);
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(resultadopesquisa.this);
                alerta.setCancelable(false);
                alerta.setTitle("Erro...");
                alerta.setMessage("Tente pesquisar novamente mais tarde...");
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
            }
        });
    }
    public void buscar_receita_pesquisada(){

    }
    //se logado vai senao nao
    public void irparafavoritos(View view){
        if (emailUSUARIO != null){
            Intent outraTela = new Intent(getApplicationContext(), favoritoslogado.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
        }else{
            Toast.makeText(this, "Você ainda não esta logado, faça login para ter favoritos", Toast.LENGTH_SHORT).show();
        }
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
    //se logado vai senao nao
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
    public void botaohome(View view){//botao home
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
}