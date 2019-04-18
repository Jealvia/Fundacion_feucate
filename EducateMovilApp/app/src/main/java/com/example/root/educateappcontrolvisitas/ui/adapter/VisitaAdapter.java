package com.example.root.educateappcontrolvisitas.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;

import java.util.Arrays;
import java.util.List;

import static androidx.core.content.ContextCompat.getColor;

public class VisitaAdapter extends ArrayAdapter<Visita> {

    private List<Visita> visitas;


    /**
     * Create a new {@link VisitaAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param visitas is the list of  to be displayed.
     */
    public VisitaAdapter(Context context, List<Visita> visitas) {
        super(context, 0, visitas);
        this.visitas = visitas;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.visita_item, parent, false);
        }


        Visita currentVisita = visitas.get(position);


        TextView visita_numero = (TextView) listItemView.findViewById(R.id.visita_numero);
        int length = (int) (Math.log10(currentVisita.getId()) + 1);
        String s = "c";
        String original = "N° ";
        char c = '0';
        int number = 9 - length;
        char[] repeat = new char[number];
        Arrays.fill(repeat, c);
        original += new String(repeat);
        visita_numero.setText(original + currentVisita.getId());
        TextView visita_hora = (TextView) listItemView.findViewById(R.id.visita_hora);
        String string = currentVisita.getDate_planned();
        String[] parts = string.split("T");
        String fecha = parts[0]; // 004
        String hora = parts[1];
        System.out.println(string);
        System.out.println(fecha);
        System.out.println(hora.substring(0, 5));
        visita_hora.setText(hora);

        TextView escuela_MIE = (TextView) listItemView.findViewById(R.id.escuela_MIE);
        escuela_MIE.setText(currentVisita.getSchool_amie());

        TextView escuela_jornada = (TextView) listItemView.findViewById(R.id.escuela_jornada);
        escuela_jornada.setText(currentVisita.getSchool_workday());

        TextView escuela_direccion = (TextView) listItemView.findViewById(R.id.escuela_direccion);
        escuela_direccion.setText(currentVisita.getSchool_address());


        TextView escuela_nombre = (TextView) listItemView.findViewById(R.id.escuela_nombre);
        escuela_nombre.setText(currentVisita.getSchool_name());

        TextView visita_estado = (TextView) listItemView.findViewById(R.id.visita_estado);
        int estadoVisita = currentVisita.getState();
        String[] estadosVisitas = {"","PENDIENTE","REALIZADA","NO REALIZADA"};

        visita_estado.setText(estadosVisitas[estadoVisita]);


        // Set the theme color for the list item

        View textContainer = listItemView.findViewById(R.id.visita_text_container);
        // Find the color that the resource ID maps to

        // Set the background color of the text container View
        int colorBackground;

        switch (estadoVisita){
            case 1:
                colorBackground = getColor(getContext(),R.color.tan_background_pendiente);
                textContainer.setBackgroundColor(colorBackground);
                break;
            case 2:
                colorBackground = getColor(getContext(),R.color.tan_background_realizada);
                textContainer.setBackgroundColor(colorBackground);
                break;
            case 3:
                colorBackground = getColor(getContext(),R.color.tan_background_no_realizada);
                textContainer.setBackgroundColor(colorBackground);
                break;


        }


        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.

        return listItemView;
    }

}
