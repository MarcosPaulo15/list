package com.example.list.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.list.R;
import com.example.list.model.MdSubTask;

public class NewSubTask extends AppCompatActivity {

    private SQLiteDatabase bd;
    MdSubTask subTask = new MdSubTask();
    private TextView title_layout;
    private EditText title;
    private EditText description;
    private EditText date;
    private Button btnAdd;
    private Button btncancel;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        Intent intent = getIntent();
        itemId = intent.getIntExtra("ID_ITEM", -1);

        Initialize();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirDados();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(NewSubTask.this, DisplayTask.class);
                startActivity(intent);
            }
        });
        Consulta();
    }

    public void Initialize(){

        title_layout = findViewById(R.id.txvRegisterTilte);
        title = findViewById(R.id.edtTitulo);
        description = findViewById(R.id.edtDescricao);
        date = findViewById(R.id.edtdata);
        btnAdd = findViewById(R.id.btnSave);
        btncancel = findViewById(R.id.btnCancel);

        title_layout.setText("Registre a Sub-Tarefa");
    }
    public void Consulta(){
        try{

            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);
            bd.execSQL(subTask.criarBD());
            bd.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean inserirDados() {
        try {
            bd = openOrCreateDatabase("list", MODE_PRIVATE, null);
            subTask.setTitle(title.getText().toString());
            subTask.setDescription(description.getText().toString());
            subTask.setDate(date.getText().toString());

            ContentValues cv = new ContentValues();
            cv.put("id_task", itemId);
            cv.put("titulo", subTask.getTitle());
            cv.put("descricao", subTask.getDescription());
            cv.put("data", subTask.getDate());

            if ((title.getText().toString() == "" || title.getText().toString() == null) || (description.getText().toString() == "" || date.getText().toString() == null)) {
                Toast.makeText(this, "Favor inserir todos os campos para poder salvar!", Toast.LENGTH_SHORT).show();
                return false;
            } else {

                long resultado = bd.insert("subtask", null, cv);
                bd.close();

                boolean valInser = resultado != -1 ? true : false;

                if (valInser){
                    Toast.makeText(this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NewSubTask.this, DisplayTask.class);
                    intent.putExtra("ITEM_ID", itemId);
                    startActivity(intent);
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