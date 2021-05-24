package com.example.olivarestolentino_tarea_10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.olivarestolentino_tarea_10.db.DBHelper;
import com.example.olivarestolentino_tarea_10.modelo.Producto;


public class MainActivity extends AppCompatActivity {
    private EditText etCodigo, etDescripcion, etPrecio;
    private ListView lvProductos;
    private DBHelper dbHelper;

    ArrayAdapter<String> adapter;
    ProductoListAdapter adapterProducto;
    Producto currentProduct = new Producto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(getBaseContext());


        etCodigo = findViewById(R.id.et_codigo);
        etDescripcion = findViewById(R.id.et_descripcion);
        etPrecio = findViewById(R.id.et_precio);
        lvProductos = findViewById(R.id.lv_productos);

        findViewById(R.id.btn_registrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarProducto();
            }
        });

        findViewById(R.id.btn_actualizar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarProducto();
            }
        });

        findViewById(R.id.btn_buscar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarRegistroPorCodigo();
            }
        });

        findViewById(R.id.btn_eliminar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarProducto();
            }
        });

        lvProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentProduct = (Producto) lvProductos.getItemAtPosition(position);
                FillText();
            }
        });
        Listar_Productos();
    }


    private void Listar_Productos(){
        adapterProducto = new ProductoListAdapter(this, R.layout.adapter_view_producto, dbHelper.get_all_products());
        lvProductos.setAdapter(adapterProducto);
    }

    private void RegistrarProducto(){
        if (!etCodigo.getText().toString().isEmpty() && !etPrecio.getText().toString().isEmpty() && !etDescripcion.getText().toString().isEmpty()){
            int codigo = Integer.parseInt(etCodigo.getText().toString());
            String descripcion = etDescripcion.getText().toString();
            double precio = Double.parseDouble(etPrecio.getText().toString());
            Producto producto = new Producto(codigo, descripcion, precio);
            long resultado = dbHelper.Insertar_Producto(producto);

            if (resultado < 1){
                Toast.makeText(this, "Error, verifique los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");
            currentProduct = new Producto();
            Toast.makeText(this, "Producto registrado con éxito", Toast.LENGTH_SHORT).show();

            Listar_Productos();
        }
        else {
            Toast.makeText(this, "LLena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void ActualizarProducto(){
        if (!etCodigo.getText().toString().isEmpty() && !etPrecio.getText().toString().isEmpty() && !etDescripcion.getText().toString().isEmpty()){
            int codigo = Integer.parseInt(etCodigo.getText().toString());
            String descripcion = etDescripcion.getText().toString();
            double precio = Double.parseDouble(etPrecio.getText().toString());
            Producto producto = new Producto(codigo, descripcion, precio);

            int resultado = dbHelper.ActualizarProducto(producto);

            if (resultado < 1){
                Toast.makeText(this, "Error, verifique el código ingresado", Toast.LENGTH_SHORT).show();
                return;
            }
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");
            currentProduct = new Producto();
            Toast.makeText(this, "Producto actualizado con éxito", Toast.LENGTH_SHORT).show();

            Listar_Productos();
        }
        else {
            Toast.makeText(this, "LLena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void BuscarRegistroPorCodigo()
    {
        if (!etCodigo.getText().toString().isEmpty()){
            int codigo = Integer.parseInt(etCodigo.getText().toString());
            currentProduct = dbHelper.GetProductByCode(codigo);

            if (currentProduct.getRowId() < 1 ){
                Toast.makeText(this, "No se encontró el producto: " + codigo, Toast.LENGTH_SHORT).show();
                return;
            }

            FillText();

        }
        else{
            Toast.makeText(this, "Escribe un código para realizar la búsqueda", Toast.LENGTH_SHORT).show();
        }
    }

    private void EliminarProducto(){
        if (currentProduct.getRowId() < 1 ){
            Toast.makeText(this, "Elige un Producto", Toast.LENGTH_SHORT).show();
            return;
        }
        int resultado = 0;
        resultado = dbHelper.EliminarProducto(currentProduct.getRowId());
        if(resultado >= 1){
            Toast.makeText(this, "Se eliminó el producto: " + currentProduct.getDescripcion(), Toast.LENGTH_SHORT).show();
            currentProduct = new Producto();
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");
            Listar_Productos();
        }
        else {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void FillText(){
        etCodigo.setText(currentProduct.getCodigo() + "");
        etDescripcion.setText(currentProduct.getDescripcion() + "");
        etPrecio.setText(currentProduct.getPrecio() + "");
    }
}