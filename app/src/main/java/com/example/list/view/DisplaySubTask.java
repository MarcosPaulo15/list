package com.example.list.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.list.R;

public class DisplaySubTask extends AppCompatActivity {

    private TextView titleAct;
    private TextView title;
    private TextView description;
    private TextView date;
    int itemId;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);
        initialize();

        btnBack.setVisibility(View.GONE);
        carregarDados(itemId);


    }

    public void initialize(){

        titleAct = findViewById(R.id.txvTitleAct);
        title = findViewById(R.id.txvTitle);
        description = findViewById(R.id.txvdescription);
        date = findViewById(R.id.txvdata);
        btnBack = findViewById(R.id.btnback);
        titleAct.setText("Sub-Tarefa");
    }

    private void carregarDados(int itemId) {

        SQLiteDatabase bd = null;
        Cursor meuCursor = null;

        try {
            // Abrindo o banco de dados
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);

            // Query para buscar o título e descrição com base no ID
            String query = "SELECT TITULO, DESCRICAO, DATA FROM subtask WHERE ID = ?";

            // Executar a query com o ID como parâmetro
            meuCursor = bd.rawQuery(query, new String[]{String.valueOf(itemId)});

            // Verificar se há resultados
            if (meuCursor.moveToFirst()) {
                // Recuperar os valores do título e descrição
                String titulo = meuCursor.getString(0);  // Primeiro campo (TITULO)
                String descricao = meuCursor.getString(1);  // Segundo campo (DESCRICAO)
                String data = meuCursor.getString(2);// Segundo campo (DATA)

                title.setText(titulo);
                description.setText(descricao);
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