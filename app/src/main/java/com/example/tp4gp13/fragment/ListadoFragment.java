package com.example.tp4gp13.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.view.ViewGroup;

import com.example.tp4gp13.R;
import com.example.tp4gp13.conexion.DataMainActivity;


public class ListadoFragment extends Fragment {

    private ListView lvArticulos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado, container, false);

        lvArticulos = view.findViewById(R.id.lvArticulos);

        // Ejecutar la tarea para cargar los artículos
        DataMainActivity dataMainActivity = new DataMainActivity(lvArticulos, getActivity());
        dataMainActivity.execute();

        return view;

    }
}