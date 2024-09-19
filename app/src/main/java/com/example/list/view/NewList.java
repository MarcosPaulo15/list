package com.example.list.view;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.list.R;
import com.example.list.model.MdNewList;

import java.util.Calendar;

public class NewList extends AppCompatActivity {

    private EditText edtData;
    private SQLiteDatabase bd;
    private EditText title;
    private EditText descricao;
    Button save;
    Button cancel;
    MdNewList list = new MdNewList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_list);
        edtData = findViewById(R.id.edtdata);
        title = findViewById(R.id.edtTitulo);
        descricao = findViewById(R.id.edtDescricao);
        save = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.btnCancel);
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (inserirDados()) {
                    Intent intent = new Intent(NewList.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                NewList.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        edtData.setText(dayofMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    public boolean inserirDados() {
        try {
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);
            list.setTitulo(title.getText().toString());
            list.setDescricao(descricao.getText().toString());
            list.setData(edtData.getText().toString());

            ContentValues cv = new ContentValues();
            cv.put("titulo", list.getTitulo());
            cv.put("descricao", list.getDescricao());
            cv.put("data", list.getData());

            if ((title.getText().toString() == "" || title.getText().toString() == null) || (descricao.getText().toString() == "" || descricao.getText().toString() == null)) {
                Toast.makeText(this, "Favor inserir todos os campos para poder salvar!", Toast.LENGTH_SHORT).show();
                return false;
            } else {

                long resultado = bd.insert("list", null, cv);
                bd.close();

                boolean valInser = resultado != -1 ? true : false;

                if (valInser){
                    Toast.makeText(this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Falha ao inserir dados!", Toast.LENGTH_SHORT).show();
                }

                return valInser;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}