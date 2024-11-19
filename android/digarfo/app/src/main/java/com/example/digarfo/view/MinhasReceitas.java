package com.example.digarfo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class MinhasReceitas extends AppCompatActivity {
    String emailUSUARIO;
    RecyclerView recycler_view;
    List<Receita> lista_receitas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_minhas_receitas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //pegando email do usuario
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        //acesso ao recycler view
        recycler_view = findViewById(R.id.minhas_rct);
        //configurando recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);
        recycler_view.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        //chamando função para carregar as receitas
        pegarRct();
    }
    //pegando as receitas do usuario
    public void pegarRct(){
        //client retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        receitaAPIController.getReceitaForUser(emailUSUARIO, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {}

            @Override
            public void onSuccessList(List<Receita> receitas) {
                lista_receitas.clear();
                lista_receitas.addAll(receitas);
                //adapter
                Adapter_Minhas_Receitas adapter_minhas_receitas = new Adapter_Minhas_Receitas(lista_receitas);
                //recycla viewsssss
                recycler_view.setAdapter(adapter_minhas_receitas);
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MinhasReceitas.this);
                alerta.setCancelable(false);
                alerta.setTitle("Erro...");
                alerta.setMessage("Erro ao exibir suas receitas...");
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
            }
        });
    }
    //funcoes do rodape
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
    public void irparaperfil(View view){
        Intent outraTela = new Intent(getApplicationContext(), editarperfil.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
    public void minhasReceitas(View view){
        Toast.makeText(this, "Você já está aqui :)", Toast.LENGTH_SHORT).show();
    }
    public void verReceitaMinha(View view){
        //mandar id da receita junto
        TextView id = view.findViewById(R.id.id_rct_my);
        String idStg = id.getText().toString();
        //lets go the other interface(recipe visualization)
        Intent outraTela = new Intent(getApplicationContext(), Visualizar_My_Receita.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("id_rct", idStg);
        startActivity(outraTela);
        finish();
    }
}