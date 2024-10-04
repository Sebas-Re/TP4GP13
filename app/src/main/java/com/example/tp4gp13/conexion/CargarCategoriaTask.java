package com.example.tp4gp13.conexion;
import com.example.tp4gp13.entidad.Categoria;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class CargarCategoriaTask extends AsyncTask<Void, Void, List<Categoria>> {

    private Context context;
    private OnDataLoadedListener listener;

    public interface OnDataLoadedListener {
        void onDataLoaded(List<Categoria> categorias);
    }

    public CargarCategoriaTask(Context context) {
        this.context = context;
    }

    public void setDataLoadedListener(OnDataLoadedListener listener) {
        this.listener = listener;
    }


    private List<Categoria> cargarCategoriasDesdeBaseDeDatos() {
        List<Categoria> categorias = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.jdbc.Driver");

            // Establecer la conexión
            con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            // Crear una declaración
            st = con.createStatement();

            // Ejecutar la consulta para obtener todas las categorías
            String query = "SELECT id, descripcion FROM categoria"; // Ajusta esta consulta según tu tabla
            rs = st.executeQuery(query);

            // Iterar sobre los resultados y llenar la lista de categorías
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categorias.add(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir cualquier error
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return categorias; // Retornar la lista de categorías
    }
    @Override
    protected List<Categoria> doInBackground(Void... voids) {
        // Aquí deberías implementar la lógica para cargar las categorías desde tu base de datos
        // Retorna la lista de categorías
        return cargarCategoriasDesdeBaseDeDatos(); // Implementa este método según tu lógica
    }

    @Override
    protected void onPostExecute(List<Categoria> categorias) {
        if (listener != null) {
            listener.onDataLoaded(categorias); // Notifica que los datos están cargados
        }
    }
}