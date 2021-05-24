package com.example.olivarestolentino_tarea_10.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.olivarestolentino_tarea_10.modelo.Producto;

import java.util.ArrayList;

public class DBHelper {

    private DBAdapter dbAdapter;

    public DBHelper(Context context) {
        dbAdapter = new DBAdapter(context);
    }

    public boolean isOpen(){
        return dbAdapter.isOpen();
    }

    public long Insertar_Producto(Producto producto){
        long r = 0;
        dbAdapter.open();
        r = dbAdapter.Insertar_Producto(producto);
        dbAdapter.close();
        return r;
    }

    public int ActualizarProducto(Producto producto){
        int r = 0;
        dbAdapter.open();
        r = dbAdapter.Actualizar_Producto(producto);
        dbAdapter.close();
        return r;
    }

    public Producto GetProductByCode(int codigo){
        dbAdapter.open();
        Producto p = dbAdapter.GetProductByCode(codigo);
        dbAdapter.close();
        return p;
    }

    public int EliminarProducto(int id){
        dbAdapter.open();
        int resultado = dbAdapter.EliminarProducto(id);// el metodo debuelve la cantidad de registros eliminados
        dbAdapter.close();
        return resultado;
    }

    public ArrayList<String> get_all_product_names(){
        dbAdapter.open();
        ArrayList<String> productos = dbAdapter.get_all_product_names();
        dbAdapter.close();
        return productos;
    }

    public ArrayList<Producto> get_all_products(){
        dbAdapter.open();
        ArrayList<Producto> productos = dbAdapter.get_all_products();
        dbAdapter.close();
        return productos;
    }
}
