package com.example.root.educateappcontrolvisitas.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Equipo;
import com.example.root.educateappcontrolvisitas.api.model.EquipoAdapter;
import com.example.root.educateappcontrolvisitas.api.model.Evidencias;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class EvidenciasAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Evidencias> items;

    public EvidenciasAdapter(Context context, ArrayList<Evidencias> items){
        this.context= context;
        this.items= items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final int indice= i;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView= inflater.inflate(R.layout.list_imagenes_layout,parent,false);//parent,false
        TextView nombre= contentView.findViewById(R.id.tv_evidencia2);
        Button btn_eliminar= contentView.findViewById(R.id.btn_eliminar2);
        nombre.setText(items.get(i).getNombreArchivo());
        //imageView.setImageBitmap(BitmapFactory.decodeFile(items.get(i).getPathEvidencia()));
        //imageView.setImageURI(items.get(i).getPathEvidencia());//setImageBitmap(BitmapFactory.decodeFile(items.get(i).getPathEvidencia()));

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    new File(items.get(indice).getPathEvidencia()).delete();
                    items.remove(indice);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Log.e("tag", e.getMessage());
                }
                EvidenciasAdapter.this.notifyDataSetChanged();

            }
        });


        return contentView;
    }

}
