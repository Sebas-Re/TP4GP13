package com.example.tp4gp13.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.tp4gp13.adapter.ArticuloAdapter;
import com.example.tp4gp13.entidad.Articulo;
import com.example.tp4gp13.entidad.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataMainActivity extends AsyncTask<String, Void, String> {
    private ListView lvArticulos;
    private Context context;

    private static String result2;
    private static ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();

    public DataMainActivity(ListView lv, Context ct)
    {
        lvArticulos = lv;
        context = ct;
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT articulo.id,articulo.nombre,articulo.stock,articulo.idCategoria FROM articulo ");
            result2 = " ";

            Articulo articulo;
            while(rs.next()) {
                articulo = new Articulo();
                articulo.setId(rs.getInt("id"));
                articulo.setNombre(rs.getString("nombre"));
                articulo.setStock(rs.getInt("stock"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));

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
        ArticuloAdapter adapter = new ArticuloAdapter(context, listaArticulos);
        lvArticulos.setAdapter(adapter);
    }
}
