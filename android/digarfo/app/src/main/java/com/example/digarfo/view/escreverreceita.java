package com.example.digarfo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ReceitaAPIController;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.model.Receita;

public class escreverreceita extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String emailUSUARIO;

    /*------ATRIBUTOS QUE VAO RECEBER O VALOR DO ID DOS ELEMENTOS  */
    EditText titulo;
    EditText tempo;
    EditText ingredientes;
    EditText modo_prep;
    Spinner categ_spinner;
    RadioGroup radioGroup_custo;
    RadioGroup radioGroup_dificul;
    //ImageView img_receita;
    //img
    //aprov
    /*------FIM-ATRIBUTOS QUE VAO RECEBER O VALOR DO ID DOS ELEMENTOS  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_escreverreceita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //pegando o email do usuario logado
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        //outras relaçoes
        titulo = findViewById(R.id.title_input_rct);
        radioGroup_custo = findViewById(R.id.radioCusto);
        categ_spinner = findViewById(R.id.categ_spinner);
        radioGroup_dificul = findViewById(R.id.radioDific);
        tempo = findViewById(R.id.time_input_rct);
        ingredientes = findViewById(R.id.ingred_input_rct);
        modo_prep = findViewById(R.id.prep_input_rct);
        //sppiner
        String[] categorias = getResources().getStringArray(R.array.categorias);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Define o adaptador no Spinner
        categ_spinner.setAdapter(adapter);

        // Listener para o Spinner
        categ_spinner.setOnItemSelectedListener(this);

        //radiogroup
        /*
        radioGroup_dificul = findViewById(R.id.radioDific);
        radioGroup_custo = findViewById(R.id.radioCusto);

         */
    }
    //PEGA O VALOR SELECIONADO
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.categ_spinner){
            String valueFromSpinner = parent.getItemAtPosition(position).toString();//pegando o valor qu foi selecionado
            Toast.makeText(getApplicationContext(), "Item selecionado: " + valueFromSpinner, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //RADIOGROUP 1
    String dific_selecionada;// adicionei la no parametro do receitaapicontroller
    public void selecionarDificul(View view){
        int itemRadioGroupSelecionado = radioGroup_dificul.getCheckedRadioButtonId();//variavel que recebe o radiogroup

        if(itemRadioGroupSelecionado != -1){//algum radiobutton foi selecionado
            RadioButton rbDificSelecionada = findViewById(itemRadioGroupSelecionado); //variavel do tipo radiobutton encontra o radiogroup
            //**********************************************
            dific_selecionada =  rbDificSelecionada.getText().toString();//dificSelecionada recebe o valor escolhido/recebido
            //**********************************************
            Toast.makeText(this, dific_selecionada, Toast.LENGTH_SHORT).show();

        }else {
            // Nenhum RadioButton foi selecionado
            Toast.makeText(this, "Selecione uma dificuldade", Toast.LENGTH_SHORT).show();
        }
    }
    //FIM-RADIOGROUP 1
    //RADIOGROUP 2
    String custo_selecionado;
    public void selecionarCusto(View view){
        int itemRadioGroupSelecionado = radioGroup_custo.getCheckedRadioButtonId();//variavel que recebe o radiogroup

        if(itemRadioGroupSelecionado != -1){//algum radiobutton foi selecionado
            RadioButton rbCustoSelecionado = findViewById(itemRadioGroupSelecionado); //variavel do tipo radiobutton encontra o radiogroup
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            custo_selecionado =  rbCustoSelecionado.getText().toString();//dificSelecionada recebe o valor escolhido/recebido
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            Toast.makeText(this, custo_selecionado, Toast.LENGTH_SHORT).show();

        }else {
            // Nenhum RadioButton foi selecionado
            Toast.makeText(this, "Selecione um custo", Toast.LENGTH_SHORT).show();
        }
    }
    //FIM-RADIOGROUP 2
    public void irparaperfil(View view){
            Intent intent = new Intent(escreverreceita.this, editarperfil.class);
            intent.putExtra("Email", emailUSUARIO);
            startActivity(intent);
            finish();
    }
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
    public void irparainserir(View view){
            Toast.makeText(this, "Você já está em escrever receitas :)", Toast.LENGTH_SHORT).show();
    }
    public void irparafavoritos(View view){
            Intent intent = new Intent(escreverreceita.this, favoritoslogado.class);
            intent.putExtra("Email", emailUSUARIO);
            startActivity(intent);
            finish();
    }
    //enviar rct
    public void enviar_rct(View view) {
        //pegando valores para criação
        String tituloString = titulo.getText().toString();
        String tempoString = tempo.getText().toString();
        String ingredientesString = ingredientes.getText().toString();
        String modo_prepString = modo_prep.getText().toString();
        String categ_spinnerString = categ_spinner.getSelectedItem().toString();
        //RADIOGROUP CUSTO----------------------------
        int idRadioCusto = radioGroup_custo.getCheckedRadioButtonId();  // pegando ID do radiobutton
        RadioButton custoButtonSelecionado = findViewById(idRadioCusto);  // usando o ID p achar radiobutton
        String radioGroup_custoString = custoButtonSelecionado.getText().toString();  // convertendo p string
        //RADIOGROUP DIFICULDADE----------------------------
        int idRadioDificul = radioGroup_dificul.getCheckedRadioButtonId();
        RadioButton dificulButtonSelecionado = findViewById(idRadioDificul);
        String radioGroup_dificulString = dificulButtonSelecionado.getText().toString();
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        //cadastro
        receitaAPIController.enviarReceita(tituloString, radioGroup_custoString, categ_spinnerString, radioGroup_dificulString, tempoString, ingredientesString, modo_prepString, emailUSUARIO, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(escreverreceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Receita Enviada");
                alerta.setMessage("Sua receita foi enviada com sucesso e agora irá para analise");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
                //limpando as coisas
                //titulo.setText(null);
                //tempo.setText(null);
                //ingredientes.setText(null);
                //modo_prep.setText(null);
                //categ_spinner.setText(null);//!!poe o que?
                //radioGroup_custo.setText(null);//!!
                //radioGroup_dificul.setText(null);//!!
                Intent outraTela = new Intent(getApplicationContext(), home.class);
                //outraTela.putExtra("Email", emailUSUARIO);
                startActivity(outraTela);
                //finish();
            }
            @Override
            public void onFailure(Throwable t) {
                androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(escreverreceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Conexão Falhou, não é possivel criar uma receita, tente novamente mais tarde");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
                //limpando
                //titulo.setText(null);
                //tempo.setText(null);
                //ingredientes.setText(null);
               // modo_prep.setText(null);
            }
        });
    }
}

