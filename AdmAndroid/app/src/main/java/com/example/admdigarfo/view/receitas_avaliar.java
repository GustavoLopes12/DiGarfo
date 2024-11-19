package com.example.admdigarfo.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.LinearLayout;

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

public class receitas_avaliar extends AppCompatActivity {
    //classe do recyclerview

    RecyclerView recycler_view;
    List<Receita> lista_receitas = new ArrayList<>();

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

        //acesso ao recycler view
        recycler_view = findViewById(R.id.rcts_naoaprov);
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
        receitaAPIController.getReceitasNaoApdv( new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {

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



}