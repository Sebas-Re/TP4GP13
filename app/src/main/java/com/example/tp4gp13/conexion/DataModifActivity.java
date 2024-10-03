package com.example.tp4gp13.conexion;

import android.content.Context;
import android.os.AsyncTask;

import com.example.tp4gp13.entidad.Articulo;
import com.example.tp4gp13.entidad.Categoria;
import com.example.tp4gp13.interfaces.DataLoadedListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataModifActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private int id;

    private static String result2;
    private static ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();

    public DataModifActivity(Context ct, int id)
    {
        context = ct;
        this.id = id;
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT a.id,a.nombre,a.stock,a.idCategoria, b.descripcion FROM articulo a inner join categoria b on b.id=a.idCategoria where a.id="+id);
            result2 = " ";

            Articulo articulo;
            while(rs.next()) {
                articulo = new Articulo();
                articulo.setId(rs.getInt("id"));
                articulo.setNombre(rs.getString("nombre"));
                articulo.setStock(rs.getInt("stock"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setDescripcion(rs.getString("descripcion"));

                articulo.setCategoria(categoria);
                listaArticulos.add(articulo);
            }
            response = "Conexion exitosa";
        }
        catch(Exception e) {
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }
        return response;

    }


    @Override
    protected void onPostExecute(String response) {
        if (listener != null) {
            listener.onDataLoaded(listaArticulos);
        }
    }


    private DataLoadedListener listener;

    public void setDataLoadedListener(DataLoadedListener listener) {
        this.listener = listener;
    }
}
