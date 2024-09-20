package com.example.list.model;

import android.database.sqlite.SQLiteDatabase;

public class MdSubTask {

    private SQLiteDatabase bd;

    private int id;
    private int id_task;
    private String  title;
    private String description;
    private String date;

    public int getId() {
        return id;
    }

    public int getId_task() {
        return id_task;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MdSubTask() {
    }

    public MdSubTask(int id, int id_task, String title, String description, String date) {
        this.id = id;
        this.id_task = id_task;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String criarBD(){
        try{
            String query = ("CREATE TABLE IF NOT EXISTS subtask(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ID_TASK INTEGER NOT NULL, " +
                    "TITULO VARCHAR," +
                    "DESCRICAO VARCHAR," +
                    "DATA VARCHAR)");

            return query;

        }catch(Exception ex){
            ex.printStackTrace();
            return "";
        }
    }
}
