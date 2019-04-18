package com.example.root.educateappcontrolvisitas.api.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.educateappcontrolvisitas.R;

import java.util.ArrayList;

public class EquipoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Equipo> items;

    public EquipoAdapter(Context context, ArrayList<Equipo> items){
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
        View contentView= inflater.inflate(R.layout.list_equipos_layout,parent,false);
        TextView equipo= contentView.findViewById(R.id.tv_equipo);
        TextView serie= contentView.findViewById(R.id.tv_serie);
        TextView cantidad= contentView.findViewById(R.id.tv_cantidad);
        TextView codigo=contentView.findViewById(R.id.tv_codigo);
        Button btn_eliminar= contentView.findViewById(R.id.btn_eliminar);

        equipo.setText(items.get(i).getDescripcion());
        serie.setText("Serie: "+items.get(i).getSerie_del_equipo());
        cantidad.setText(""+items.get(i).getCantidad());
        codigo.setText(items.get(i).getCodigo_municipal());

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(indice);
                EquipoAdapter.this.notifyDataSetChanged();
            }
        });


        return contentView;
    }

}
