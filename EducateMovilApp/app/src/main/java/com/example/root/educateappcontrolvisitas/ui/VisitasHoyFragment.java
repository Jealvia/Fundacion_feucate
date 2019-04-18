package com.example.root.educateappcontrolvisitas.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.VisitasClient;
import com.example.root.educateappcontrolvisitas.ui.adapter.VisitaAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.widget.AdapterView;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * create an instance of this fragment.
 */
public class VisitasHoyFragment extends Fragment {

    private ListView listView;
    private String fechaHoy;
    private String nombreUsuario;
    private String usuarioID;
    private List<Visita> visitasDeHoy;
    private List<Visita> todasLasVisitas;
    TextView errorTextview;
    private View barraCargando;
    private FloatingActionButton fab;




    public VisitasHoyFragment() {
        // Required empty public constructor
    }

    public static VisitasHoyFragment newInstance(String usuario_id) {
        VisitasHoyFragment visitasHoyFragment = new VisitasHoyFragment();
        Bundle args = new Bundle();
        args.putString("usuario_id", usuario_id);
        visitasHoyFragment.setArguments(args);
        return visitasHoyFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.usuarioID = getArguments().getString("usuario_id");
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.visitas_hoy_lista, container, false);


        listView = (ListView) rootView.findViewById(R.id.visitaList);
        if(usuarioID.equals("3")||usuarioID.equals("4")){

            System.out.println("Entro al floating");

            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            System.out.println("FLOARIN");

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent mainIntent = new Intent(getContext(), CrearVisita.class);
                    getActivity().startActivity(mainIntent);
                }
            });
        }else {
            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            System.out.println("FLOARIN");
            fab.hide();
        }


        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        fechaHoy = formatter.format(todayDate);
        MainActivity activity = (MainActivity) getActivity();
        nombreUsuario = activity.getmUsername();
        usuarioID = activity.getIdentificadorUsuario();
        visitasDeHoy = new ArrayList<Visita>();
        todasLasVisitas = new ArrayList<Visita>();
        barraCargando = (ProgressBar)rootView.findViewById(R.id.busqueda_visitas_hoy_progreso);
        errorTextview = (TextView) rootView.findViewById(R.id.errorViewHoy);
        errorTextview.setText("");
        barraCargando.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_backend))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        VisitasClient visitasClient = retrofit.create(VisitasClient.class);
        Call<JsonObject> call =  visitasClient.obtenerVisitas("system","ABC123456789",1000000);
        System.out.println("Que vaina: "+call.request().body()+"; "+call.request().toString()+";"+call.toString());
        final List<Visita> visitas = new ArrayList<Visita>();

        ///////////////////////////////////////

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null){

                    if(response.body() != null && response.body().getAsJsonArray("objects").size() > 0){
                        JsonElement todasLasVisitasdeBD = response.body().getAsJsonArray("objects");

                        int totalVisitas = ((JsonArray) todasLasVisitasdeBD).size();
                        System.out.println("---------------DESDE VISITAS HOY (" + fechaHoy + ")-------------------------------------" );

                        System.out.println("Hay " + totalVisitas + " visitas en la BD");
                        barraCargando.setVisibility(View.VISIBLE);




                        for(int i = 0;  i < totalVisitas ; i++){
                            JsonElement visita = ((JsonArray) todasLasVisitasdeBD).get(i);
                            System.out.println(""+visita );
                            //System.out.println("Visita N° " + (i+1));
                            //System.out.println(visita);
                            JsonElement visita_requerimiento = visita.getAsJsonObject().get("requirement");
                            JsonElement visita_user = visita.getAsJsonObject().get("user");

                            JsonElement check_in =  visita.getAsJsonObject().get("check_in");
                            String visita_checkIn;
                            if(check_in.isJsonNull()){
                                visita_checkIn = "null";

                            }
                            else{
                                visita_checkIn = visita.getAsJsonObject().get("check_in").getAsString();
                            }
                            JsonElement checkout = visita.getAsJsonObject().get("check_out");
                            String visita_checkOut;
                            if(checkout.isJsonNull()){
                                visita_checkOut = "null";
                            }
                            else{

                                visita_checkOut = visita.getAsJsonObject().get("check_out").getAsString();
                            }

                            JsonElement lat_in = visita.getAsJsonObject().get("coordinates_lat_in");
                            double visita_lat_in;
                            if(lat_in.isJsonNull()){
                                visita_lat_in = 0.0;
                            }
                            else {
                                visita_lat_in = visita.getAsJsonObject().get("coordinates_lat_in").getAsDouble();
                            }

                            JsonElement lat_out = visita.getAsJsonObject().get("coordinates_lat_out");
                            double visita_lat_out;
                            if(lat_out.isJsonNull()){
                                visita_lat_out = 0.0;
                            }
                            else{
                                visita_lat_out = visita.getAsJsonObject().get("coordinates_lat_out").getAsDouble();
                            }
                            JsonElement lon_in = visita.getAsJsonObject().get("coordinates_lon_in");
                            double visita_lon_in;
                            if(lon_in.isJsonNull()){
                                visita_lon_in = 0.0;
                            }
                            else {
                                visita_lon_in = visita.getAsJsonObject().get("coordinates_lon_in").getAsDouble();
                            }
                            JsonElement lon_out = visita.getAsJsonObject().get("coordinates_lon_out");
                            double visita_lon_out;
                            if(lon_out.isJsonNull()){
                                visita_lon_out = 0.0;
                            }
                            else{

                                visita_lon_out = visita.getAsJsonObject().get("coordinates_lon_out").getAsDouble();
                            }

                            String visita_date_planned = visita.getAsJsonObject().get("date_planned").getAsString();
                            int visita_id = visita.getAsJsonObject().get("id").getAsInt();
                            int requerimiento_id = visita_requerimiento.getAsJsonObject().get("id").getAsInt();
                            String requerimiento_reason = visita_requerimiento.getAsJsonObject().get("reason").getAsString();
                            JsonElement visita_Escuela = visita_requerimiento.getAsJsonObject().get("school");
                            String escuela_direccion = visita_Escuela.getAsJsonObject().get("address").getAsString();
                            String escuela_embajador_in;
                            if(visita_Escuela.getAsJsonObject().get("ambassador_in")==null){
                                escuela_embajador_in="";
                            }else {
                                escuela_embajador_in = visita_Escuela.getAsJsonObject().get("ambassador_in").getAsString();
                            }
                            int escuela_id = visita_Escuela.getAsJsonObject().get("id").getAsInt();
                            String escuela_amie = visita_Escuela.getAsJsonObject().get("amie").getAsString();
                            String escuela_nombre = visita_Escuela.getAsJsonObject().get("name").getAsString();
                            String escuela_parroquia;
                            if (visita_Escuela.getAsJsonObject().get("parish")==null){
                                escuela_parroquia="";
                            }else {
                                escuela_parroquia = visita_Escuela.getAsJsonObject().get("parish").getAsString();
                            }
                            int escuelaJornada;
                            if(visita_Escuela.getAsJsonObject().get("workday")==null){
                                escuelaJornada=0;
                            }else {
                                escuelaJornada = visita_Escuela.getAsJsonObject().get("workday").getAsInt();
                            }

                            String[] jornadas = {"","MATUTINA", "VESPERTINA","MATUTINA/VESPERTINA"};
                            String escuela_jornada = jornadas[escuelaJornada];
                            String escuela_referencia;
                            if (visita_Escuela.getAsJsonObject().get("reference")==null){
                                escuela_referencia="";
                            }else {
                                escuela_referencia = visita_Escuela.getAsJsonObject().get("reference").getAsString();
                            }
                            //JsonElement estado_visita = visita.getAsJsonObject().get("state");
                            int visita_estado;
                            if(visita.getAsJsonObject().get("state")==null){
                                visita_estado = 1;
                            }
                            else{
                                visita_estado = visita.getAsJsonObject().get("state").getAsInt();
                            }

                            //JsonElement visita_tipo_json = visita.getAsJsonObject().get("type");
                            int visita_tipo;
                            if(visita.getAsJsonObject().get("type")!=null){
                                visita_tipo = 1;//visita.getAsJsonObject().get("type").getAsInt();
                            }
                            else {
                                visita_tipo = 0;

                            }
                            int tipo_usuario = visita_user.getAsJsonObject().get("user_type").getAsInt();

                            int usuario_id = visita_user.getAsJsonObject().get("id").getAsInt();
                            String usuario_username = visita_user.getAsJsonObject().get("username").getAsString();
                            String usuario_nombre = visita_user.getAsJsonObject().get("first_name").getAsString();
                            String usuario_apellido = visita_user.getAsJsonObject().get("last_name").getAsString();

                            int idUsuario = Integer.parseInt(usuarioID);
                            System.out.println("-------------------------------------------------------------------------------------");
                            System.out.println("Visita N° " + i);
                            System.out.println("Datos de visita buscada " + fechaHoy + ",1," +nombreUsuario + ","+idUsuario);
                            System.out.println("Datos de visita actual " + visita_date_planned + ","+visita_estado+"," +usuario_username + ","+usuario_id);
                            boolean esDeHoy = visita_date_planned.contains(fechaHoy) && visita_estado==1 && usuario_username.equals(nombreUsuario) && usuario_id == idUsuario;
                            System.out.println("Es de hoy? " + esDeHoy);
                            if(esDeHoy && visita_estado==1){

                                Visita nuevaVisita = new Visita();

                                nuevaVisita.setCheck_in(visita_checkIn);
                                nuevaVisita.setCheck_out(visita_checkOut);
                                nuevaVisita.setCoordinates_lat_in(visita_lat_in);
                                nuevaVisita.setCoordinates_lon_in(visita_lon_in);
                                nuevaVisita.setCoordinates_lat_out(visita_lat_out);
                                nuevaVisita.setCoordinates_lon_out(visita_lon_out);
                                nuevaVisita.setDate_planned(visita_date_planned);
                                nuevaVisita.setId(visita_id);
                                nuevaVisita.setRequirement_id(requerimiento_id);
                                nuevaVisita.setRequirement_reason(requerimiento_reason);
                                nuevaVisita.setSchool_address(escuela_direccion);
                                nuevaVisita.setSchool_ambassador_in(escuela_embajador_in);
                                nuevaVisita.setSchool_amie(escuela_amie);
                                nuevaVisita.setSchool_name(escuela_nombre);
                                nuevaVisita.setSchool_parish(escuela_parroquia);
                                nuevaVisita.setSchool_reference(escuela_referencia);
                                nuevaVisita.setState(visita_estado);
                                nuevaVisita.setType(visita_tipo);
                                nuevaVisita.setUser_type(tipo_usuario);
                                nuevaVisita.setUser_id(usuario_id);
                                nuevaVisita.setUsername(usuario_username);
                                nuevaVisita.setSchool_workday(escuela_jornada);
                                nuevaVisita.setSchool_id(escuela_id);
                                nuevaVisita.setUser_first_name(usuario_nombre);
                                nuevaVisita.setUser_last_name(usuario_apellido);
                                visitasDeHoy.add(nuevaVisita);
                            }




                        }
                        if(visitasDeHoy == null){

                            System.out.println("No hay nada");
                            listView.setVisibility(View.GONE);
                            errorTextview.setText("NO HAY VISITAS QUE MOSTRAR");
                            barraCargando.setVisibility(View.GONE);

                        }
                        else{
                            System.out.println("Hay "+ visitasDeHoy.size() + " visitas de hoy");
                            listView.setAdapter(new VisitaAdapter(getActivity(), visitasDeHoy));

                            errorTextview.setText("NO HAY VISITAS QUE MOSTRAR");
                            barraCargando.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    Context context = getContext();
                                    CharSequence text = "Mostrando detalles de la visita...";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();

                                    Visita visita = visitasDeHoy.get(position);

                                    if(visita.getState() != 3){
                                        Context context2 = getContext();
                                        CharSequence text2 = "Detalles de la visita";
                                        int duration2 = Toast.LENGTH_SHORT;

                                        Toast toast2 = Toast.makeText(context2, text2, duration2);
                                        toast2.show();
                                        Intent mainIntent = new Intent(getActivity(), DetallesEscuela.class);
                                        mainIntent.putExtra("visitaActual",visita);

                                        startActivity(mainIntent);

                                    }
                                    else {
                                        Context context3 = getContext();
                                        CharSequence text3 = "Visita NO REALIZADA, no hay nada que mostrar";
                                        int duration3 = Toast.LENGTH_SHORT;

                                        Toast toast3 = Toast.makeText(context3, text3, duration3);
                                        toast3.show();

                                    }



                                }
                            });


                        }
                    }
                    else{

                        System.out.println("No hay nada");
                        listView.setVisibility(View.GONE);
                        errorTextview.setText("NO HAY VISITAS QUE MOSTRAR");
                        barraCargando.setVisibility(View.GONE);

                    }

                }
                else {
                    errorTextview.setText("No hay visitas que mostrar");
                    listView.setVisibility(View.GONE);
                    barraCargando.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                errorTextview.setText("NO HAY VISITAS QUE MOSTRAR");
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();

            }
        });





        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();


    }



}
