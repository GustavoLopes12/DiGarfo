package com.example.digarfo.view;

import static com.example.digarfo.conexao_spring.ApiConnection.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.digarfo.conexao_spring.ApiConnection;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.conexao_spring.UsuarioAPIController;
import com.example.digarfo.model.Usuario;

import java.io.File;

import okhttp3.ResponseBody;

public class Cadastro extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText senha;
    EditText descricao;

    //ImageView img;
    //Uri imageUri; //guardar uri da imagem de perfil

    //para ocultar ou desocultar a senha
    ImageView olho;
    boolean visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.nameinput);
        email = findViewById(R.id.emailinput);
        senha = findViewById(R.id.senhainput);
        descricao = findViewById(R.id.descrição);
        //para ocultar a senha ou desocultar
        olho = findViewById(R.id.zoioo);
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
        //pegando img pela galeria
        //img.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
       //         startActivityForResult(Intent.createChooser(intent, "Escolha sua Imagem"), 1);
        //    }
       // });
    }
    //negocio para pegar imagem na galeria
    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);
    //    if(resultCode == Activity.RESULT_OK){
    //        if(requestCode == 1){
    //            imageUri = data.getData(); // Armazena a URI da imagem selecionada
     //           img.setImageURI(imageUri);
    //        }
    //    }
    //}//
    // Método para obter o caminho do arquivo a partir da URI
    ////private String getRealPathFromURI(Uri uri) {
    //    String[] projection = { MediaStore.Images.Media.DATA };
    //    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
   //     if (cursor != null) {
   //         int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
     //       cursor.moveToFirst();
     //       String path = cursor.getString(columnIndex);
     //       cursor.close();
      //      return path;
     //   }
     //   return null;
    //}
    //cadastro
    public void cadastrar(View view){
        //pegando valores para criação
        String emailString = email.getText().toString();
        String nomeString = name.getText().toString();
        String senhaString = senha.getText().toString();
        String descricaoString = descricao.getText().toString();
        //cliente retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        //api controller
        UsuarioAPIController usuarioAPIController = new UsuarioAPIController(retrofitClient);
        //cadastro
        usuarioAPIController.Cadastro(nomeString, emailString, senhaString, descricaoString, new UsuarioAPIController.ResponseCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                Log.d("Cadastro", "Cadastro realizado com sucesso: " + usuario.getNome_usuario());
                Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso " + usuario.getNome_usuario(), Toast.LENGTH_SHORT).show();
                name.setText(null);
                email.setText(null);
                senha.setText(null);
                descricao.setText(null);
                Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(outraTela);
            }
            @Override
            public void onSuccess(ResponseBody responseBody) {
            }
            @Override
            public void onFailure(Throwable t) {
                androidx.appcompat.app.AlertDialog.Builder alerta = new androidx.appcompat.app.AlertDialog.Builder(Cadastro.this);
                alerta.setCancelable(false);
                alerta.setTitle("Conexão Falhou, não é possivel realizar cadastro, tente novamente");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
                name.setText(null);
                email.setText(null);
                senha.setText(null);
                descricao.setText(null);
            }
        });
    }
    //botao home sem login
    public void homesemlogin(View view){//indo para outra pagina
        Intent outraTela = new Intent(getApplicationContext(), home.class);
        startActivity(outraTela);
    }
    //voltar login
    public void outrapagina(View view){//indo para outra pagina
        Intent outraTela = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(outraTela);
    }
}