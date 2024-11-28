package com.example.admdigarfo.view;

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

import com.example.admdigarfo.R;
import com.example.admdigarfo.api.ReceitaAPIController;
import com.example.admdigarfo.api.RetrofitClient;
import com.example.admdigarfo.model.Receita;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class receitas_avaliar extends AppCompatActivity {
    RecyclerView recycler_view;
    List<Receita> lista_receitas = new ArrayList<>();
    String emailADM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_receitas_avaliar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //quem tá logado?
        String emailGuardado = getIntent().getStringExtra("Email");
        emailADM = emailGuardado;
        //acesso ao recycler view
        recycler_view = findViewById(R.id.recycler);
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
        receitaAPIController.getReceitasNaoApdv(new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {

            }

            @Override
            public void onSuccessList(List<Receita> receitas) {
                lista_receitas.clear();
                lista_receitas.addAll(receitas);
                //adapter
                Adapter_Receitas_Avaliar adapter_receitas_avaliar = new Adapter_Receitas_Avaliar(lista_receitas);
                //recycla viewsssss
                recycler_view.setAdapter(adapter_receitas_avaliar);
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(receitas_avaliar.this);
                alerta.setCancelable(false);
                alerta.setTitle("Erro...");
                alerta.setMessage("Erro ao exibir receitas não aprovadas...");
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
            }
        });
    }

    //função onclick ir p receita clicada
    /*public void funcver_receita(View view){
        TextView id = findViewById(R.id.id_rct);
        String id_rct = id.getText().toString();
        Intent outraTela = new Intent(getApplicationContext(), ver_receita.class);
        outraTela.putExtra("Email", emailADM);
        outraTela.putExtra("id_rct", id_rct);
        startActivity(outraTela);
        finish();
    }*/
}