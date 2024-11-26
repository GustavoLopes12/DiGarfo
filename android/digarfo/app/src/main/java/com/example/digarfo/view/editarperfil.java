package com.example.digarfo.view;

import static android.text.TextUtils.isEmpty;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.example.digarfo.conexao_spring.ReceitaAPIController;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;

public class editarperfil extends AppCompatActivity {
    EditText senha; //input
    EditText nome; //input
    EditText descricao;//input
    String emailUSUARIO;//para editar esse usuario
    //para pegar a img da galeria
    ImageView img;
    Uri imageUri;

    //para ocultar ou desocultar a senha
    ImageView olho;
    boolean visible = false;
    String name_img;

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

        atualizar_dados();

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
    //BOTAO SAIR CONTA
    public void sairdaconta(View view){
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(this, "Você saiu da sua conta ;)", Toast.LENGTH_SHORT).show();
        startActivity(outraTela);
        finish();
    }
    //BOTAO DELETAR USUARIO
    public void deletaruser(View view){
        // Criação do diálogo de confirmação de exclusão
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação de Exclusão");
        builder.setMessage("Deseja confirmar a exclusão do seu perfil?");

        // Botão "Sim" para confirmar a exclusão
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Exibe uma barra de progresso para dar feedback ao usuário
                ProgressDialog progressDialog = new ProgressDialog(editarperfil.this);
                progressDialog.setMessage("Excluindo usuario...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // Inicia o processo de exclusão
                RetrofitClient retrofitClient = new RetrofitClient();
                UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);

                usuarioAPIController.deletar(emailUSUARIO, new UsuarioAPIController.ResponseCallback() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        // Fecha o ProgressDialog
                        progressDialog.dismiss();

                        // Confirmação visual e redirecionamento após exclusão
                        Toast.makeText(editarperfil.this, "Seu perfil foi deletado com sucesso.", Toast.LENGTH_SHORT).show();

                        // Redireciona para a tela de "Minhas Receitas"
                        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(outraTela);
                        finish();
                    }

                    @Override
                    public void onSuccess(Usuario usuario) {
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // Fecha o ProgressDialog e mostra uma mensagem de erro
                        progressDialog.dismiss();
                        Log.d("DeletarReceita", "Falha em deletar usuario: " + t.getMessage());
                        Toast.makeText(editarperfil.this, "Falha ao deletar o usuario. Tente novamente mais tarde!!!", Toast.LENGTH_SHORT).show();
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
    //aparecer imagem
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
                        img.setImageBitmap(bitmap);
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
    //atualizar dados
    public void atualizar_dados(){
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
                //fazendo img aparecer
                pegar_img(usuario.getEmail());
                //passando nome da img para uma variavel
                name_img = usuario.getImg_user();
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
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
    }
    //atualizar (SALVAR) usuario
    public void salvar(View view) {
        // Pegando os valores dos campos de texto
        String descricaoString = descricao.getText().toString().trim();
        String senhaString = senha.getText().toString().trim();
        String nomeString = nome.getText().toString().trim();
        // Verificando se o usuário selecionou uma nova imagem
        File imageFile = null;
        if (imageUri != null) {
            imageFile = copiarArquivoParaCache(imageUri); // Copiar nova imagem para cache
        } else {
            // Verifica se o ImageView possui uma imagem existente no caso a q veio do banco
            Drawable drawable = img.getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                if (bitmap != null) {
                    imageFile = bitmapParaArquivo(bitmap, name_img); // Nome temporário
                }
            }
        }
        // Validação dos campos obrigatórios
        if (isEmpty(descricaoString) || isEmpty(senhaString) || isEmpty(nomeString)) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return; // Interrompe a execução do método
        }

        // Validação da imagem obrigatória
        if (imageFile == null) {
            Toast.makeText(this, "Você precisa selecionar ou manter uma imagem válida.", Toast.LENGTH_SHORT).show();
            return; // Interrompe a execução do método
        }

        // Criando o objeto do usuário
        Usuario usuario = new Usuario();
        usuario.setDescricao(descricaoString);
        usuario.setNome_usuario(nomeString);
        usuario.setEmail(emailUSUARIO);
        usuario.setImg_user(null); // Será definido no backend
        usuario.setSenha(senhaString);

        // Cliente Retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);

        // Atualizar usuário
        usuarioAPIController.atualizar(emailUSUARIO, usuario, imageFile, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(editarperfil.this);
                alerta.setCancelable(false);
                alerta.setTitle("Mudança Realizada");
                alerta.setMessage("Seu perfil foi alterado com sucesso");
                alerta.setNegativeButton("Ok", null);
                alerta.create().show();
                atualizar_dados();
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                // Não utilizado
            }

            @Override
            public void onFailure(Throwable t) {
                androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(editarperfil.this);
                alerta.setCancelable(false);
                alerta.setTitle("Erro de Conexão");
                alerta.setMessage("Não foi possível alterar seu perfil. Tente novamente.\n" + t.toString());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
                atualizar_dados();
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
   /* public void salvar(View view){
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
            public void onSuccess(ResponseBody responseBody) {
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
    }*/
    public void verMinhasReceitas(View view){
        Intent outraTela = new Intent(getApplicationContext(), MinhasReceitas.class);
        outraTela.putExtra("Email", emailUSUARIO);
        startActivity(outraTela);
        finish();
    }
}