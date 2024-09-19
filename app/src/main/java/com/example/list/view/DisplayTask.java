package com.example.list.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.list.R;

public class DisplayTask extends AppCompatActivity {

    private SQLiteDatabase bd;
    TextView title;
    TextView edt;
    TextView date;
    int itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task); // Verifique se o layout está correto
        Initialize();

        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);

        if (itemId != -1) {
            carregarDados(itemId);  // Método que irá carregar os dados do banco
        } else {
            // Tratamento caso não haja um ID válido
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla o menu da pasta res/menu
        getMenuInflater().inflate(R.menu.menu, menu); // Certifique-se que R.menu.menu_main está correto
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Trata as ações do menu de opções
        
        Button t = findViewById( R.id.update);
        int id = item.getItemId();

        if (id == R.id.delete) {
            
            // Código para ação de configurações
            delete(itemId);
            
            return true;
        }else if(id == R.id.update){
            
            Intent intent = new Intent(DisplayTask.this, AcUpdateTask.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Initialize(){

        title = findViewById(R.id.txvTitle);
        edt = findViewById(R.id.txvdescription);
        date = findViewById(R.id.txvdata);
    }

    public void delete(int id){

        try{
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(itemId)};

            // Chamada ao método delete do SQLiteDatabase
            int rowsDeleted = bd.delete("list", whereClause, whereArgs);

            // Verifica quantas linhas foram deletadas
            if (rowsDeleted > 0) {
                Toast.makeText(this, "Item deletado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DisplayTask.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Item não encontrado", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
        }

    }


    private void carregarDados(int itemId) {

        SQLiteDatabase bd = null;
        Cursor meuCursor = null;

        try {
            // Abrindo o banco de dados
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);

            // Query para buscar o título e descrição com base no ID
            String query = "SELECT TITULO, DESCRICAO, DATA FROM list WHERE ID = ?";

            // Executar a query com o ID como parâmetro
            meuCursor = bd.rawQuery(query, new String[]{String.valueOf(itemId)});

            // Verificar se há resultados
            if (meuCursor.moveToFirst()) {
                // Recuperar os valores do título e descrição
                String titulo = meuCursor.getString(0);  // Primeiro campo (TITULO)
                String descricao = meuCursor.getString(1);  // Segundo campo (DESCRICAO)
                String data = meuCursor.getString(2);// Segundo campo (DATA)

                title.setText(titulo);
                edt.setText(descricao);
                date.setText(data);

            } else {
                // Caso não encontre o item com o ID fornecido
                Toast.makeText(this, "Nenhum item encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
        } finally {
            // Fechar o cursor e o banco de dados
            if (meuCursor != null && !meuCursor.isClosed()) {
                meuCursor.close();
            }
            if (bd != null && bd.isOpen()) {
                bd.close();
            }
        }
    }
}