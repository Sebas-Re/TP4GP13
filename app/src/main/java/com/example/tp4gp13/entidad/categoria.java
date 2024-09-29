package com.example.tp4gp13.entidad;

import androidx.annotation.NonNull;

public class categoria {

    private int id;
    private String descripcion;

    public categoria(){}

    public categoria(String descripcion, int id) {
        this.descripcion = descripcion;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @NonNull
    @Override
    public String toString() {
        return "categoria{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
