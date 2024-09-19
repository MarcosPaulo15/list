package com.example.list.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.list.R;
import com.example.list.model.MdNewList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bd;
    private ListView lista;
    MdNewList mdlist = new MdNewList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnAdd);
        lista = (ListView) findViewById(R.id.listView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewList.class);
                startActivity(intent);
            }
        });

        MdNewList list = new MdNewList();
        Consulta();
        listar();
    }

    public void Consulta(){
        try{

            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);
            mdlist.criarBD();
            bd.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /*public void  listar(){

        try{

            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);
            Cursor meuCursor = bd.rawQuery("SELECT ID, TITULO, DESCRICAO FROM list", null);
            ArrayList<String> linhas = new ArrayList<String>();
            final ArrayList<Integer> ids = new ArrayList<>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            lista.setAdapter((meuAdapter));
            meuCursor.moveToFirst();
            /*while(meuCursor != null){

                linhas.add(meuCursor.getString(1));
                meuCursor.moveToNext();
            }

            while (!meuCursor.isAfterLast()) {  // Verifica se o cursor já terminou de iterar
                linhas.add(meuCursor.getString(1));  // Adiciona o título ou descrição à lista
                ids.add(meuCursor.getInt(0));  // Armazena o ID do banco de dados (_id)
                meuCursor.moveToNext();
            }

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Recupera o ID do item clicado usando a posição
                    int itemId = ids.get(position);

                    // Exemplo de ação: mostrar um Toast com o ID
                    Toast.makeText(MainActivity.this, "Item ID: " + itemId, Toast.LENGTH_SHORT).show();

                    // Você pode usar este ID para carregar outra tela ou dados
                    // Exemplo: Intent para abrir outra Activity e passar o ID
                    Intent intent = new Intent(MainActivity.this, android.R.menu.class);
                    intent.putExtra("ITEM_ID", itemId);
                    //startActivity(intent);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public void listar() {
        try {
            // Abrindo/criando o banco de dados
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);

            // Query para buscar o ID, título e descrição
            Cursor meuCursor = bd.rawQuery("SELECT ID, TITULO, DESCRICAO FROM list", null);

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
                    Toast.makeText(MainActivity.this, "Item ID: " + itemId, Toast.LENGTH_SHORT).show();

                    // Exemplo de redirecionamento para outra Activity (comente ou ajuste conforme necessário)
                Intent intent = new Intent(MainActivity.this, DisplayTask.class);
                intent.putExtra("ITEM_ID", itemId);
                startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}