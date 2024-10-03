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


public class ModificacionFragment extends Fragment {

    private ListView lvArticulos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modificacion, container, false);

        EditText input_id_modif = view.findViewById(R.id.input_id_modif);
        Button btn_buscar = view.findViewById(R.id.btn_buscar);

        btn_buscar.setOnClickListener(v -> {
            String id = input_id_modif.getText().toString();

            Toast.makeText(getActivity(), "ID: " + id, Toast.LENGTH_SHORT).show();
        });


        return view;
    }
}