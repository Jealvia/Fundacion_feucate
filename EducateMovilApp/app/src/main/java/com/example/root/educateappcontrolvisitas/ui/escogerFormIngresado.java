package com.example.root.educateappcontrolvisitas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;

public class escogerFormIngresado extends AppCompatActivity {
    private Button btnPedagogico;
    private Button btnTecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoger_form_ingresado);
        final Visita visita = getIntent().getParcelableExtra("visitaActual");

        btnPedagogico = (Button) findViewById(R.id.btn_pedagogico2);
        btnTecnico = (Button) findViewById(R.id.btn_tecnico2);



        btnPedagogico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visita.setType(1);
                Intent formularioTecnico = new Intent(getApplicationContext(),mostrarFormularioPedagogico.class);
                formularioTecnico.putExtra("visitaActual",visita);
                startActivity(formularioTecnico);
            }
        });

        btnTecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visita.setType(2);
                Intent formularioTecnico = new Intent(getApplicationContext(),mostrarFormularioTecnicoActivity.class);
                formularioTecnico.putExtra("visitaActual",visita);
                startActivity(formularioTecnico);
            }
        });

    }
}
