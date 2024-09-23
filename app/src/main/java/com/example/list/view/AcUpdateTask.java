package com.example.list.view;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.metrics.EditingSession;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.list.R;

import java.util.Calendar;

public class AcUpdateTask extends AppCompatActivity {

    private SQLiteDatabase bd;
    int itemId;
    private EditText title;
    private EditText description;
    private EditText data;
    private Button update;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_update_task);
        initialize();
        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);
        LoadDate(itemId);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcUpdateTask.this, DisplayTask.class);
                intent.putExtra("ITEM_ID", itemId);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(itemId);
            }
        });
    }

    public void initialize(){
        title = (EditText) findViewById(R.id.edtTit);
        description = (EditText) findViewById(R.id.edtDesc);
        data = (EditText) findViewById(R.id.edtDat);
        update = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.btnCancel);
    }

    public void LoadDate(int id){

        Cursor meuCursor = null;
       try{
           // Abrir ou criar o banco de dados
           bd = openOrCreateDatabase("list", MODE_PRIVATE, null);

           // Query para buscar o título e descrição com base no ID
           String query = "SELECT TITULO, DESCRICAO, DATA FROM list WHERE ID = ?";

           // Executar a query com o ID como parâmetro
           meuCursor = bd.rawQuery(query, new String[]{String.valueOf(id)});

           // Verificar se há resultados
           if (meuCursor.moveToFirst()) {
               // Recuperar os valores do título e descrição
               String titulo = meuCursor.getString(0);  // Primeiro campo (TITULO)
               String descricao = meuCursor.getString(1);  // Segundo campo (DESCRICAO)
               String date = meuCursor.getString(2);// Segundo campo (DATA)

               title.setText(titulo);
               description.setText(descricao);
               data.setText(date);

           } else {
               // Caso não encontre o item com o ID fornecido
               Toast.makeText(this, "Nenhum item encontrado", Toast.LENGTH_SHORT).show();
           }


       }catch(Exception e){
           e.printStackTrace();
           Toast.makeText(this, "Erro ao atualizar item", Toast.LENGTH_SHORT).show();
       }
    }

    public void update(int id){

        try {
            // Abrir ou criar o banco de dados
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);

            // Criar um ContentValues para armazenar os valores que você deseja atualizar
            ContentValues valores = new ContentValues();
            valores.put("TITULO", title.getText().toString());  // Atualizar o campo TITULO
            valores.put("DESCRICAO", description.getText().toString());  // Atualizar o campo DESCRICAO
            valores.put("DATA", data.getText().toString());

            // Condição para atualizar (onde ID = itemId)
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(itemId)};

            // Chamada ao método update do SQLiteDatabase
            int rowsUpdated = bd.update("list", valores, whereClause, whereArgs);

            // Verifica quantas linhas foram atualizadas
            if (rowsUpdated > 0) {
                Toast.makeText(this, "Item atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AcUpdateTask.this, DisplayTask.class);
                intent.putExtra("ITEM_ID", id);
                startActivity(intent);
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

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AcUpdateTask.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        data.setText(dayofMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }



}