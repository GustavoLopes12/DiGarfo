package com.example.digarfo.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ReceitaAPIController;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

public class Visualizar_My_Receita extends AppCompatActivity {
    String emailUSUARIO;
    Long id_long;
    String id_rct_two; //p editala
    //ELEMENTOS DA PAGINA
    TextView nome_receita;
   // TextView autor_receita;
    TextView categoria;
    TextView tempo;
    TextView custo;
    TextView ingredientes;
    TextView modo_prep;
    TextView aprovada;
    TextView motivo;
    ImageView exibicao_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visualizar_my_receita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //elementos
        exibicao_img = findViewById(R.id.imagemmmmmmm); //para exibir img
        nome_receita = findViewById(R.id.nome_RECEITA);
        //autor_receita = findViewById(R.id.autor_nameeeee);
        categoria = findViewById(R.id.CATEGORIA);
        custo = findViewById(R.id.CUSTO);
        tempo = findViewById(R.id.TEMPO);
        ingredientes = findViewById(R.id.INGREDIENTES);
        aprovada = findViewById(R.id.aprovacao_rcttt);
        modo_prep = findViewById(R.id.MODO_PREP);
        motivo = findViewById(R.id.motivo);
        //email usuario
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        //pegar id da receita
        String id_guardado = getIntent().getStringExtra("id_rct");
        id_long = Long.parseLong(id_guardado);
        //chamando metodo para buscar minha receita
        buscar_minha_receita(id_long);
    }
    //buscarMinhaReceitaClicadaPorId
    public void buscar_minha_receita(Long id_receita){
        //RetroFitClient
        RetrofitClient retrofitClient = new RetrofitClient();
        //Api Controller
        ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);
        //chamando metodo
        receitaAPIController.getReceita(id_receita, new ReceitaAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Receita receita) {
                //ibagem exibir

                //resto das paradas
                id_rct_two = receita.getId_receita().toString();
                nome_receita.setText(receita.getNome_receita());
                categoria.setText(receita.getCategoria());
                custo.setText(receita.getCusto());
                tempo.setText(receita.getTempo_prep());
                ingredientes.setText(receita.getIngredientes());
                modo_prep.setText(receita.getModo_prep());
                Log.d("StatusAprovacao", "Valor de aprovacao: " + receita.getAprovada());//teste para ver o valor se aprovado ou n no log cat
                Log.d("Receitaaa", "Receita: " + receita.toString());//teste para ver a receita
                if(receita.getAprovada()){
                    aprovada.setText("Receita aceita 👍");
                    motivo.setText("Sua Receita foi aprovada com sucesso!!! Não houveram motivos para desaprovação");
                }else{
                    aprovada.setText("Receita rejeitada 👎");
                    if(receita.getMotivo_desaprovacao() == null){
                        motivo.setText("Sua receita ainda está em analise");
                    }else{
                        motivo.setText("Motivo de reijeição: " + receita.getMotivo_desaprovacao());
                    }
                }
                /*carregarAutor(receita.getId_receita());*/
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {}

            @Override
            public void onSuccessList(List<Receita> receitas) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Visualizar_My_Receita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Não foi possivel carregar sua receita");
                alerta.setMessage("Tente novamente mais tarde");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
                //mandando de volta pra minhas receitas caso de errado
                Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
                outraTela.putExtra("Email", emailUSUARIO);
                startActivity(outraTela);
                finish();
            }
        });
    }
    public void botao_excluir(View view) {
        // Criação do diálogo de confirmação de exclusão
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação de Exclusão");
        builder.setMessage("Deseja confirmar a deleção?");

        // Botão "Sim" para confirmar a exclusão
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Exibe uma barra de progresso para dar feedback ao usuário
                ProgressDialog progressDialog = new ProgressDialog(Visualizar_My_Receita.this);
                progressDialog.setMessage("Excluindo receita...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // Inicia o processo de exclusão
                RetrofitClient retrofitClient = new RetrofitClient();
                ReceitaAPIController receitaAPIController = new ReceitaAPIController(retrofitClient);

                receitaAPIController.deletarReceita(id_long, new ReceitaAPIController.ResponseCallback() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        // Fecha o ProgressDialog
                        progressDialog.dismiss();

                        // Confirmação visual e redirecionamento após exclusão
                        Toast.makeText(Visualizar_My_Receita.this, "Sua receita foi deletada com sucesso.", Toast.LENGTH_SHORT).show();

                        // Redireciona para a tela de "Minhas Receitas"
                        Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
                        outraTela.putExtra("Email", emailUSUARIO);
                        startActivity(outraTela);
                        finish();
                    }

                    @Override
                    public void onSuccess(Receita receita){}

                    @Override
                    public void onSuccessList(List<Receita> receitas) {
                        // Não usado para este caso
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // Fecha o ProgressDialog e mostra uma mensagem de erro
                        progressDialog.dismiss();
                        Log.d("DeletarReceita", "Falha em deletar receita: " + t.getMessage());
                        Toast.makeText(Visualizar_My_Receita.this, "Falha ao deletar a receita. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Botão "Voltar" para cancelar a ação
        builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Apenas fecha o diálogo
            }
        });

        // Criar e exibir o diálogo de confirmação
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void botao_atualizar(View view){
        Intent outraTela = new Intent(getApplicationContext(), EditarReceita.class);
        outraTela.putExtra("Email", emailUSUARIO);
        outraTela.putExtra("id_rct", id_rct_two);
        startActivity(outraTela);
        finish();
    }
    //pegar autor
    /*public void carregarAutor(Long id){
        //client retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //pegando
        usuarioAPIController.getUsuarioForReceita(id, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                autor_receita.setText(usuario.getNome_usuario());
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Visualizar_My_Receita.this);
                alerta.setCancelable(false);
                alerta.setTitle("Algo de errado não está certo...");
                alerta.setMessage("autor da receita não encontrado");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
    }*/
    //funcoes rodape
    public void irparaperfil(View view){
        Intent outraTela = new Intent(getApplicationContext(), editarperfil.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
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
    public void verMinhasReceitas(View view){
        Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
}