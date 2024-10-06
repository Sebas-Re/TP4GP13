package com.example.tp4gp13.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tp4gp13.R;
import com.example.tp4gp13.conexion.CargarCategoriaTask;
import com.example.tp4gp13.conexion.DataAltaActivity;
import com.example.tp4gp13.entidad.Articulo;
import com.example.tp4gp13.entidad.Categoria;

import java.util.ArrayList;
import java.util.List;

public class AltaFragment extends Fragment {

    private EditText etId;
    private EditText etNombre;
    private EditText etStock;
    private Spinner spinnerCategoria;
    private Button btnAgregar;

    private List<Categoria> listaCategorias = new ArrayList<>();
    private ArrayAdapter<Categoria> adapterCategoria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alta, container, false);

        // Inicializar los componentes de la UI
        etId = view.findViewById(R.id.input_id);
        etNombre = view.findViewById(R.id.input_nombre_producto);
        etStock = view.findViewById(R.id.input_stock);
        spinnerCategoria = view.findViewById(R.id.spinner_categoria);
        btnAgregar = view.findViewById(R.id.btn_Agregar);

        // Crear el adaptador para el Spinner
        adapterCategoria = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaCategorias);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        // Cargar las categorías en el Spinner
        cargarCategorias();

        // Acción del botón "Agregar"
        btnAgregar.setOnClickListener(v -> darDeAlta());

        return view;
    }

    private void darDeAlta() {
        String id = etId.getText().toString();
        String nombre = etNombre.getText().toString();
        String stock = etStock.getText().toString();
        Categoria categoriaSeleccionada = (Categoria) spinnerCategoria.getSelectedItem();

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(nombre) || TextUtils.isEmpty(stock) || categoriaSeleccionada == null) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Articulo nuevoArticulo = new Articulo();
        nuevoArticulo.setId(Integer.parseInt(id));
        nuevoArticulo.setNombre(nombre);
        nuevoArticulo.setStock(Integer.parseInt(stock));
        nuevoArticulo.setCategoriaId(categoriaSeleccionada.getId());  // Asociar la categoría seleccionada

        DataAltaActivity dataAltaActivity = new DataAltaActivity(getActivity(), nuevoArticulo);
        dataAltaActivity.setDataAltaListener(new DataAltaActivity.DataAltaListener() {
            @Override
            public void onAltaExitosa() {
                Toast.makeText(getContext(), "Artículo agregado exitosamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }

            @Override
            public void onAltaFallida(String error) {
                Toast.makeText(getContext(), "Error al agregar artículo: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        // Ejecutar la tarea de alta
        dataAltaActivity.execute();
    }

    private void limpiarCampos() {
        etId.setText("");
        etNombre.setText("");
        etStock.setText("");
        spinnerCategoria.setSelection(0);  // Reiniciar el Spinner
    }

    // Método para cargar las categorías
    private void cargarCategorias() {
        CargarCategoriaTask cargarTask = new CargarCategoriaTask(getActivity());
        cargarTask.setDataLoadedListener(categorias -> {
            // Añadir las categorías cargadas a la lista
            listaCategorias.addAll(categorias);
            // Notificar al adaptador que los datos han cambiado
            adapterCategoria.notifyDataSetChanged();
        });
        cargarTask.execute();
    }
}