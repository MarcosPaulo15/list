package com.example.list.model;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.list.R;
import com.example.list.model.MdNewList;

import java.util.ArrayList;

public class MdNewList {

    private SQLiteDatabase bd;
    int id;
    String Titulo;
    String descricao;
    String Data;

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public MdNewList(int id, String titulo, String descricao, String data) {
        this.id = id;
        Titulo = titulo;
        this.descricao = descricao;
        Data = data;
    }

    public MdNewList() {
    }


    public String criarBD(){
        try{
            String query = ("CREATE TABLE IF NOT EXISTS list(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", TITULO VARCHAR" +
                    ", DESCRICAO VARCHAR," +
                    "DATA VARCHAR)");

            return query;
        }catch(Exception ex){
            ex.printStackTrace();
            return "";
        }
    }

    public Cursor  listarData(){

        try{
            Cursor meuCursor = bd.rawQuery("SELECT TITULO, DESCRICAO FROM list", null);

            return meuCursor;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String inserirDados(){
            String sql = "INSERT INTO list (TITULO, DESCRICAO) VALUES('"+ getTitulo() + "', '" + getDescricao() + ")";
            return sql;

    }
}
