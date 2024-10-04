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

import com.example.tp4gp13.conexion.CargarCategoriaTask; // Asegúrate de tener esta importación
import com.example.tp4gp13.conexion.DataModifActivity;
import com.example.tp4gp13.entidad.Articulo;
import com.example.tp4gp13.entidad.Categoria;
import com.example.tp4gp13.R;
import com.example.tp4gp13.adapter.CategoriaAdapter;
import java.util.ArrayList;
import java.util.List;

public class ModificacionFragment extends Fragment {

    private EditText inputIdModif, inputNombreProducto, inputStock;
    private Spinner spinnerCategoria;
    private Button btnBuscar, btnModificar;
    private Articulo articuloCargado;
    private List<Categoria> listaCategorias = new ArrayList<>();
    private ArrayAdapter<Categoria> adapterCategoria; // Declara el adaptador


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificacion, container, false);

        // Inicializar los componentes de la UI
        inputIdModif = view.findViewById(R.id.input_id_modif);
        inputNombreProducto = view.findViewById(R.id.input_nombre_producto);
        inputStock = view.findViewById(R.id.input_stock);
        spinnerCategoria = view.findViewById(R.id.spinner_categoria);
        btnBuscar = view.findViewById(R.id.btn_buscar);
        btnModificar = view.findViewById(R.id.btn_modificar);

        // Crear el adaptador para el Spinner
        adapterCategoria = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaCategorias);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        // Cargar las categorías en el Spinner
        cargarCategorias();

        // Acción del botón "Buscar"
        btnBuscar.setOnClickListener(v -> {
            String idArticulo = inputIdModif.getText().toString();
            if (!TextUtils.isEmpty(idArticulo)) {
                int id = Integer.parseInt(idArticulo);

                // Ejecutar la tarea de búsqueda
                DataModifActivity buscarTask = new DataModifActivity(getActivity(), id);
                buscarTask.setDataLoadedListener(articulo -> {
                    articuloCargado = articulo; // Guardar el artículo encontrado
                    if (articulo != null) {
                        // Actualizar los campos de la UI con los datos del artículo encontrado
                        inputNombreProducto.setText(articulo.getNombre());
                        inputStock.setText(String.valueOf(articulo.getStock()));

                        // Aquí seleccionamos la categoría correspondiente en el Spinner
                        seleccionarCategoriaEnSpinner(articulo.getCategoria().getId());
                    }
                });
                buscarTask.execute();
            } else {
                // Mostrar un error si no se ingresó un ID
                Toast.makeText(getActivity(), "Ingresa un ID válido", Toast.LENGTH_SHORT).show();
            }
        });

        // Acción del botón "Modificar"
        btnModificar.setOnClickListener(v -> {
            if (articuloCargado != null) {
                // Obtener los datos del formulario
                String nombre = inputNombreProducto.getText().toString();
                String stockString = inputStock.getText().toString();

                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(stockString)) {
                    Toast.makeText(getActivity(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int stock = Integer.parseInt(stockString);
                Categoria categoriaSeleccionada = (Categoria) spinnerCategoria.getSelectedItem();

                // Ejecutar la tarea de modificación
                DataModifActivity modifTask = new DataModifActivity(getActivity(), articuloCargado.getId(), nombre, stock, categoriaSeleccionada.getId());
                modifTask.execute();
            } else {
                // Mostrar mensaje si no se ha cargado un artículo
                Toast.makeText(getActivity(), "Primero busca el artículo a modificar", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Método para seleccionar la categoría correcta en el Spinner
    private void seleccionarCategoriaEnSpinner(int idCategoria) {
        for (int i = 0; i < spinnerCategoria.getCount(); i++) {
            Categoria categoria = (Categoria) spinnerCategoria.getItemAtPosition(i);
            if (categoria.getId() == idCategoria) {
                spinnerCategoria.setSelection(i);
                break;
            }
        }
    }

    // Método para cargar las categorías
    private void cargarCategorias() {
        CargarCategoriaTask cargarTask = new CargarCategoriaTask(getActivity());
        cargarTask.setDataLoadedListener(categorias -> {
            // Añadir las categorías cargadas a la lista
            listaCategorias.addAll(categorias);
            // Inicializa el adaptador y lo configura con el Spinner
            adapterCategoria.notifyDataSetChanged();
        });
        cargarTask.execute();
    }
}