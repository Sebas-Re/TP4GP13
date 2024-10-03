package com.example.tp4gp13.interfaces;

import com.example.tp4gp13.entidad.Articulo;

import java.util.ArrayList;

public interface DataLoadedListener {
    void onDataLoaded(ArrayList<Articulo> listaArticulos);
}