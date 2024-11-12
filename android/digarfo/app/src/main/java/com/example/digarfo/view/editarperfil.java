package com.example.digarfo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Usuario;

public class editarperfil extends AppCompatActivity {
    EditText senha; //input
    EditText nome; //input
    EditText descricao;//input
    String emailUSUARIO;//para editar esse usuario
    //para pegar a img da galeria
    ImageView img;
    boolean banidoUSER;
    Uri imageUri;

    //para ocultar ou desocultar a senha
    ImageView olho;
    boolean visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editarperfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //valores
                senha = findViewById(R.id.password);
                olho = findViewById(R.id.olho2);
                //botao olhinho da senha
                olho.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(visible){
                            senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            olho.setImageResource(R.drawable.zoio);
                        }else{
                            senha.setInputType(InputType.TYPE_CLASS_TEXT);
                            olho.setImageResource(R.drawable.olho_aberto);
                        }
                        senha.setSelection(senha.getText().length());
                        visible = !visible;
                    }
                });
        nome = findViewById(R.id.nome);
        descricao = findViewById(R.id.descricao);
        //pegando email de usuario que deverá ser atualizado
        String emailGuardado = getIntent().getStringExtra("Email");
        emailUSUARIO = emailGuardado;
        //pego usuario do email

            //cliente retrofit
            RetrofitClient retrofitClient = new RetrofitClient();
            //api controller
            UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
            usuarioAPIController.getUsuario(emailUSUARIO, new UsuarioAPIController.ResponseCallback() {
                @Override
                public void onSuccess(Usuario usuario) {
                    nome.setText(usuario.getNome_usuario());
                    senha.setText(usuario.getSenha());
                    descricao.setText(usuario.getDescricao());
                    banidoUSER = usuario.isBanido();
                }

                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(editarperfil.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Algo de errado não está certo...");
                    alerta.setMessage("Tente editar seu usuario mais tarde!!!");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
                }
            });

        //abrindo galeria
        img = findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Escolha sua Imagem"), 1);
            }
        });
    }//FIM-ONCREATE

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

    //BOTAO SAIR CONTA
    public void sairdaconta(View view){
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(this, "Você saiu da sua conta ;)", Toast.LENGTH_SHORT).show();
        startActivity(outraTela);
        finish();
    }

    //BOTAO DELETAR USUARIO
    public void deletaruser(View view){

            //cliente retrofit
            RetrofitClient retrofitClient = new RetrofitClient();
            //api controller
            UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
            usuarioAPIController.deletar(emailUSUARIO, new UsuarioAPIController.ResponseCallback() {
                @Override
                public void onSuccess(Usuario usuario) { //rever parametros
                    //por fim
                    Toast.makeText(editarperfil.this, "Usuário deletado!!", Toast.LENGTH_SHORT).show();

                    Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(outraTela);
                    finish();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("Erro", "Erro: " +t);
                    AlertDialog.Builder alerta = new AlertDialog.Builder(editarperfil.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Erro ao excluir conta");
                    alerta.setMessage("Houve um erro ao tentar excluir sua conta. Tente novamente.");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
                }
            });


    }
    //--endpoint deleta user do email guardado ate essa pag
    //FIM-BOTAO DELETAR USUARIO

    //voltar home
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
    //inserir receita
    public void irparainserir(View view){
            Intent outraTela = new Intent(getApplicationContext(), escreverreceita.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
    }
    //ir p favoritos se logado
    /*public void irparafavoritos(View view){
            Intent outraTela = new Intent(getApplicationContext(), favoritoslogado.class);
            outraTela.putExtra("Email", emailUSUARIO);
            startActivity(outraTela);
            finish();
    }*/
    //atualizar dados
    public void atualizar_dados(){
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        usuarioAPIController.getUsuario(emailUSUARIO, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                nome.setText(usuario.getNome_usuario());
                senha.setText(usuario.getSenha());
                descricao.setText(usuario.getDescricao());
                banidoUSER = usuario.isBanido();
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(editarperfil.this);
                alerta.setCancelable(false);
                alerta.setTitle("Algo de errado não está certo...");
                alerta.setMessage("erro ao buscar dados de perfil");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });
    }
    //atualizar (SALVAR) usuario
    public void salvar(View view){
        //para texto
        String descricaoString = descricao.getText().toString();
        String senhaString = senha.getText().toString();
        String nomeString = nome.getText().toString();
        //criando usuario
        Usuario usuario = new Usuario();
        usuario.setDescricao(descricaoString);
        usuario.setNome_usuario(nomeString);
        usuario.setEmail(emailUSUARIO);
        usuario.setSenha(senhaString);
        usuario.setBanido(banidoUSER);
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //atualizar
        usuarioAPIController.atualizar(usuario, emailUSUARIO, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(editarperfil.this);
                alerta.setCancelable(false);
                alerta.setTitle("Mudança Realizada");
                alerta.setMessage("Seu perfil foi alterado com sucesso");
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
                nome.setText(null);
                descricao.setText(null);
                senha.setText(null);
                atualizar_dados();
            }

            @Override
            public void onFailure(Throwable t) {
                androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(editarperfil.this);
                alerta.setCancelable(false);
                alerta.setTitle("Conexão Falhou, não é possivel alterar seu perfil, tente novamente");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
                nome.setText(null);
                descricao.setText(null);
                senha.setText(null);
                atualizar_dados();
            }
        });
    }
    public void verMinhasReceitas(View view){
        Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
}