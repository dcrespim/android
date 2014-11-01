package com.tutoriales.basedatos01.model;
/**
 * Created by dcrespi on 20/05/13.
 */
public class Tarea {

    private String iddb= "";
    private String id = "";
    private String title = "";
    private String completed = "";
    private int deleted = 0;
    private String due = "";
    private String etag = "";
    private String notes = "";

    public Tarea(){

    }

    public Tarea(String iddb, String id, String title, String completed, int deleted, String due, String etag, String notes){
        this.iddb = iddb;
        this.id = id;
        this.title = title;
        this.completed=completed;
        this.deleted=deleted;
        this.due = due;
        this.etag = etag;
        this.notes = notes;
    }

    public String getIddb() {
        return iddb;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEtag() {
        return etag;
    }

    public void setIddb(String iddb) {
        this.iddb = iddb;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title= title;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    @Override
    public String toString() {
        return title;
    }
}
