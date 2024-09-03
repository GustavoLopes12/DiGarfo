package com.example.digarfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class resultadopesquisa extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultadopesquisa);
<<<<<<< HEAD

        //******** código que ja estava aqui
=======
>>>>>>> 2570cebc5d946515b23128a7eb35d2699a45ac4d
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
<<<<<<< HEAD
        //------------------------------------------------------------



        //------------------------------------------------------------

=======
    }
    public void botaohome(View view){//botao home
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
>>>>>>> 2570cebc5d946515b23128a7eb35d2699a45ac4d
    }
}