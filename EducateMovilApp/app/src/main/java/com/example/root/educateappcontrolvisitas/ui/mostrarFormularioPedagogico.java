package com.example.root.educateappcontrolvisitas.ui;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.formularioPedagogicoClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class mostrarFormularioPedagogico extends AppCompatActivity {

    private TextView fechaCheckout;
    private TextView fechaCheckin;
    private TextView latCheckout;
    private TextView lonCheckout;
    private TextView latCheckin;
    private TextView lonCheckin;
    private TextView visitaNumero;
    private TextView escuelaMIE;
    private TextView nombre;
    private TextView direccion;
    private TextView escuelaJornada;
    private TextView tieneInternet;
    private TextView parroquia;
    private TextView asesor;
    private TextView motivo;
    private TextView embajadorIN;
    private TextView extracurricular;
    private TextView visitaConTecnico;
    private TextView accion;
    private ScrollView scrollEscuela;
    private TextView accionTomadaPegagogico;
    private EditText noProfesoresCapacitados;
    private EditText minutosDelAPCI;
    private View barraCargando;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_formulario_pedagogico);

        final Visita visita = getIntent().getParcelableExtra("visitaActual");


        scrollEscuela = (ScrollView) findViewById(R.id.scrollEscuela);
        fechaCheckout = (TextView) findViewById(R.id.fecha_hora_checkout);
        fechaCheckout.setText(visita.getCheck_out());
        fechaCheckin = (TextView) findViewById(R.id.fecha_hora_checkin);
        fechaCheckin.setText(visita.getCheck_in());
        latCheckin = (TextView) findViewById(R.id.lat_checkin);
        latCheckin.setText(Double.toString(visita.getCoordinates_lat_in()));
        latCheckout = (TextView) findViewById(R.id.lat_checkout);
        latCheckout.setText(Double.toString(visita.getCoordinates_lat_out()));
        lonCheckin = (TextView) findViewById(R.id.lon_checkin);
        lonCheckin.setText(Double.toString(visita.getCoordinates_lon_in()));
        lonCheckout = (TextView) findViewById(R.id.lon_checkout);
        lonCheckout.setText(Double.toString(visita.getCoordinates_lon_out()));
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
        escuelaJornada = (TextView) findViewById(R.id.escuela_jornada);
        escuelaJornada.setText("N/A");

        parroquia = (TextView) findViewById(R.id.parroquia);
        parroquia.setText(visita.getSchool_parish());

        motivo = (TextView) findViewById(R.id.motivo);
        motivo.setText(visita.getRequirement_reason());
        embajadorIN = (TextView) findViewById(R.id.embajadorIN);
        embajadorIN.setText(visita.getSchool_ambassador_in());


        accionTomadaPegagogico = (TextView) findViewById(R.id.form_pedagogico_accion_tomada);
        noProfesoresCapacitados = (EditText) findViewById(R.id.noProfesoresCapacitados_mostrar);
        minutosDelAPCI = (EditText) findViewById(R.id.noMinutosAPCI_mostrar);

        barraCargando = (ProgressBar) findViewById(R.id.cargar_formulario_tec);
        barraCargando.setVisibility(View.VISIBLE);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_backend))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        formularioPedagogicoClient formularioPedagogicoClient = retrofit.create(formularioPedagogicoClient.class);

        Call<JsonObject> call = formularioPedagogicoClient.obtenerFormularios("system","ABC123456789",1000);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null){
                    if(response.body() != null && response.body().getAsJsonArray("objects").size() > 0){

                        JsonElement todosLosformulariosPedagogicosdeBD = response.body().getAsJsonArray("objects");

                        int totalFormulariosPedagogicos = ((JsonArray) todosLosformulariosPedagogicosdeBD).size();

                        System.out.println("------Hay " + totalFormulariosPedagogicos + " formularios pedagogicos en la BD----");
                        System.out.println("---PERO SOLO BUSCO FORMULARIOS CON VISITA ID = " + visita.getId());
                        barraCargando.setVisibility(View.VISIBLE);
                        for(int i = totalFormulariosPedagogicos-1;  i >=0  ; i--){
                            JsonElement formularioPedagogico = ((JsonArray) todosLosformulariosPedagogicosdeBD).get(i);

                            JsonElement visitaForm = formularioPedagogico.getAsJsonObject().get("visit");
                            int visita_id;
                            if(visitaForm.isJsonNull()){

                                visita_id = 0;

                            }
                            else{

                                visita_id = visitaForm.getAsJsonObject().get("id").getAsInt();


                            }

                            JsonElement accionTomada = formularioPedagogico.getAsJsonObject().get("action_taken");
                            String action_taken;
                            if (accionTomada.isJsonNull()){
                                action_taken = "Ninguna";

                            }
                            else {
                                action_taken = formularioPedagogico.getAsJsonObject().get("action_taken").getAsString();
                                if(action_taken.equals("")){
                                    action_taken = "Ninguna";

                                }
                            }

                            JsonElement noProf = formularioPedagogico.getAsJsonObject().get("no_prof_capacitados");
                            int noProfesores;
                            if (noProf.isJsonNull()){
                                noProfesores = 0;

                            }
                            else {
                                noProfesores = formularioPedagogico.getAsJsonObject().get("no_prof_capacitados").getAsInt();

                            }

                            JsonElement minUso = formularioPedagogico.getAsJsonObject().get("minutos_uso");
                            double minutos_uso;
                            if (noProf.isJsonNull()){
                                minutos_uso = 0;

                            }
                            else {
                                minutos_uso = formularioPedagogico.getAsJsonObject().get("minutos_uso").getAsDouble();

                            }

                            String usuarioCreador;

                            usuarioCreador = formularioPedagogico.getAsJsonObject().get("created_by").getAsString();






                            System.out.println("----------------FORMULARIO " + i);
                            System.out.println("id de visita" + visita_id + ". Se necesita visita con id: " + visita.getId());
                            System.out.println("Creador por: " + usuarioCreador + ". Se necesita a : " + visita.getUsername());

                            if(visita_id == visita.getId() || usuarioCreador.equals(visita.getUsername())){
                                System.out.println("----------------ACEPTADO");
                                ;
                                accionTomadaPegagogico.setText(action_taken);
                                noProfesoresCapacitados.setText(Integer.toString(noProfesores), TextView.BufferType.EDITABLE);
                                minutosDelAPCI.setText(Double.toString(minutos_uso), TextView.BufferType.EDITABLE);
                                barraCargando.setVisibility(View.GONE);

                                //noProfesores = (EditText) findViewById(R.id.noProfesoresCapacitados_mostrar);
                                //minutosDelAPCI = (EditText) findViewById(R.id.noMinutosAPCI_mostrar);





                            }
                            else {
                                System.out.println("----------------DESCARTADO");
                            }

                        }
/*
                        if(formulariosDeVisita == null){
                            System.out.println("-----NO HAY FORMULARIOS PARA ESTA VISITA-----");
                            barraCargando.setVisibility(View.GONE);

                        }
                        else {
                            int formulariosVisita = formulariosDeVisita.size();
                            if (formulariosVisita < 1){
                                System.out.println("-----NO HAY FORMULARIOS PARA ESTA VISITA-----");
                                barraCargando.setVisibility(View.GONE);


                            }
                            else {
                                barraCargando.setVisibility(View.GONE);

                                System.out.println("-----HAY " + formulariosDeVisita.size() + " formularios para esta visita------");
                                System.out.println("Los formularios de esta visita tienen los siguientes ids: ");
                                for (int i = 0; i < formulariosDeVisita.size(); i++){
                                    formularioPedagogico ftec = formulariosDeVisita.get(i);
                                    System.out.println("id: " + ftec.getId());


                                }

                                Collections.sort(formulariosDeVisita);
                                formularioPedagogico formularioGanador = formulariosDeVisita.get(0);
                                System.out.println("El formulario escogido tiene id: " + formularioGanador.getId());
                                accionTomada.setText(formularioGanador.getAccionTomada());
                                observaciones.setText(formularioGanador.getObservaciones());
                                actividadesRealizadas.setText(formularioGanador.getActividadesRealizadas());



                            }
*/                        }



                    }
                    else{
                        System.out.println("No hay formularios tecnicos en la BD");
                        barraCargando.setVisibility(View.GONE);


                    }


                }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {


            }
        });




    }
}
