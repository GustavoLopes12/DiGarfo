package com.example.digarfo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
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
                    AlertDialog.Builder alerta = new AlertDialog.Builder(editarperfil.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Editar Usuario " + usuario.getNome_usuario());
                    alerta.setMessage("Aqui você poderá editar seu usuario");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
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
    //pegar a foto mesmo em si

    //atualizar este usuario o usuario podera mudar seu nome, senha, descrição e foto, apenas isso

    //voltar home
    public void botaohome(View view){
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
    }
    //inserir receita //se logado
    public void irparainserir(View view){
        if(emailUSUARIO != null){
            Intent outraTela = new Intent(getApplicationContext(), escreverreceita.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Faça login para escrever receitas :)", Toast.LENGTH_SHORT).show();
        }

    }
    //ir p favoritos se logado
    public void irparafavoritos(View view){
        if (emailUSUARIO != null){
            Intent outraTela = new Intent(getApplicationContext(), favoritoslogado.class);
            startActivity(outraTela);
        }else{
            Toast.makeText(this, "Você ainda não esta logado", Toast.LENGTH_SHORT).show();
        }
    }
    //atualizar (SALVAR) usuario
    public void salvar(View view){
        //para texto
        String descricaoString = descricao.getText().toString();
        String senhaString = senha.getText().toString();
        String nomeString = nome.getText().toString();
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //atualizar
        usuarioAPIController.atualizar(nomeString, descricaoString, senhaString, emailUSUARIO, banidoUSER,new UsuarioAPIController.ResponseCallback() {
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
            }
        });
    }
}