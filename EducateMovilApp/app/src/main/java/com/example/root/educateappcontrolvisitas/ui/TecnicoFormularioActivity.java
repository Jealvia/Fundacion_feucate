package com.example.root.educateappcontrolvisitas.ui;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.formularioTecnicoClient;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TecnicoFormularioActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        return;
    }

    private ScrollView scrollEscuela;
    private TextView visitaHora;
    private TextView visitaNumero;
    private TextView escuelaMIE;
    private TextView nombre;
    private TextView direccion;
    private TextView labelEscuelaJornada;
    private TextView escuelaJornada;
    private TextView labelTieneInternet;
    private TextView tieneInternet;
    private TextView labelParroquia;
    private TextView parroquia;
    private TextView labelAsesor;
    private TextView asesor;
    private TextView labelMotivo;
    private TextView motivo;
    private TextView labelEmbajadorIN;
    private TextView embajadorIN;
    private TextView labelExtracurricular;
    private TextView extracurricular;
    private TextView labelVisitaConTecnico;
    private TextView visitaConTecnico;
    private TextView labelAccion;
    private TextView accion;
    private CheckBox chbocApci;
    private CheckBox chboxInternet;
    private CheckBox chboxSoftware;
    private CheckBox chboxHardware;
    private CheckBox chboxElectrico;
    private CheckBox chboxRedesycom;
    private Button btnSiguiente;
    private Button btnCancelar;
    private EditText observaciones;
    private EditText accionTomada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnico_formulario);
        final Visita visita = getIntent().getParcelableExtra("visitaActual");

        scrollEscuela = (ScrollView) findViewById(R.id.scrollEscuela);
        visitaHora = (TextView) findViewById(R.id.visita_hora);
        visitaHora = (TextView) findViewById(R.id.visita_hora);
        String string = visita.getDate_planned();
        String[] parts = string.split("T");
        String fecha = parts[0]; // 004
        String hora = parts[1];
        System.out.println(string);
        System.out.println(fecha);
        System.out.println(hora.substring(0, 5));
        visitaHora.setText(hora);
        visitaNumero = (TextView) findViewById(R.id.visita_numero);
        int length = (int) (Math.log10(visita.getId()) + 1);
        String s = "c";
        String original = "N° ";
        char c = '0';
        int number = 9 - length;
        char[] repeat = new char[number];
        Arrays.fill(repeat, c);
        original += new String(repeat);
        visitaNumero.setText(original + visita.getId());        escuelaMIE = (TextView) findViewById(R.id.escuela_MIE);
        escuelaMIE.setText(visita.getSchool_amie());
        nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText(visita.getSchool_name());
        direccion = (TextView) findViewById(R.id.direccion);
        direccion.setText(visita.getSchool_address());
        labelEscuelaJornada = (TextView) findViewById(R.id.label_escuela_jornada);
        escuelaJornada = (TextView) findViewById(R.id.escuela_jornada);
        escuelaJornada.setText(visita.getSchool_workday());

        labelParroquia = (TextView) findViewById(R.id.labelParroquia);
        parroquia = (TextView) findViewById(R.id.parroquia);
        parroquia.setText(visita.getSchool_parish());

        labelMotivo = (TextView) findViewById(R.id.labelMotivo);
        motivo = (TextView) findViewById(R.id.motivo);
        motivo.setText(visita.getRequirement_reason());
        labelEmbajadorIN = (TextView) findViewById(R.id.labelEmbajadorIN);
        embajadorIN = (TextView) findViewById(R.id.embajadorIN);
        embajadorIN.setText(visita.getSchool_ambassador_in());



        chbocApci = (CheckBox) findViewById(R.id.chboc_apci);
        chboxInternet = (CheckBox) findViewById(R.id.chbox_internet);
        chboxSoftware = (CheckBox) findViewById(R.id.chbox_software);
        chboxHardware = (CheckBox) findViewById(R.id.chbox_hardware);
        chboxElectrico = (CheckBox) findViewById(R.id.chbox_electrico);
        chboxRedesycom = (CheckBox) findViewById(R.id.chbox_redesycom);
        observaciones = (EditText) findViewById(R.id.observaciones);
        accionTomada = (EditText) findViewById(R.id.accionTomada);


        btnSiguiente = (Button) findViewById(R.id.btn_siguiente);
        btnCancelar = (Button)findViewById(R.id.btn_cancelar);







        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String observacion = observaciones.getText().toString();
                String acciontomada = accionTomada.getText().toString();
                String id = "/serviceweb/api/v1/visit/" + visita.getId() + "/";

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(getString(R.string.url_backend))
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();

                formularioTecnicoClient formularioTecnicoClient = retrofit.create(formularioTecnicoClient.class);

                String apci,electrico,hardware,internet,redes,software;
                if(chbocApci.isChecked()){
                    apci = "True";

                }
                else{
                    apci = "False";
                }
                if(chboxElectrico.isChecked()){
                    electrico = "True";

                }
                else{
                    electrico = "False";
                }
                if(chboxHardware.isChecked()){
                    hardware = "True";

                }
                else{
                    hardware = "False";
                }
                if(chboxInternet.isChecked()){
                    internet = "True";

                }
                else{
                    internet = "False";
                }
                if(chboxRedesycom.isChecked()){
                    redes = "True";

                }
                else{
                    redes = "False";
                }
                if(chboxSoftware.isChecked()){
                    software = "True";

                }
                else{
                    software = "False";
                }

                Call<Void> call = formularioTecnicoClient.guardarFormulario("system","ABC123456789",1000,id,acciontomada,observacion,apci
                        ,electrico,hardware,internet,redes,software,visita.getUsername(),visita.getUsername());

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(visita.getUser_type() == 2 || visita.getUser_type() == 4){
                        Intent checkOutIntent = new Intent(getApplicationContext(), CheckOutActivity.class);
                        checkOutIntent.putExtra("visitaActual",visita);
                        startActivity(checkOutIntent);
                        }
                        else{

                                Intent checkOutIntent = new Intent(getApplicationContext(), Incidencias.class);
                                checkOutIntent.putExtra("visitaActual",visita);
                                startActivity(checkOutIntent);


                    }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {



                    }
                });





            }
        });



        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TecnicoFormularioActivity.this,MainActivity.class));
                finish();
            }
        });

    }


}
