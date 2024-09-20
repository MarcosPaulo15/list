package com.example.list.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.list.R;
import com.example.list.model.MdSubTask;

public class SubTask extends AppCompatActivity {

    private SQLiteDatabase bd;
    MdSubTask subTask = new MdSubTask();
    private TextView title_layout;
    private EditText title;
    private EditText description;
    private EditText date;
    private Button btnAdd;
    private Button btncancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        Initialize();
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

    public void Insert(){


    }




}