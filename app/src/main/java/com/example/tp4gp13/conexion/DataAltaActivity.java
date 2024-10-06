package com.example.tp4gp13.conexion;

import android.content.Context;
import android.os.AsyncTask;

import com.example.tp4gp13.entidad.Articulo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataAltaActivity extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private Articulo nuevoArticulo;
    private DataAltaListener dataAltaListener;
    private String error;

    public DataAltaActivity(Context context, Articulo nuevoArticulo) {
        this.context = context;
        this.nuevoArticulo = nuevoArticulo;
    }

    public interface DataAltaListener {
        void onAltaExitosa();
        void onAltaFallida(String error);
    }

    public void setDataAltaListener(DataAltaListener listener) {
        this.dataAltaListener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            // Consulta SQL para insertar el artículo con id de categoría
            String query = "INSERT INTO articulo (id, nombre, stock, idCategoria) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            // Asignar los valores de artículo
            statement.setInt(1, nuevoArticulo.getId());
            statement.setString(2, nuevoArticulo.getNombre());
            statement.setInt(3, nuevoArticulo.getStock());
            statement.setInt(4, nuevoArticulo.getCategoriaId()); // Obtener el ID de la categoría seleccionada

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (ClassNotFoundException | SQLException e) {
            error = e.getMessage();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (dataAltaListener != null) {
            if (success) {
                dataAltaListener.onAltaExitosa();
            } else {
                dataAltaListener.onAltaFallida(error);
            }
        }
    }
}