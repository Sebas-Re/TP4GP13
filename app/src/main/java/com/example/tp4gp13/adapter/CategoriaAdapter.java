package com.example.tp4gp13.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tp4gp13.entidad.Categoria;

import java.util.List;

public class CategoriaAdapter extends ArrayAdapter<Categoria> {

    public CategoriaAdapter(Context context, List<Categoria> categorias) {
        super(context, 0, categorias);
    }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtén el objeto Categoria para esta posición
        Categoria categoria = getItem(position);

        // Verifica si existe una vista reciclada, si no, infla una nueva
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // Configura el texto del Spinner con la descripción de la categoría
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(categoria.getDescripcion());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getDescripcion());
        return view;
    }
}