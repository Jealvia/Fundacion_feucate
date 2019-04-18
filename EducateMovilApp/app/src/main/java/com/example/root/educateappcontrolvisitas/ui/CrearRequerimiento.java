package com.example.root.educateappcontrolvisitas.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Escuela;
import com.example.root.educateappcontrolvisitas.api.model.PostRequirement;
import com.example.root.educateappcontrolvisitas.api.model.School;
import com.example.root.educateappcontrolvisitas.api.service.Requerimiento;
import com.example.root.educateappcontrolvisitas.api.service.escuelasClient;
import com.example.root.educateappcontrolvisitas.ui.adapter.EscuelaAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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


public class CrearRequerimiento extends AppCompatActivity {

    //widgets
    private EditText textMotivo;
    private Spinner escuelas, tipo;
    private Button btnAceptar, btnCancelar;
    private ArrayList<String> listaEscuela,listaTipo;
    private String escuelaSeleccionada, responsableSeleccionado;
    private EscuelaAdapter mEscuelaAdapter;
    private String mEscuelaId;
    private SharedPreferences sp;
    private int iaa;


    //Inicializacion de la vista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_requerimiento);

        //Inicializan los widgets
        textMotivo = (EditText) findViewById(R.id.textMotivo);
        escuelas = (Spinner) findViewById(R.id.escuela);
        tipo = (Spinner) findViewById(R.id.tipo);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        iaa= escuelas.getSelectedItemPosition();
        System.out.println("VISAGE"+iaa);
        escuelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println("Escuela: "+parent.getSelectedItem()+parent.getItemAtPosition(position).toString());
                    escuelaSeleccionada = parent.getItemAtPosition(position).toString();
                    Escuela medicalCenter = (Escuela) parent.getItemAtPosition(position);
                    mEscuelaId = medicalCenter.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Escuela: "+parent.getSelectedItem());
            }

        });

        //Query para llenar spinner escuelas
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_backend))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        escuelasClient escuelasClient = retrofit.create(escuelasClient.class);

        String valor=getIntent().getStringExtra("sector");
        System.out.println("VALOR"+valor);
        Call<JsonObject> call = escuelasClient.obtenerEscuelas("system","ABC123456789",1000,valor);

        //Inicializacion del spinner
        mEscuelaAdapter=new EscuelaAdapter(this,new ArrayList<Escuela>(0));
        escuelas.setAdapter(mEscuelaAdapter);

        listaTipo=new ArrayList<String>();

        mEscuelaId="";


        //Query para llenar spinner tipo
        listaTipo.add("Periodica");
        listaTipo.add("Llamada");
        listaTipo.add("Incidencia");

        ArrayAdapter<CharSequence> adaptador1 = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listaTipo);
        tipo.setAdapter(adaptador1);

        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                responsableSeleccionado=Integer.toString(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Manejador evento boton aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String motivo = textMotivo.getText().toString();
                System.out.println("Hola"+mEscuelaId+" "+responsableSeleccionado+" "+motivo);
                if (motivo.equals("") || mEscuelaId.equals("") || responsableSeleccionado.equals("")) {
                    Toast.makeText(CrearRequerimiento.this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();

                } else {
                    System.out.println("ENREDO1: ");
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
                    sp=getSharedPreferences("login",MODE_PRIVATE);
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(getString(R.string.url_backend))
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(builder1.build());
                    Retrofit retrofit = builder.build();
                    Requerimiento crearVisitaClient = retrofit.create(Requerimiento.class);

                    int escuela=Integer.parseInt(mEscuelaId);
                    int type=Integer.parseInt(responsableSeleccionado);
                    School school =new School(escuela);
                    String tmp=sp.getString("usuario_id","");
                    System.out.println("USUARIOLALALALALLALA"+tmp);
                    //String user, String reason, String school, int type) {
                    PostRequirement postRequirement=new PostRequirement("serviceweb/api/v2/usuario/"+tmp+"/", motivo,
                            "serviceweb/api/v2/school/"+escuela+"/",type);

                    Call<PostRequirement> call=crearVisitaClient.createRequirement(postRequirement);//"system","ABC123456789",

                    System.out.println(call.request()+"-------"+call.toString()+"------"+call);
                    System.out.println("ENREDO3: ");
                    call.enqueue(new Callback<PostRequirement>() {
                        @Override
                        public void onResponse(Call<PostRequirement> call, Response<PostRequirement> response) {
                            //System.out.println("Respuesta"+response.raw()+"----"+response.body().toString()+"------"+response.message());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            System.out.println("QUE PASO"+call.request()+"--------"+call.isExecuted()+"---------"+response);
                        }

                        @Override
                        public void onFailure(Call<PostRequirement> call, Throwable t) {
                            System.out.println("QUE PASO MAL"+call.request());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
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


        //LLamada a la base para llenar la lista escuelas
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.body() != null) {

                    if (response.body() != null && response.body().getAsJsonArray("objects").size() > 0) {
                        JsonElement escuelasBD = response.body().getAsJsonArray("objects");
                        System.out.println(escuelasBD);
                        int totalEscuelas = ((JsonArray) escuelasBD).size();
                        for (int i = 0; i < totalEscuelas; i++) {
                            JsonElement formularioPedagogico = ((JsonArray) escuelasBD).get(i);

                            String nombreEscuela;
                            String id;
                            if (formularioPedagogico.isJsonNull()) {
                                nombreEscuela = "";
                                id="";
                            } else {

                                nombreEscuela = formularioPedagogico.getAsJsonObject().get("name").getAsString();
                                id=formularioPedagogico.getAsJsonObject().get("id").getAsString();
                                Escuela escuela=new Escuela(id,nombreEscuela);
                                mEscuelaAdapter.getmItems().add(escuela);
                                mEscuelaAdapter.notifyDataSetChanged();
                            }
                        }
                        System.out.println("LAMPARA");

                    }


                } else {
                    Toast.makeText(CrearRequerimiento.this, "No hay escuelas en la base", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.print("Mal trip"+t.getMessage());
            }
        });

    }

}
