package com.example.admdigarfo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.admdigarfo.R;
import com.example.admdigarfo.api.ReceitaAPIController;
import com.example.admdigarfo.api.RetrofitClient;
import com.example.admdigarfo.api.UsuarioAPIController;
import com.example.admdigarfo.model.Adm;
import com.example.admdigarfo.model.Receita;
import com.example.admdigarfo.model.Usuario;

import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;

public class ver_receita extends AppCompatActivity {

    String emailADM;
    Long id_long;

    //coisas do layout
    ImageView img;
    ImageView img2;
    TextView dificuldade;
    TextView nome_autor;
    TextView nome_receita;
    TextView ingredientes;
    TextView modo_prep;
    TextView custo;
    TextView categoria;
    TextView tempo;
    String emailUSUARIO;

    String motivo;

    TextView descricao;
    String caminho;
    TextView motivodesaprovacao;
    TextView emailAdmLastAvaliation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_receita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String emailGuardado = getIntent().getStringExtra("Email");
        emailADM = emailGuardado;
        //pegar id da receita
        String id_guardado = getIntent().getStringExtra("id_rct");
        id_long = Long.parseLong(id_guardado);
        //teste pra ver qual id vem
        //Toast.makeText(this, id_guardado, Toast.LENGTH_SHORT).show();
        //relacion
        nome_autor = findViewById(R.id.autor_name);
        nome_receita = findViewById(R.id.nome_r);
        ingredientes = findViewById(R.id.ingredientes);
        modo_prep = findViewById(R.id.modo_prep);
        custo = findViewById(R.id.custo);
        categoria = findViewById(R.id.categoria);
        tempo = findViewById(R.id.tempo);
        descricao = findViewById(R.id.descricao);
        dificuldade = findViewById(R.id.dificuldade);
        img = findViewById(R.id.imageView6);
        img2 = findViewById(R.id.imageView);
        emailAdmLastAvaliation = findViewById(R.id.emailAdm);
        motivodesaprovacao = findViewById(R.id.motivacao);
        carregar_rct();
    }

    public void carregar_rct(){
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //receita api controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        receitaAPIController.getReceita(id_long, new ReceitaAPIController.ResponseCallback() {

            @Override
            public void onSuccess(Receita receita) {
                nome_receita.setText(receita.getNome_receita());
                ingredientes.setText(receita.getIngredientes());
                modo_prep.setText(receita.getModo_prep());
                custo.setText(receita.getCusto());
                categoria.setText(receita.getCategoria());
                tempo.setText(receita.getTempo_prep());
                dificuldade.setText(receita.getDificuldade());
                caminho = receita.getImg_receita();
                if(receita.getMotivo_desaprovacao() == null && receita.getAdm() == null){
                    motivodesaprovacao.setText("Receita ainda não foi avaliada");
                    emailAdmLastAvaliation.setText("Nenhum Adm avaliou está receita ainda");
                }else{
                    motivodesaprovacao.setText(receita.getMotivo_desaprovacao());
                    emailAdmLastAvaliation.setText(receita.getAdm().getEmail());
                }
                carregarIMG(receita.getId_receita());
                carregarAutor(receita.getId_receita());
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {

            }

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ver_receita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Não foi possivel carregar sua receita");
                alerta.setMessage("Tente novamente mais tarde");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
    }

    public void carregarAutor(Long id){
        //client retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //pegando
        usuarioAPIController.getUsuarioForReceita(id, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                if(usuario != null){
                    nome_autor.setText(usuario.getNome_usuario());
                    descricao.setText(usuario.getDescricao());
                    emailUSUARIO = usuario.getEmail();
                    pegar_img(usuario.getEmail());
                }else{
                    nome_autor.setText("Autor não achado");
                    descricao.setText("Descrição não achada");
                }
            }
            @Override
            public void onSuccess(ResponseBody responseBody) {
            }
            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ver_receita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Algo de errado não está certo...");
                alerta.setMessage("autor da receita não encontrado");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
    }

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
            }
        });
    }

    public void pegar_img(String email){
        RetrofitClient retrofitClient = new RetrofitClient();
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        usuarioAPIController.buscarImagem(email, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    // Converta a resposta para Bitmap
                    InputStream inputStream = responseBody.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if(bitmap != null){
                        // Exiba o Bitmap em uma ImageView
                        img2.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    Log.e("Erro", "Erro ao processar imagem: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Erro", "Falha ao buscar imagem: " + t.getMessage());
            }
        });
    }
    public void voltar(View view){
        Intent outraTela = new Intent(getApplicationContext(), receitas_avaliar.class);
        outraTela.putExtra("Email", emailADM);
        startActivity(outraTela);
        finish();
    }
    public void confirmar(View view){
        //atributos
        String nomeR = nome_receita.getText().toString();
        String ingredientesR = ingredientes.getText().toString();
        String modo_prepR = modo_prep.getText().toString();
        String custoR = custo.getText().toString();
        String categoriaR = categoria.getText().toString();
        String tempoR = tempo.getText().toString();
        String dificuldadeR = dificuldade.getText().toString();
        //nome do caminho da imagem
        String img_caminho = caminho;
        //criando o autor e o adm
        Usuario usuario = new Usuario(emailUSUARIO);
        Adm adm = new Adm(emailADM);
        //transformando em receita e atualizando
        Receita receita = new Receita(img_caminho, nomeR, custoR, categoriaR, dificuldadeR, tempoR, ingredientesR, modo_prepR, true, usuario, null, adm);
        //atualizando heheheh
        // Consumir API
        RetrofitClient retrofitClient = new RetrofitClient();
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        receitaAPIController.atualizarReceita(receita, id_long, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                // Sem uso aqui
            }

            @Override
            public void onSuccess(Receita receita) {
                Toast.makeText(ver_receita.this, "Receita Avaliada com Sucesso, agora ela está aprovada", Toast.LENGTH_SHORT).show();
                Log.d("ver_receita", "Sucesso ao avaliar receita, agora la está aprovada" + receita.toString());
                Intent outraTela = new Intent(getApplicationContext(), receitas_avaliar.class);
                outraTela.putExtra("Email", emailADM);
                startActivity(outraTela);
                finish();
            }

            @Override
            public void onSuccessList(List<Receita> receitas) {
                // Sem uso aqui
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("ver_receita", "Falha ao avaliar receita e tenta-la aprovar!!!" + t);
            }
        });
    }
    public void negar(View view){
        // Crie o campo de entrada (EditText) programaticamente
        final EditText inputMotivacao = new EditText(this);
        inputMotivacao.setHint("Digite o motivo...");
        inputMotivacao.setLines(3);
        inputMotivacao.setGravity(View.TEXT_ALIGNMENT_VIEW_START);
        inputMotivacao.setPadding(16, 16, 16, 16);

        // Crie o AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Insira o motivo da negação: ")
                .setView(inputMotivacao)
                .setPositiveButton("Confirmar", null) // Ouvinte será configurado depois para controle
                .setNegativeButton("Voltar", (dialogInterface, which) -> {
                    // Apenas fecha o diálogo no botão negativo
                    dialogInterface.dismiss();
                })
                .create();

        // Mostre o diálogo
        dialog.show();

        // Configure o botão positivo para validar o campo antes de fechar
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            motivo = inputMotivacao.getText().toString().trim();
            if (motivo.isEmpty()) {
                inputMotivacao.setError("O motivo é obrigatório!");
                inputMotivacao.requestFocus(); // Garante que o campo receba o foco
            } else {
                dialog.dismiss(); // Fecha o diálogo
                //atributos
                String nomeR = nome_receita.getText().toString();
                String ingredientesR = ingredientes.getText().toString();
                String modo_prepR = modo_prep.getText().toString();
                String custoR = custo.getText().toString();
                String categoriaR = categoria.getText().toString();
                String tempoR = tempo.getText().toString();
                String dificuldadeR = dificuldade.getText().toString();
                //nome do caminho da imagem
                String img_caminho = caminho;
                //criando o autor e o adm
                Usuario usuario = new Usuario(emailUSUARIO);
                Adm adm = new Adm(emailADM);
                //transformando em receita e atualizando
                Receita receita = new Receita(img_caminho, nomeR, custoR, categoriaR, dificuldadeR, tempoR, ingredientesR, modo_prepR, false, usuario, motivo, adm);
                //teste pra ver qual id vem
                Toast.makeText(this, "id: "  + id_long, Toast.LENGTH_SHORT).show();
                //atualizando heheheh
                // Consumir API
                RetrofitClient retrofitClient = new RetrofitClient();
                ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
                receitaAPIController.atualizarReceita(receita, id_long, new ReceitaAPIController.ResponseCallback() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        // Sem uso aqui
                    }

                    @Override
                    public void onSuccess(Receita receita) {
                        Toast.makeText(ver_receita.this, "Receita Avaliada com Sucesso, agora ela está negada", Toast.LENGTH_SHORT).show();
                        Log.d("ver_receita", "Sucesso ao avaliar receita, agora ela está negada" /*+ receita.toString()*/);
                        Intent outraTela = new Intent(getApplicationContext(), receitas_avaliar.class);
                        outraTela.putExtra("Email", emailADM);
                        startActivity(outraTela);
                        finish();
                    }

                    @Override
                    public void onSuccessList(List<Receita> receitas) {
                        // Sem uso aqui
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d("ver_receita", "Falha ao avaliar receita e tenta-la nega-lá!!!" + t);
                    }
                });
            }
        });
    }
}