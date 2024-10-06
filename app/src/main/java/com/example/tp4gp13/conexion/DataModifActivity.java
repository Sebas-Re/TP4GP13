package com.example.tp4gp13.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tp4gp13.entidad.Articulo;
import com.example.tp4gp13.entidad.Categoria;
import com.example.tp4gp13.interfaces.DataLoadedListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataModifActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private int id;
    private String nombre;
    private int stock;
    private int idCategoria;

    private static String result2;
    private Articulo articulo = new Articulo();

    // Constructor para búsqueda
    public DataModifActivity(Context ct, int id) {
        context = ct;
        this.id = id;
    }

    // Constructor para modificación
    public DataModifActivity(Context ct, int id, String nombre, int stock, int idCategoria) {
        context = ct;
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.idCategoria = idCategoria;
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            if (nombre != null) {
                // Si se pasó un nombre, estamos modificando el artículo
                String queryUpdate = "UPDATE articulo SET nombre = '" + nombre + "', stock = " + stock + ", idCategoria = " + idCategoria + " WHERE id = " + id;
                st.executeUpdate(queryUpdate);
                response = "Artículo actualizado correctamente";
            } else {
                // Si no hay nombre, estamos buscando el artículo
                ResultSet rs = st.executeQuery("SELECT a.id, a.nombre, a.stock, a.idCategoria, b.descripcion FROM articulo a INNER JOIN categoria b ON b.id = a.idCategoria WHERE a.id = " + id);

                if (rs.next()) {
                    articulo.setId(rs.getInt("id"));
                    articulo.setNombre(rs.getString("nombre"));
                    articulo.setStock(rs.getInt("stock"));

                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("idCategoria"));
                    categoria.setDescripcion(rs.getString("descripcion"));

                    articulo.setCategoria(categoria);
                    response = "Artículo cargado correctamente";
                } else {
                    // Si no se encontraron resultados
                    response = "Artículo no encontrado";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result2 = "Error en la conexión: " + e.getMessage();
            response = result2;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        if (nombre != null) {
            // Si se hizo una modificación, muestra un mensaje
            if (response.equals("Artículo actualizado correctamente")) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al modificar el artículo: " + response, Toast.LENGTH_SHORT).show();
            }
        } else {
            // Para búsqueda
            if (response.equals("Artículo cargado correctamente")) {
                if (listener != null) {
                    listener.onDataLoaded(articulo);
                }
            } else {
                // Mostrar mensaje si no se encontró el artículo
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private DataLoadedListener listener;

    public void setDataLoadedListener(DataLoadedListener listener) {
        this.listener = listener;
    }
}