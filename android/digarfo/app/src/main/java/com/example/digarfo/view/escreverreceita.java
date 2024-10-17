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
    //ADD NA LINHA ACIMA:
    //ApiConnection conexao;
    //ApiConnection.UsuarioCallback usuarioCallback;

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

        titulo = findViewById(R.id.title_input_rct);
        radioGroup_custo = findViewById(R.id.radioCusto);
        categ_spinner = findViewById(R.id.categ_spinner);
        radioGroup_dificul = findViewById(R.id.radioDific);
        tempo = findViewById(R.id.time_input_rct);
        ingredientes = findViewById(R.id.ingred_input_rct);
        modo_prep = findViewById(R.id.prep_input_rct);
        //img = findViewById(R.id.categ_spinner);
        //aprovacao = findViewById(R.id.categ_spinner);
       //categ_spinner = findViewById(R.id.categ_spinner);

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
    //************************ PARTE DE BAIXO ********************************************
    //************************************************************************************
    boolean usuariologado = true; //variavel usada em todas as funções
    public void irparaperfil(View view){
        if(usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), editarperfil.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você não está logado, faça login para editar seu perfil!!!", Toast.LENGTH_SHORT).show();
            Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(outraTela);
        }
    }
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
    }
    public void irparainserir(View view){
        if(usuariologado){
            Toast.makeText(this, "Você já está em escrever receitas :)", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
        }

    }
    public void irparafavoritos(View view){
        if (usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), favoritoslogado.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você ainda não esta logado", Toast.LENGTH_SHORT).show();
        }
    }
    //************************ FIM-PARTE DE BAIXO ********************************************
    //************************************************************************************

    //*********************** BOTÃO ENVIAR RECEITA-FIZ MAS COMENTEI***************************
    //******************************************************************************
    /*

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
        //*****
        String radioGroup_custoString = custoButtonSelecionado.getText().toString();  // convertendo p string
        //FIM-RADIOGROUP CUSTO----------------------------

        //RADIOGROUP DIFICULDADE----------------------------
        int idRadioDificul = radioGroup_dificul.getCheckedRadioButtonId();
        RadioButton dificulButtonSelecionado = findViewById(idRadioDificul);
        //*****
        String radioGroup_dificulString = dificulButtonSelecionado.getText().toString();
        //FIM-RADIOGROUP DIFICULDADE----------------------------

        Receita receita = new Receita(tituloString, radioGroup_custoString,
                categ_spinnerString, radioGroup_dificulString, tempoString,
                ingredientesString, modo_prepString);

        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        //cadastro
        receitaAPIController.enviarReceita(tituloString, radioGroup_custoString,
                categ_spinnerString, radioGroup_dificulString, tempoString,
                ingredientesString, modo_prepString, null, false,
                new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(enviarReceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Receita Enviada");
                alerta.setMessage("Sua receita foi enviada com sucesso");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
                titulo.setText(null);
                tempo.setText(null);
                ingredientes.setText(null);
                modo_prep.setText(null);
                //categ_spinner.setText(null);//!!poe o que?
                //radioGroup_custo.setText(null);//!!
                //radioGroup_dificul.setText(null);//!!
                Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(outraTela);

            }
            @Override
            public void onFailure(Throwable t) {
                androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(enviarReceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Conexão Falhou, não é possivel realizar cadastro, tente novamente");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
                titulo.setText(null);
                tempo.setText(null);
                ingredientes.setText(null);
                modo_prep.setText(null);
            }
        });
    }
         /*-------------FIM-MODELO CADASTRAR----------------------*/

    }

    //colocar a categoria abaixo

        /* INICO

        receitaAPIController.novaReceita(  titulo, custo, null, null, tempo, ingredientes, modo_prep, null, false, new ReceitaAPIController.ResponseCallback(){

            @Override
            public void onSuccess(Receita receita) {
                //textView.setText(user_list.getSupport().getText());
                AlertDialog.Builder alerta = new AlertDialog.Builder(escreverreceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Receita Criada");
                //alerta.setMessage(receita.toString());
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
            @Override
            public void onFailure(Throwable t) {
                //Log.e("UserList", "Erro ao inserir receita", t);
                AlertDialog.Builder alerta = new AlertDialog.Builder(escreverreceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Receita Não Criada");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Falhou",null);
                alerta.create().show();
            }
        });

        //conexao.criarUsuario(user, usuarioCallback);----!!! CRIAR NO APCONNECTION !!!------
       // Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
       // startActivity(outraTela);

    }
    FIM
         */
    //*********************** FIM-BOTÃO ENVIAR RECEITA ***********************
    //******************************************************************************


/* ------------------------CODIGO ORIGINAL-----------------------
package com.example.digarfo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;
import com.example.digarfo.model.Receita;

public class escreverreceita extends AppCompatActivity {
    //ApiConnection conexao;
    //ApiConnection.UsuarioCallback usuarioCallback;

    //img
    EditText titulo; //certo
    EditText tempo; //certo
   //dificuldade
    EditText ingredientes; //certo
    EditText modo_prep; //certo
    //dificuldade-----!!!!! colocar radio (bolinhas opção, pq ta como img) !!!!!!! -----
    EditText custo;
    EditText categoria;

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

        // Referenciar o Spinner no layout
        Spinner spinner = findViewById(R.id.my_spinner);

        // Criar uma lista de opções
        String[] options = {"Nenhum", "Doces", "Salgados", "Saladas"};

        // Criar um ArrayAdapter usando a lista de opções e o layout padrão do Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplicar o ArrayAdapter ao Spinner
        spinner.setAdapter(adapter);

        // Definir o item selecionado como "Nenhum"
        spinner.setSelection(0); // Seleciona o primeiro item, que é "Nenhum"


        //img
        titulo =  findViewById(R.id.email);
        tempo =  findViewById(R.id.time_input_rct);
        ingredientes = findViewById(R.id.ingred_input_rct);
        modo_prep = findViewById(R.id.prep_input_rct);
        //dificuldade
        custo = findViewById(R.id.custo_input_rct);
    }

    //*********************** FAZENDO CAMPOS FUNCIONAREM ***************************
    //******************************************************************************

    //-botao enviar receita
    public void enviar_rct(View view){
        Receita receita = new Receita(null, titulo.getText().toString(), custo.getText().toString(), categoria.getText().toString(), null, tempo.getText().toString(),
                ingredientes.getText().toString(), modo_prep.getText().toString(), null, true);

        //conexao.criarUsuario(user, usuarioCallback);----!!! CRIAR NO APCONNECTION !!!------
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(outraTela);

    }
    //-FIM-botao enviar receita

    //*********************** FIM-FAZENDO CAMPOS FUNCIONAREM ***********************
    //******************************************************************************


    //************************ PARTE DE BAIXO ********************************************
    //************************************************************************************
    boolean usuariologado = true; //variavel usada em todas as funções
    public void irparaperfil(View view){
        if(usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), editarperfil.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você não está logado, faça login para editar seu perfil!!!", Toast.LENGTH_SHORT).show();
            Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(outraTela);
        }
    }
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
    }
    public void irparainserir(View view){
        if(usuariologado){
            Toast.makeText(this, "Você já está em escrever receitas :)", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
        }

    }
    public void irparafavoritos(View view){
        if (usuariologado){
            Intent outraTela = new Intent(getApplicationContext(), favoritoslogado.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você ainda não esta logado", Toast.LENGTH_SHORT).show();
        }
    }
    //************************ FIM-PARTE DE BAIXO ********************************************
    //************************************************************************************
}

 ------------------------FIM-CODIGO ORIGINAL-----------------------*/