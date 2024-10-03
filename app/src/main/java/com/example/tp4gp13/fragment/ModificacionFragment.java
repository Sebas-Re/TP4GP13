package com.example.tp4gp13.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tp4gp13.R;
import com.example.tp4gp13.conexion.DataModifActivity;
import com.example.tp4gp13.entidad.Articulo;
import com.example.tp4gp13.interfaces.DataLoadedListener;

import java.util.ArrayList;


public class ModificacionFragment extends Fragment implements DataLoadedListener {

    private EditText etnombre;
    private EditText etstock;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modificacion, container, false);

        EditText input_id_modif = view.findViewById(R.id.input_id_modif);
        Button btn_buscar = view.findViewById(R.id.btn_buscar);
        etnombre = view.findViewById(R.id.input_nombre_producto);
        etstock = view.findViewById(R.id.input_stock);

        btn_buscar.setOnClickListener(v -> {
            String id = input_id_modif.getText().toString();

            // Ejecutar la tarea para cargar los artículos
            DataModifActivity DataModifActivity = new DataModifActivity(getActivity(), Integer.parseInt(id));
            DataModifActivity.setDataLoadedListener(this);
            DataModifActivity.execute();
        });


        return view;
    }

    @Override
    public void onDataLoaded(ArrayList<Articulo> listaArticulos) {
        if (!listaArticulos.isEmpty()) {
            Articulo articulo = listaArticulos.get(0);
            etnombre.setText(articulo.getNombre());
            etstock.setText(String.valueOf(articulo.getStock()));
        }
    }
}