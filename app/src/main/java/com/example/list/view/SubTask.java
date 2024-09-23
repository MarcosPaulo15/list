package com.example.list.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.list.R;
import com.example.list.model.MdNewList;

import java.util.ArrayList;

public class SubTask extends AppCompatActivity {

    private SQLiteDatabase bd;
    private ListView lista;
    private Button btnBack;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);

        initialize();
        listar();
    }

    public void initialize(){
        lista = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnAdd);
        btnBack.setText("Voltar");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubTask.this, DisplayTask.class);
                intent.putExtra("ITEM_ID", itemId);
                startActivity(intent);
            }
        });
    }

    public void listar(){
        try {
            // Abrindo/criando o banco de dados
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);

            // Query para buscar o ID, título e descrição
            Cursor meuCursor = bd.rawQuery("SELECT ID, TITULO, DESCRICAO FROM subtask WHERE ID_TASK = " + itemId , null);

            // Listas para armazenar títulos e IDs
            ArrayList<String> linhas = new ArrayList<String>();
            final ArrayList<Integer> ids = new ArrayList<>();

            // Adaptador para o ListView
            ArrayAdapter<String> meuAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            lista.setAdapter(meuAdapter);

            // Mover o cursor para o primeiro item
            meuCursor.moveToFirst();

            // Itera sobre o cursor enquanto ele não chegar ao fim
            while (!meuCursor.isAfterLast()) {
                linhas.add(meuCursor.getString(1));  // Adiciona o título à lista
                ids.add(meuCursor.getInt(0));  // Armazena o ID do banco de dados
                meuCursor.moveToNext();
            }

            // Listener para cliques nos itens da lista
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Recupera o ID do item clicado
                    int itemId = ids.get(position);

                    // Exibe o ID em um Toast
                    //Toast.makeText(SubTask.this, "Item ID: " + itemId, Toast.LENGTH_SHORT).show();

                    // Exemplo de redirecionamento para outra Activity (comente ou ajuste conforme necessário)
                    Intent intent = new Intent(SubTask.this, DisplaySubTask.class);
                    intent.putExtra("ITEM_ID", itemId);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}