package com.example.root.educateappcontrolvisitas.ui;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;

import androidx.appcompat.app.AppCompatActivity;

public class EscogerFormularioActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnPedagogico;
    private Button btnTecnico;
    private Button btnAmbos;
    private Button btnMovimiento;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoger_formulario);


        final Visita visita = getIntent().getParcelableExtra("visitaActual");

        btnPedagogico = (Button) findViewById(R.id.btn_pedagogico);
        btnTecnico = (Button) findViewById(R.id.btn_tecnico);
        btnAmbos = (Button) findViewById(R.id.btn_ambos);
        btnMovimiento=(Button) findViewById(R.id.btn_equipos);

        btnMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visita.setType(1);
                Intent formularioMovimientoEquipo = new Intent(getApplicationContext(),MovimientoEquiposActivity.class);
                formularioMovimientoEquipo.putExtra("visitaActual",visita);
                startActivity(formularioMovimientoEquipo);
            }
        });

        btnPedagogico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visita.setType(1);
                Intent formularioTecnico = new Intent(getApplicationContext(),CrearFormularioPedagogico.class);
                formularioTecnico.putExtra("visitaActual",visita);
                startActivity(formularioTecnico);
            }
        });

        btnTecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visita.setType(2);
                Intent formularioTecnico = new Intent(getApplicationContext(),TecnicoFormularioActivity.class);
                formularioTecnico.putExtra("visitaActual",visita);
                startActivity(formularioTecnico);
            }
        });

        btnAmbos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visita.setType(3);
                Intent formularioTecnico = new Intent(getApplicationContext(),CrearFormularioPedagogico.class);
                formularioTecnico.putExtra("visitaActual",visita);
                startActivity(formularioTecnico);
            }
        });



    }
}
