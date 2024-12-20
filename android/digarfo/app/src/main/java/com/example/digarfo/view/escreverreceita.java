package com.example.digarfo.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ReceitaAPIController;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;

public class escreverreceita extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String emailUSUARIO;
    String emailUSUARIO2;
    /*------ATRIBUTOS QUE VAO RECEBER O VALOR DO ID DOS ELEMENTOS  */
    EditText titulo;
    EditText tempo;
    EditText ingredientes;
    EditText modo_prep;
    Spinner categ_spinner;
    RadioGroup radioGroup_custo;
    RadioGroup radioGroup_dificul;
    //imagem
    ImageView img;
    Uri imageUri;
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
        //abrindo galeria quando clica na ft
        img = findViewById(R.id.ibagem);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Escolha sua Imagem"), 1);
            }
        });
        //pegando o email do usuario logado
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        emailUSUARIO2 = emailGuardado;
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
    }
    //fazer foto aparecer no lugar da foto padrao
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1){
                imageUri = data.getData(); // Armazena a URI da imagem selecionada
                img.setImageURI(imageUri);
            }
        }
    }
    //pegando paradas da foto
    private String obterNomeArquivo(Uri uri) {
        String resultado = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    resultado = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                }
            }
        }
        if (resultado == null) {
            resultado = uri.getLastPathSegment();
        }
        return resultado;
    }
    private File copiarArquivoParaCache(Uri uri) {
        try {
            // Obter o nome do arquivo a partir da URI
            String nomeArquivo = obterNomeArquivo(uri);
            if (nomeArquivo == null) {
                throw new IllegalStateException("Não foi possível obter o nome do arquivo.");
            }

            // Criar um arquivo temporário no cache
            File arquivoCache = new File(getCacheDir(), nomeArquivo);

            // Abrir os fluxos de entrada e saída
            try (InputStream inputStream = getContentResolver().openInputStream(uri);
                 OutputStream outputStream = new FileOutputStream(arquivoCache)) {

                // Copiar os dados
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }

            return arquivoCache; // Retorna o arquivo no cache
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao processar a imagem: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null; // Retorna null em caso de erro
        }
    }
    //obter caminho pela uri (codigo do antigo cadastro de usuario que iria imagem e n vai mais)
    /*private String getRealPathFromURI(Uri uri) {
      String[] projection = { MediaStore.Images.Media.DATA };
      Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
      if (cursor != null) {
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
           cursor.moveToFirst();
           String path = cursor.getString(columnIndex);
           cursor.close();
          return path;
       }
       return null;
    }*/
    //PEGA O VALOR SELECIONADO CATEGORIA
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
    //RADIOGROUP 1 DIFICULDADE
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
    //RADIOGROUP 2 CUSTO
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
    public void verMinhasReceitas(View view){
            Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
    }
    //enviar rct
    public void enviar_rct(View view) {
        if(imageUri != null){
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
            //pegando file de imagem
            // Processar imagem
            File imageFile = null;
            if (imageUri != null) {
                imageFile = copiarArquivoParaCache(imageUri); // Copiar para cache
            }
            //cliente retrofit
            RetrofitClient retrofitClient = new RetrofitClient();
            //api controller
            ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
            //cadastro com img nuovo
            Usuario user = new Usuario(emailUSUARIO);
            Receita receita = new Receita(tituloString, radioGroup_custoString, categ_spinnerString, radioGroup_dificulString, tempoString, ingredientesString, modo_prepString, false, null, user);
            receitaAPIController.criarReceitaComImagem(receita, imageFile, new ReceitaAPIController.ResponseCallback() {
                @Override
                public void onSuccess(ResponseBody responseBody) {

                }

                @Override
                public void onSuccess(Receita receita) {
                    Log.d("Sucesso: ", "sucesso ao criar receita");
                    Toast.makeText(escreverreceita.this, "Receita criada com sucesso ", Toast.LENGTH_SHORT).show();
                    Intent outraTela = new Intent(getApplicationContext(), home.class);
                    outraTela.putExtra("Email", emailUSUARIO);
                    startActivity(outraTela);
                }

                @Override
                public void onSuccessList(List<Receita> receitas) {

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("Erro", "erro ao criar receita: " + t);
                    androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(escreverreceita.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Conexão Falhou, não é possivel criar uma receita, tente novamente mais tarde");
                    alerta.setMessage(t.toString());
                    alerta.setNegativeButton("Voltar",null);
                    alerta.create().show();
                }
            });

            //cadastro sem imagem vecchio
        /*receitaAPIController.enviarReceita(tituloString, radioGroup_custoString, categ_spinnerString, radioGroup_dificulString, tempoString, ingredientesString, modo_prepString,false,null, emailUSUARIO, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(escreverreceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Receita Enviada");
                alerta.setMessage("Sua receita foi enviada com sucesso e agora irá para analise");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
                //limpando as coisas
                titulo.setText(null);
                tempo.setText(null);
                ingredientes.setText(null);
                modo_prep.setText(null);
                //categ_spinner.setText(null);//!!poe o que?
                //radioGroup_custo.setText(null);//!!
                //radioGroup_dificul.setText(null);//!!
                Intent outraTela = new Intent(getApplicationContext(), home.class);
                outraTela.putExtra("Email", emailUSUARIO2);
                startActivity(outraTela);
                finish();
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {}

            @Override
            public void onSuccessList(List<Receita> receitas) {
            }

            @Override
            public void onFailure(Throwable t) {
                androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(escreverreceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Conexão Falhou, não é possivel criar uma receita, tente novamente mais tarde");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
            }
        });*/
        }else{
            Toast.makeText(escreverreceita.this, "Insira uma imagem", Toast.LENGTH_SHORT).show();
        }
    }
}

