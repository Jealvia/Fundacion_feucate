package com.example.root.educateappcontrolvisitas.ui;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.FormularioTecnico;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.formularioTecnicoClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class mostrarFormularioTecnicoActivity extends AppCompatActivity {

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
    private TextView accionTomada;
    private TextView observaciones;
    private TextView actividadesRealizadas;
    private List<FormularioTecnico> formulariosDeVisita;
    private View barraCargando;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_formulario_tecnico);

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
        visitaNumero.setText(original + visita.getId());        escuelaMIE = (TextView) findViewById(R.id.escuela_MIE);
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


        accionTomada = (TextView) findViewById(R.id.form_tec_accion_tomada);
        observaciones = (TextView) findViewById(R.id.form_tec_observaciones);
        actividadesRealizadas = (TextView) findViewById(R.id.form_tec_actividades_realizadas);
        barraCargando = (ProgressBar) findViewById(R.id.cargar_formulario_tec);
        barraCargando.setVisibility(View.VISIBLE);


        formulariosDeVisita = new ArrayList<FormularioTecnico>();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_backend))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        formularioTecnicoClient formularioTecnicoClient = retrofit.create(formularioTecnicoClient.class);

        Call<JsonObject> call = formularioTecnicoClient.obtenerFormularios("system","ABC123456789",1000);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null){
                    if(response.body() != null && response.body().getAsJsonArray("objects").size() > 0){

                        JsonElement todosLosformulariosTecnicosdeBD = response.body().getAsJsonArray("objects");

                        int totalFormulariosTecnicos = ((JsonArray) todosLosformulariosTecnicosdeBD).size();

                        System.out.println("------Hay " + totalFormulariosTecnicos + " formularios tecnicos en la BD----");
                        System.out.println("---PERO SOLO BUSCO FORMULARIOS CON VISITA ID = " + visita.getId());
                        barraCargando.setVisibility(View.VISIBLE);
                        for(int i = 0;  i < totalFormulariosTecnicos ; i++){
                            JsonElement formularioTecnico = ((JsonArray) todosLosformulariosTecnicosdeBD).get(i);
                            JsonElement form_id = formularioTecnico.getAsJsonObject().get("id");
                            int formulario_id;
                            if(form_id.isJsonNull()){
                                formulario_id = 0;

                            }
                            else {
                                formulario_id = formularioTecnico.getAsJsonObject().get("id").getAsInt();

                            }



                            JsonElement visitaForm = formularioTecnico.getAsJsonObject().get("visit");
                            int visita_id;
                            if(visitaForm.isJsonNull()){

                                visita_id = 0;

                            }
                            else{

                                visita_id = visitaForm.getAsJsonObject().get("id").getAsInt();


                            }

                            JsonElement accionTomada = formularioTecnico.getAsJsonObject().get("action_taken");
                            String action_taken;
                            if (accionTomada.isJsonNull()){
                                action_taken = "Ninguna";

                            }
                            else {
                                action_taken = formularioTecnico.getAsJsonObject().get("action_taken").getAsString();
                                if(action_taken.equals("")){
                                    action_taken = "Ninguna";

                                }
                            }

                            JsonElement observacion = formularioTecnico.getAsJsonObject().get("observation");
                            String observation;
                            if (observacion.isJsonNull()){
                                observation = "Ninguna";

                            }
                            else {
                                observation = formularioTecnico.getAsJsonObject().get("observation").getAsString();
                                if(observation.equals("")){
                                    observation = "Ninguna";

                                }
                            }

                            boolean apci,internet,redes,software,hardware,electrico;
                            JsonElement op_apci = formularioTecnico.getAsJsonObject().get("apci");
                            if(op_apci.isJsonNull()){
                                apci = false;

                            }
                            else {
                                apci = formularioTecnico.getAsJsonObject().get("apci").getAsBoolean();

                            }
                            JsonElement op_internet = formularioTecnico.getAsJsonObject().get("internet");
                            if(op_internet.isJsonNull()){
                                internet = false;

                            }
                            else {
                                internet = formularioTecnico.getAsJsonObject().get("internet").getAsBoolean();

                            }

                            JsonElement op_redes = formularioTecnico.getAsJsonObject().get("redes");
                            if(op_redes.isJsonNull()){
                                redes = false;

                            }
                            else {
                                redes = formularioTecnico.getAsJsonObject().get("redes").getAsBoolean();

                            }

                            JsonElement op_software = formularioTecnico.getAsJsonObject().get("software");
                            if(op_software.isJsonNull()){
                                software = false;

                            }
                            else {
                                software = formularioTecnico.getAsJsonObject().get("software").getAsBoolean();

                            }

                            JsonElement op_hardware = formularioTecnico.getAsJsonObject().get("hardware");
                            if(op_hardware.isJsonNull()){
                                hardware = false;

                            }
                            else {
                                hardware = formularioTecnico.getAsJsonObject().get("hardware").getAsBoolean();

                            }

                            JsonElement op_electrico = formularioTecnico.getAsJsonObject().get("electrico");
                            if(op_electrico.isJsonNull()){
                                electrico  = false;

                            }
                            else {
                                electrico  = formularioTecnico.getAsJsonObject().get("electrico").getAsBoolean();

                            }

                            String actividadRealizada;

                            if(!apci && !internet && !redes && !hardware && !software && !electrico){
                                actividadRealizada= "Ninguna";
                            }
                            else{
                                String act1,act2,act3,act4,act5,act6;
                                if(apci){act1 = "APCI" + "\n";}else {act1 = "";}
                                if (internet){act2 = "INTERNET" + "\n";}else {act2 = "";}
                                if (software){act3 = "SOFTWARE" + "\n";}else {act3 = "";}
                                if (hardware){act4 = "HARDWARE" + "\n";}else {act4 = "";}
                                if (electrico){act5 = "ELECTRICO" + "\n";}else {act5 = "";}
                                if (redes){act6 = "REDES Y CONECTIVIDAD" + "\n";}else {act6 = "";}
                                actividadRealizada= act1 + act2 + act3 + act4 + act5 + act6;

                            }


                            System.out.println("----------------FORMULARIO " + (i+1));
                            System.out.println("id: " + formulario_id);
                            System.out.println("id de visita" + visita_id + ". Se necesita visita con id: " + visita.getId());
                            System.out.println("accion tomada: " + action_taken);
                            System.out.println("observaciones: " + observation);
                            System.out.println("actividades: "); System.out.println(actividadRealizada);
                            if(visita_id == visita.getId()){
                                System.out.println("----------------ACEPTADO");
                                FormularioTecnico formTecnico = new FormularioTecnico(formulario_id,actividadRealizada,action_taken,observation);
                                formulariosDeVisita.add(formTecnico);


                            }
                            else {
                                System.out.println("----------------DESCARTADO");
                            }

                         }

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
                                    FormularioTecnico ftec = formulariosDeVisita.get(i);
                                    System.out.println("id: " + ftec.getId());


                                }

                                Collections.sort(formulariosDeVisita);
                                FormularioTecnico formularioGanador = formulariosDeVisita.get(0);
                                System.out.println("El formulario escogido tiene id: " + formularioGanador.getId());
                                accionTomada.setText(formularioGanador.getAccionTomada());
                                observaciones.setText(formularioGanador.getObservaciones());
                                actividadesRealizadas.setText(formularioGanador.getActividadesRealizadas());



                            }
                         }



                    }
                    else{
                        System.out.println("No hay formularios tecnicos en la BD");
                        barraCargando.setVisibility(View.GONE);


                    }


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
