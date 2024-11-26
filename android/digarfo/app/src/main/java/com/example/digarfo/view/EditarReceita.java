package com.example.digarfo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;

public class EditarReceita extends AppCompatActivity{
    //recebendo os extras
    String emailUSUARIO;
    Long id_long;
    String long_id;//p voltar
    //caracteristicas da receita que serao editadas
    Spinner spinner_categoria;
    String categoria; //vai receber a categoria e deixar selecionado no spinner quando usuario n clicar em nd
    EditText titulo_receita_ed;
    EditText ingredientes_receita_ed;
    EditText modo_de_preparo_ed;
    EditText tempo_receita_ed;
    String dificuldade;
    String custo;
    RadioGroup radioGroupDificuldades;

    RadioGroup radioGroupCusto;

    String name_img;

    //img
    ImageView img;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_receita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //recebendo os put extras
        //email usuario
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        //pegar id da receita
        String id_guardado = getIntent().getStringExtra("id_rct");
        id_long = Long.parseLong(id_guardado);
        //relacionando com os elementos no layout
        spinner_categoria = findViewById(R.id.spinner_editar_cat);
        titulo_receita_ed = findViewById(R.id.titulo_rct_editar);
        ingredientes_receita_ed = findViewById(R.id.ingredientes_editar);
        modo_de_preparo_ed = findViewById(R.id.modo_de_preparo_editar);
        tempo_receita_ed = findViewById(R.id.tempo_rct_editar);
        radioGroupDificuldades = findViewById(R.id.radio_group_difculdades_editar);
        radioGroupCusto = findViewById(R.id.custo_editar_radio);
        //carregando receita
        carregarRct(id_long);
        //spinner de categorias colocando as categorias
        /*String[] categorias = getResources().getStringArray(R.array.categorias);
        ArrayAdapter adapter = new ArrayAdapter(EditarReceita.this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Define o adaptador no Spinner
        spinner_categoria.setAdapter(adapter);
        // Define a categoria selecionada com base na variável categoria
        int posicaoCategoria = Arrays.asList(categorias).indexOf(categoria);
        if (posicaoCategoria >= 0) {
            spinner_categoria.setSelection(posicaoCategoria);
        }
        // configurando spinner
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lógica quando um item é selecionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Lógica para quando nenhum item está selecionado
            }
        });*/
        //img
        img = findViewById(R.id.imageView5);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Escolha sua Imagem"), 1);
            }
        });
    }
    //img
    //pegar img da receita
    public void carregarIMG(Long id){
        RetrofitClient retrofitClient = new RetrofitClient();
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        receitaAPIController.buscarImagem(id, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    // Converta a resposta para Bitmap
                    InputStream inputStream = responseBody.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if(bitmap != null){
                        // Exiba o Bitmap em uma ImageView
                        img.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    Log.e("Erro", "Erro ao processar imagem: " + e.getMessage());
                }
            }

            @Override
            public void onSuccess(Receita receita) {

            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Erro", "Falha ao buscar imagem: " + t.getMessage());
                Toast.makeText(EditarReceita.this, "Erro ao carregar imagem ou receita nao possui", Toast.LENGTH_SHORT).show();
            }
        });
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
    //categoria
    private void atualizarSpinnerComCategoria() {
        String[] categorias = getResources().getStringArray(R.array.categorias);

        // Cria o adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditarReceita.this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Define o adaptador no Spinner
        spinner_categoria.setAdapter(adapter);

        // Verifica se a variável categoria não é nula e corresponde a algum valor da lista
        if (categoria != null) {
            int pos = Arrays.asList(categorias).indexOf(categoria);
            if (pos != -1) {
                spinner_categoria.setSelection(pos); // Atualiza o Spinner
            }
        }
        // Listener para o Spinner
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Atualiza a variável categoria com o item selecionado
                categoria = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Caso nenhum item seja selecionado, você pode escolher um valor default
                //categoria = categorias[0]; // ou algum outro valor que faça sentido
            }
        });
    }
    //funcoes para usar dentro do carregar rct
    public void dificuldade(String dificuldade){
        // configurando dificuldade
        if ("Fácil".equalsIgnoreCase(dificuldade)) {
            radioGroupDificuldades.check(R.id.facil_radio_editar);
        } else if ("Médio".equalsIgnoreCase(dificuldade)) {
            radioGroupDificuldades.check(R.id.medio_radio_editar);
        } else if ("Difícil".equalsIgnoreCase(dificuldade)) {
            radioGroupDificuldades.check(R.id.dificil_radio_editar);
        }
    }

    public void custo(String custo){
        // configurando custo
        if ("Baixo".equalsIgnoreCase(custo)) {
            radioGroupCusto.check(R.id.baixo_radio_ed_c);
        } else if ("Médio".equalsIgnoreCase(custo)) {
            radioGroupCusto.check(R.id.medio_radio_ed_c);
        } else if ("Alto".equalsIgnoreCase(custo)) {
            radioGroupCusto.check(R.id.alto_radio_ed_c);
        }
    }
    //função para carregar a receita nos elementos para podela editar
    public void carregarRct(Long id){
        RetrofitClient retrofitClient = new RetrofitClient();
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        receitaAPIController.getReceita(id, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {

            }

            @Override
            public void onSuccess(Receita receita) {
                long_id = receita.getId_receita().toString();//guardando em string para se ele quiser voltar
                titulo_receita_ed.setText(receita.getNome_receita());
                ingredientes_receita_ed.setText(receita.getIngredientes());
                modo_de_preparo_ed.setText(receita.getModo_prep());
                tempo_receita_ed.setText(receita.getTempo_prep());
                //passando valor da categoria para a variavel que vai deixala ja selecionada la no spinner
                categoria = receita.getCategoria();
                atualizarSpinnerComCategoria();
                //carregando dificuldade e custo
                dificuldade = receita.getDificuldade();
                custo = receita.getCusto();
                //chamando
                dificuldade(dificuldade);
                custo(custo);
                //carregando img
                carregarIMG(receita.getId_receita());
                name_img = receita.getImg_receita();
            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(EditarReceita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Não foi possivel carregar sua receita");
                alerta.setMessage("Tente novamente mais tarde");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
                //mandando de volta a visualização caso não carregue
                Intent intent = new Intent(EditarReceita.this, Visualizar_My_Receita.class);
                intent.putExtra("Email", emailUSUARIO);
                intent.putExtra("id_rct", id_long);
                startActivity(intent);
                finish();
            }
        });
    }
    //botao editar receita
    public void editarReceita(View view){
        //pegando os valores
        String nome = titulo_receita_ed.getText().toString();
        String tempo = tempo_receita_ed.getText().toString();
        String ingredientes = ingredientes_receita_ed.getText().toString();
        String modo_preparo = modo_de_preparo_ed.getText().toString();
        String categoria_Stg = categoria;
        //dificuldade
        int idRadioDificul = radioGroupDificuldades.getCheckedRadioButtonId();
        RadioButton dificulButtonSelecionado = findViewById(idRadioDificul);
        String dificuldade = dificulButtonSelecionado.getText().toString();//usar essa variavel na api
        //custo
        int idRadioCusto = radioGroupCusto.getCheckedRadioButtonId();
        RadioButton custoButtonSelecionado = findViewById(idRadioCusto);
        String custo = custoButtonSelecionado.getText().toString();//usar essa variavel na api
        // Verifica se o usuário selecionou uma nova imagem
        File imageFile = null;
        if (imageUri != null) {
            imageFile = copiarArquivoParaCache(imageUri); // Copiar nova imagem para cache
        } else {
            // Verifica se o ImageView possui uma imagem existente
            Drawable drawable = img.getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                if (bitmap != null) {
                    imageFile = bitmapParaArquivo(bitmap, name_img); // Nome temporário
                }
            }
        }
        // Verificando se algum campo está nulo ou vazio
        if (isEmpty(nome) || isEmpty(tempo) || isEmpty(ingredientes) || isEmpty(modo_preparo) || isEmpty(categoria_Stg) || isEmpty(dificuldade) || isEmpty(custo)) {
            Toast.makeText(this, "Você possui campos em branco", Toast.LENGTH_SHORT).show();
            return; // Interrompe a execução do método
        }
        // Validação da imagem obrigatória
        if (imageFile == null) {
            Toast.makeText(this, "Você precisa selecionar ou manter uma imagem válida.", Toast.LENGTH_SHORT).show();
            return; // Interrompe a execução do método
        }
        // Consumir API
        RetrofitClient retrofitClient = new RetrofitClient();
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        // Criando receita que será enviada
        Usuario usuario = new Usuario(emailUSUARIO);
        Receita receita = new Receita(nome, custo, categoria_Stg, dificuldade, tempo, ingredientes, modo_preparo, false, null, usuario);
        //enviando
        receitaAPIController.attReceitaWithImage(id_long, receita, imageFile, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {

            }

            @Override
            public void onSuccess(Receita receita) {
                Toast.makeText(EditarReceita.this, "Sucesso ao editar receita", Toast.LENGTH_SHORT).show();
                Log.d("EditarReceita", "Sucesso ao editar receita");
                carregarRct(receita.getId_receita());
            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EditarReceita.this, "Falha ao editar receita", Toast.LENGTH_SHORT).show();
                Log.d("EditarReceita", "Falha ao editar receita, erro: " + t);
            }
        });

    }
    private File bitmapParaArquivo(Bitmap bitmap, String nomeArquivo) {
        File arquivo = new File(getCacheDir(), nomeArquivo);
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); // Ajuste o formato conforme necessário
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arquivo;
    }
   /* public void editarReceita(View view){
        //pegando os valores
        String nome = titulo_receita_ed.getText().toString();
        String tempo = tempo_receita_ed.getText().toString();
        String ingredientes = ingredientes_receita_ed.getText().toString();
        String modo_preparo = modo_de_preparo_ed.getText().toString();
        String categoria_Stg = categoria;
        //dificuldade
        int idRadioDificul = radioGroupDificuldades.getCheckedRadioButtonId();
        RadioButton dificulButtonSelecionado = findViewById(idRadioDificul);
        String dificuldade = dificulButtonSelecionado.getText().toString();//usar essa variavel na api
        //custo
        int idRadioCusto = radioGroupCusto.getCheckedRadioButtonId();
        RadioButton custoButtonSelecionado = findViewById(idRadioCusto);
        String custo = custoButtonSelecionado.getText().toString();//usar essa variavel na api
        // Verificando se algum campo está nulo ou vazio
        if (isEmpty(nome) || isEmpty(tempo) || isEmpty(ingredientes) || isEmpty(modo_preparo) || isEmpty(categoria_Stg) || isEmpty(dificuldade) || isEmpty(custo)) {
            Toast.makeText(this, "Você possui campos em branco", Toast.LENGTH_SHORT).show();
            return; // Interrompe a execução do método
        }

        // Consumir API
        RetrofitClient retrofitClient = new RetrofitClient();
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);

        // Criando receita que será enviada
        Usuario usuario = new Usuario(emailUSUARIO);
        Receita receita = new Receita(nome, custo, categoria_Stg, dificuldade, tempo, ingredientes, modo_preparo, false, null, usuario);

        // Enviando
        receitaAPIController.atualizarReceita(receita, id_long, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                // Sem uso aqui
            }

            @Override
            public void onSuccess(Receita receita) {
                Toast.makeText(EditarReceita.this, "Receita editada com sucesso", Toast.LENGTH_SHORT).show();
                Log.d("EditarReceita", "Sucesso ao editar receita");
                carregarRct(receita.getId_receita());
            }

            @Override
            public void onSuccessList(List<Receita> receitas) {
                // Sem uso aqui
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("EditarReceita", "Falha ao editar receita");
            }
        });
    }*/
    //ver se nulo ou vazio
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    //botao para voltar a visualização da receita sua
    public void voltar(View view){
        Intent intent = new Intent(EditarReceita.this, Visualizar_My_Receita.class);
        intent.putExtra("Email", emailUSUARIO);
        intent.putExtra("id_rct", long_id);
        startActivity(intent);
        finish();
    }
    //home
    public void botaohome(View view){
        Intent intent = new Intent(EditarReceita.this, home.class);
        intent.putExtra("Email", emailUSUARIO);
        startActivity(intent);
        finish();
    }
    //escrever receita
    public void irparainserir(View view){
        Intent outraTela = new Intent(EditarReceita.this, escreverreceita.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
    //perfil
    public void irparaperfil(View view){
        Intent outraTela = new Intent(EditarReceita.this, editarperfil.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
    //ver minhas receitas
    public void verMinhasReceitas(View view){
        Intent outraTela = new Intent(EditarReceita.this, MinhasReceitas.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }

}