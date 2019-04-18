package com.example.root.educateappcontrolvisitas.ui;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.IncidenciaClient;

import java.util.Arrays;

public class incidenciaFormulario extends AppCompatActivity {

    private ScrollView scrollEscuela;
    private TextView visitaHora;
    private TextView visitaNumero;
    private TextView escuelaMIE;
    private TextView nombre;
    private TextView direccion;
    private TextView escuelaJornada;
    private TextView labelEscuelaJornada;
    private TextView labelParroquia;
    private TextView parroquia;
    private TextView labelAsesor;
    private TextView asesor;
    private TextView labelMotivo;
    private TextView motivo;
    private TextView labelEmbajadorIN;
    private TextView embajadorIN;
    private EditText incidencia;
    private Button reportarIncidencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia_formulario);

        final Visita visita = getIntent().getParcelableExtra("visitaActual");

        scrollEscuela = (ScrollView) findViewById(R.id.scrollIncidencia);
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
        visitaNumero.setText(original + visita.getId());
        escuelaMIE = (TextView) findViewById(R.id.escuela_MIE);
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
        incidencia = (EditText) findViewById(R.id.descripcionIncidencia);
        reportarIncidencia = (Button) findViewById(R.id.btn_reportar_incidencia);

        reportarIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String incidenciaReportada = incidencia.getText().toString();
                boolean incidenciaVacia = TextUtils.isEmpty(incidenciaReportada);

                if(incidenciaVacia){
                    Context context = getApplicationContext();
                    CharSequence text = "¡LLENAR LOS CAMPOS OBLIGATORIOS(*)!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
                else {

                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(getString(R.string.url_backend))
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();

                    IncidenciaClient incidenciaClient = retrofit.create(IncidenciaClient.class);
                    String escuelaId = "/serviceweb/api/v1/school/" + visita.getSchool_id() + "/";
                    String usuario_Id = "/serviceweb/api/v1/usuario/" + visita.getUser_id() + "/";

                    Call<Void> call = incidenciaClient.guardarIncidencia("system","ABC123456789",1000000,escuelaId,incidenciaReportada,1,3,usuario_Id
                            ,"True",visita.getUser_first_name() + " " + visita.getUser_last_name(),visita.getUser_first_name() + " " + visita.getUser_last_name());

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                                Intent checkOutIntent = new Intent(getApplicationContext(), CheckOutActivity.class);
                                checkOutIntent.putExtra("visitaActual",visita);
                                startActivity(checkOutIntent);
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
                }
            }
        });
    }
}
