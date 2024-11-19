package com.example.digarfo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digarfo.R;
import com.example.digarfo.conexao_spring.ReceitaAPIController;
import com.example.digarfo.conexao_spring.RetrofitClient;
import com.example.digarfo.model.Receita;
import com.example.digarfo.model.Usuario;

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
    }
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
    }
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