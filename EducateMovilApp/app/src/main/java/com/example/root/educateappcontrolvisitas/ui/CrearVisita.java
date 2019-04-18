package com.example.root.educateappcontrolvisitas.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Escuela;
import com.example.root.educateappcontrolvisitas.api.model.Motivo;
import com.example.root.educateappcontrolvisitas.api.model.PostVisita;
import com.example.root.educateappcontrolvisitas.api.model.Responsable;
import com.example.root.educateappcontrolvisitas.api.service.Requerimiento;
import com.example.root.educateappcontrolvisitas.api.service.UsuariosClient;
import com.example.root.educateappcontrolvisitas.api.service.crearVisitaClient;
import com.example.root.educateappcontrolvisitas.api.service.escuelasClient;
import com.example.root.educateappcontrolvisitas.ui.adapter.EscuelaAdapter;
import com.example.root.educateappcontrolvisitas.ui.adapter.MotivoAdapter;
import com.example.root.educateappcontrolvisitas.ui.adapter.ResponsableAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.DatePicker;
import android.widget.TimePicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;


public class CrearVisita extends AppCompatActivity implements View.OnClickListener  {

    //widgets
    private EditText textFecha, textHora;
    private Spinner escuela, responsable,motivo;
    private Button btnAceptar, btnCancelar;
    private String escuelaSeleccionada, responsableSeleccionado,motivoSeleccionado;
    private ImageButton ib_obtener_hora,ib_obtener_fecha;
    private TextView textEscuela;
    private EscuelaAdapter mEscuelaAdapter;
    private MotivoAdapter motivoAdapter;
    private ResponsableAdapter responsableAdapter;

    //Atributos para manejo de fechas y horas
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "-";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    //Inicializacion de la vista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_visita);

        //Inicializan los widgets
        motivo = (Spinner) findViewById(R.id.motivo);
        textFecha = (EditText) findViewById(R.id.textFecha);
        textHora = (EditText) findViewById(R.id.textHora);
        escuela = (Spinner) findViewById(R.id.escuela);
        responsable = (Spinner) findViewById(R.id.respponsable);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        ib_obtener_fecha=(ImageButton) findViewById(R.id.ib_obtener_fecha);
        ib_obtener_hora=(ImageButton) findViewById(R.id.ib_obtener_hora);
        textEscuela=(TextView) findViewById(R.id.textEscuela);

        //Manejador de fechas y horas
        ib_obtener_fecha.setOnClickListener(this);
        ib_obtener_hora.setOnClickListener(this);

