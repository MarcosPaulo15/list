package com.example.list.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.list.R;

public class AcUpdateTask extends AppCompatActivity {

    private SQLiteDatabase bd;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_update_task);

        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);
    }


    public void update(int id){

        try {
            // Abrir ou criar o banco de dados
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);

            // Criar um ContentValues para armazenar os valores que você deseja atualizar
            ContentValues valores = new ContentValues();
            valores.put("TITULO", edtTitulo);  // Atualizar o campo TITULO
            valores.put("DESCRICAO", novaDescricao);  // Atualizar o campo DESCRICAO

            // Condição para atualizar (onde ID = itemId)
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(itemId)};

            // Chamada ao método update do SQLiteDatabase
            int rowsUpdated = bd.update("list", valores, whereClause, whereArgs);

            // Verifica quantas linhas foram atualizadas
            if (rowsUpdated > 0) {
                Toast.makeText(this, "Item atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Item não encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao atualizar item", Toast.LENGTH_SHORT).show();
        } finally {
            // Fechar o banco de dados após o uso
            if (bd != null && bd.isOpen()) {
                bd.close();
            }
        }

    }
}