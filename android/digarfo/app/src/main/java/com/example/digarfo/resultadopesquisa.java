package com.example.digarfo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class resultadopesquisa extends AppCompatActivity {
    private String nome_rct;
    private float rating;
    private int imagemId; //n° que identifica a imagem

    //construtor
    public resultadopesquisa(String nome_rct, float rating, int imagemId){
        this.nome_rct = nome_rct;
        this.rating = rating;
        this.imagemId = imagemId;
    }

    //getters
    public String getNome_rct() {
        return nome_rct;
    }
    public float getRating() {
        return rating;
    }
    public int getImagemId() {
        return imagemId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultadopesquisa);



        //------------------------------------------------------------
        //******** código que ja estava aqui
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //------------------------------------------------------------
    }
}