        //Inicializacion de los spinner
        motivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Escuela: "+parent.getSelectedItem()+parent.getItemAtPosition(position).toString());
                motivoSeleccionado = parent.getItemAtPosition(position).toString();
                Motivo medicalCenter = (Motivo) parent.getItemAtPosition(position);
                motivoSeleccionado = medicalCenter.getMotivoId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Escuela: "+parent.getSelectedItem());
            }

        });

        responsable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Escuela: "+parent.getSelectedItem()+parent.getItemAtPosition(position).toString());
                responsableSeleccionado = parent.getItemAtPosition(position).toString();
                Responsable medicalCenter = (Responsable) parent.getItemAtPosition(position);
                responsableSeleccionado = medicalCenter.getResponsableId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Escuela: "+parent.getSelectedItem());
            }

        });

        motivoAdapter=new MotivoAdapter(this,new ArrayList<Motivo>(0));
        motivo.setAdapter(motivoAdapter);

        responsableAdapter=new ResponsableAdapter(this,new ArrayList<Responsable>(0));
        responsable.setAdapter(responsableAdapter);

        //Query para llenar spinner responsable
        Retrofit.Builder builder1 = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_backend))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = builder1.build();
        UsuariosClient usuariosClient = retrofit1.create(UsuariosClient.class);

        Call<JsonObject> call1 = usuariosClient.obtenerNombreUsuarios();//"system", "ABC123456789",1000

        call1.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call1, Response<JsonObject> response1) {
                if (response1.body() != null) {
                    if (response1.body() != null && response1.body().getAsJsonArray("objects").size() > 0) {
                        System.out.println("----------------RESPONSABLE-----------------");
                        JsonElement usuariosBD = response1.body().getAsJsonArray("objects");
                        System.out.println("Json: "+usuariosBD);
                        int totalUsuarios = ((JsonArray) usuariosBD).size();
                        for (int i = 0; i < totalUsuarios; i++) {
                            JsonElement formularioPedagogico = ((JsonArray) usuariosBD).get(i);

                            String nombreEscuela;
                            String id;
                            if (formularioPedagogico.isJsonNull()) {
                                nombreEscuela = "";
                                id="";
                            } else {

                                nombreEscuela = formularioPedagogico.getAsJsonObject().get("username").getAsString();
                                id=formularioPedagogico.getAsJsonObject().get("id").getAsString();
                                Responsable escuela=new Responsable(id,nombreEscuela);
                                responsableAdapter.getmItems().add(escuela);
                                responsableAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                } else {
                    Toast.makeText(CrearVisita.this, "No hay tutores en la base", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        //Query para llenar spinner motivo
        Retrofit.Builder builder2 = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_backend))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit2 = builder2.build();
        Requerimiento requerimiento = retrofit2.create(Requerimiento.class);

        Call<JsonObject> call2 = requerimiento.obtenerRequerimiento("system", "ABC123456789", 10000);

        call2.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call1, Response<JsonObject> response1) {
                if (response1.body() != null) {
                    if (response1.body() != null && response1.body().getAsJsonArray("objects").size() > 0) {
                        System.out.println("----------------MOTIVO-----------------");
                        JsonElement requerimientoBD = response1.body().getAsJsonArray("objects");
                        System.out.println("Json: "+requerimientoBD);
                        int totalUsuarios = ((JsonArray) requerimientoBD).size();
                        System.out.println(totalUsuarios);
                        for (int i = 0; i < totalUsuarios; i++) {
                            JsonElement formularioPedagogico = ((JsonArray) requerimientoBD).get(i);
                            System.out.println(i+"------"+formularioPedagogico);
                            String nombreRequerimiento;
                            String id;
                            if (formularioPedagogico.isJsonNull()) {
                                nombreRequerimiento = "";
                                id="";
                            } else {

                                nombreRequerimiento = formularioPedagogico.getAsJsonObject().get("reason").getAsString();
                                id=formularioPedagogico.getAsJsonObject().get("id").getAsString();
                                Motivo responsable=new Motivo(id,nombreRequerimiento);
                                motivoAdapter.getmItems().add(responsable);
                                motivoAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                } else {
                    Toast.makeText(CrearVisita.this, "No hay requerimientos en la base", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


        //Manejador evento boton aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String motivo = motivoSeleccionado;
                String fecha = textFecha.getText().toString();
                String hora = textHora.getText().toString();

                if (motivo.equals("") || fecha.equals("") || hora.equals("")
                        || responsableSeleccionado.equals("")) {
                    Toast.makeText(CrearVisita.this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();

                } else {
                    OkHttpClient.Builder builder1=new OkHttpClient.Builder();
                    HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder1.addInterceptor(loggingInterceptor);
                    builder1.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request=chain.request();
                            Request.Builder newRequest=request.newBuilder().addHeader("Content-type","application/json");
                            //Request.Builder newRequest=request.newBuilder().addHeader("sessionid",getIntent().getStringExtra("sesion"));
                            //newRequest.addHeader("csrftoken",getIntent().getStringExtra("token"));

                            return chain.proceed(newRequest.build());
                        }
                    });

                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(getString(R.string.url_backend))
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(builder1.build());
                    Retrofit retrofit = builder.build();
                    crearVisitaClient crearVisitaClient = retrofit.create(crearVisitaClient.class);

                    PostVisita postVisita=new PostVisita("serviceweb/api/v2/requirement/"+motivo+"/",
                            "serviceweb/api/v2/usuario/"+responsableSeleccionado+"/",
                            fecha+"T"+hora);

                    Call<PostVisita> call=crearVisitaClient.createVisit(postVisita);//"system","ABC123456789",

                    call.enqueue(new Callback<PostVisita>() {
                        @Override
                        public void onResponse(Call<PostVisita> call, Response<PostVisita> response) {
                            System.out.println("MESSAGE"+response.message()+"---------"+response.raw());
                            System.out.println("QUE PASO"+call.request()+"--------"+call.isExecuted()+"---------");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            System.out.println("QUE PASO"+call.request()+"--------"+call.isExecuted()+"---------");
                        }

                        @Override
                        public void onFailure(Call<PostVisita> call, Throwable t) {

                        }
                    });


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        //Manejador evento boton cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    //Evento click sobre fecha y hora
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
        }
    }

    //Manejador de fechas
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;

                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                textFecha.setText( year+ BARRA + mesFormateado + BARRA + diaFormateado);


            }
        },anio, mes, dia);

        recogerFecha.show();

    }

    //Manejador de horas
    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "00";
                } else {
                    AM_PM = "00";
                }

                textHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + DOS_PUNTOS + AM_PM);
            }

        }, hora, minuto, false);

        recogerHora.show();
    }
}
