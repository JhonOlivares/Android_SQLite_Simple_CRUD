package com.example.olivarestolentino_tarea_10.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.olivarestolentino_tarea_10.modelo.Producto;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DBAdapter {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB_sesion10_ejemplo01";

    private static final String TABLE_PRODUCTO = "table_producto";


    private static final String KEY_ROWID = "rowId";

    private static final String KEY_PRODUCTO_CODIGO = "codigo";
    private static final String KEY_PRODUCTO_DESCRIPCION = "descripcion";
    private static final String KEY_PRODUCTO_PRECIO = "precio";


    private static final String TABLE_CREATE_PRODUCTO = "create table " + TABLE_PRODUCTO +
            "(" +
            KEY_ROWID + " integer primary key autoincrement, " +
            KEY_PRODUCTO_CODIGO + " integer not null unique, " +
            KEY_PRODUCTO_DESCRIPCION + " text not null, " +
            KEY_PRODUCTO_PRECIO + " real not null " +
            ")";


    private DatabaseHelper databasehelper;
    private SQLiteDatabase db; //objeto de base de datos
    private static Context context;

    public DBAdapter(Context context){
        this.context = context;
        databasehelper = new DatabaseHelper(context);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(TABLE_CREATE_PRODUCTO);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_PRODUCTO);
            db.execSQL(TABLE_CREATE_PRODUCTO);
        }
    }

    public DBAdapter open() throws SQLiteException{
        try {
            db = databasehelper.getWritableDatabase();// nos permite escribir y leer en la base de datos
        }catch (SQLiteException e){
            Toast.makeText(context, "Error al abrir la base de datos", Toast.LENGTH_SHORT).show();
        }
        return this;
    }

    public boolean isCreated(){
        if (db != null){
            return db.isOpen();
        }
        return false;
    }

    public boolean isOpen(){
        if (db == null){
            return false;
        }
        return db.isOpen();
    }

    public void close(){
        databasehelper.close();
        db.close();
    }

//    CRUD


    // INSERTAR UN PRODUCTO EN LA BASE DE DATOS
    public long Insertar_Producto(Producto producto){
        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCTO_CODIGO, producto.getCodigo());
        values.put(KEY_PRODUCTO_DESCRIPCION, producto.getDescripcion());
        values.put(KEY_PRODUCTO_PRECIO, producto.getPrecio());
        return db.insert(TABLE_PRODUCTO, null, values);
    }


    // ACTURALIZAR UN REGISTRO
    public int Actualizar_Producto(Producto producto){
        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCTO_DESCRIPCION, producto.getDescripcion());
        values.put(KEY_PRODUCTO_PRECIO, producto.getPrecio());
        return db.update(TABLE_PRODUCTO + "", values, KEY_PRODUCTO_CODIGO + " = " + producto.getCodigo(), null);
    }

    public Producto GetProductByCode(int id){
        Producto p = new Producto();
        if (id > 0){
            Cursor cursor = db.rawQuery("select * from " + TABLE_PRODUCTO + " where " + KEY_PRODUCTO_CODIGO + " = " + id, null);
            if (cursor.moveToFirst()){
                p.setRowId(cursor.getInt(0));
                p.setCodigo(cursor.getInt(1));
                p.setDescripcion(cursor.getString(2));
                p.setPrecio(cursor.getDouble(3));
            }
        }
        return p;
    }

    public int EliminarProducto(int id){
        int r = db.delete(TABLE_PRODUCTO + "", KEY_ROWID + " = " + id, null);// este metodo devuelve el numero de elrmentos eliminados
        return r;
    }


    // LISTAR LA BASE DE DATOS
    public ArrayList<Producto> get_all_products(){
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            String query = "select * from " + TABLE_PRODUCTO;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    Producto producto = new Producto();
                    producto.setRowId(cursor.getInt(0));
                    producto.setCodigo(cursor.getInt(1));
                    producto.setDescripcion(cursor.getString(2));
                    producto.setPrecio(cursor.getDouble(3));
                    productos.add(producto);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return productos;
        }catch (Exception e){
            return null;
        }
    }

    public ArrayList<String> get_all_product_names(){
        ArrayList<String> productos = new ArrayList<>();
        try {
            String query = "select * from " + TABLE_PRODUCTO;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    productos.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return productos;
        }catch (Exception e){
            return null;
        }
    }

}
