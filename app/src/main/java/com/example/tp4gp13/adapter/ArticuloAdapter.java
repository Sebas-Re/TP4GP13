package com.example.tp4gp13.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.tp4gp13.R;
import com.example.tp4gp13.entidad.Articulo;

public class ArticuloAdapter extends ArrayAdapter<Articulo> {

    public ArticuloAdapter(Context context, List<Articulo> objetos) {
        super(context, R.layout.fragment_listado, objetos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_articulo, parent, false);
        }

        TextView tvid = convertView.findViewById(R.id.idA);
        TextView tvnombre = convertView.findViewById(R.id.nombre);
        TextView tvstock = convertView.findViewById(R.id.stock);
        TextView tvcategoria = convertView.findViewById(R.id.categoria);

        Articulo articulo = getItem(position);

        if (articulo != null) {
            tvid.setText(String.valueOf(articulo.getId()));
            tvnombre.setText(articulo.getNombre());
            tvstock.setText(String.valueOf(articulo.getStock()));
            tvcategoria.setText(articulo.getCategoria() != null ? articulo.getCategoria().getDescripcion() : "Sin categoría");
        }

        return convertView;
    }
}