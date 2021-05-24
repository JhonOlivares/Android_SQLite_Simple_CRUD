package com.example.olivarestolentino_tarea_10;

import com.example.olivarestolentino_tarea_10.modelo.Producto;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProductoListAdapter extends ArrayAdapter<Producto>{

    private  static final String TAG = "ItemListAdapter";
    private Context mContext;
    int mResourse;

    TextView tvCodigo, tvDescripcion, tvPrecio;


    public ProductoListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Producto> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourse = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);



        //get item information
        int id = getItem(position).getRowId();
        int codigo = getItem(position).getCodigo();
        String descripcion = getItem(position).getDescripcion();
        Double precio = getItem(position).getPrecio();



        //create the item object  with the information
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResourse, parent, false);

        tvCodigo = convertView.findViewById(R.id.tv_codigoAdapter);
        tvDescripcion = convertView.findViewById(R.id.tv_descripcionAdapter);
        tvPrecio = convertView.findViewById(R.id.tv_precioAdapter);

        tvCodigo.setText("Codigo: " + codigo);
        tvDescripcion.setText(descripcion);
        tvPrecio.setText("S/ " + precio);




        return convertView;
    }

    @Nullable
    @Override
    public Producto getItem(int position) {
        return super.getItem(position);
    }
}
