package com.example.root.educateappcontrolvisitas.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.VisitasClient;
import com.example.root.educateappcontrolvisitas.ui.adapter.VisitaAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.ui.WorkaroundMapFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetallesEscuela extends AppCompatActivity{
    private GoogleMap mMap;
    private ScrollView mScrollView;
    private String nombresUsuario;
    private String apellidosUsuario;
    private int usuarioID;

    private Button botonCHECKIN;
    private double latitud, longitud;
    private GpsTracker gpsTracker;
    private boolean noSeHaHechoCheckIn = true;
    private boolean visitaRealizada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escuela_item);

        final Visita visita = getIntent().getParcelableExtra("visitaActual");
        nombresUsuario = visita.getUsername();
        apellidosUsuario = visita.getUsername();
        usuarioID = visita.getUser_id();
        TextView visita_escuela_hora = (TextView) findViewById(R.id.visita_hora);
        String string = visita.getDate_planned();
        String[] parts = string.split("T");
        String fecha = parts[0]; // 004
        String hora = parts[1];
        System.out.println(string);
        System.out.println(fecha);
        System.out.println(hora.substring(0, 5));
        visita_escuela_hora.setText(hora);
        TextView visita_escuela_numero = (TextView) findViewById(R.id.visita_numero);
        int length = (int) (Math.log10(visita.getId()) + 1);
        String original = "N° ";
        char c = '0';
        int number = 9 - length;
        char[] repeat = new char[number];
        Arrays.fill(repeat, c);
        original += new String(repeat);
        visita_escuela_numero.setText(original + visita.getId());
        TextView visita_escuela_MIE = (TextView) findViewById(R.id.escuela_MIE);
        visita_escuela_MIE.setText(visita.getSchool_amie());
        TextView visita_escuela_nombre = (TextView) findViewById(R.id.nombre);
        visita_escuela_nombre.setText(visita.getSchool_name());
        TextView visita_escuela_direccion = (TextView) findViewById(R.id.direccion);
        visita_escuela_direccion.setText(visita.getSchool_address());
        TextView visita_escuela_jornada = (TextView) findViewById(R.id.escuela_jornada);
        visita_escuela_jornada.setText(visita.getSchool_workday());

        TextView visita_escuela_parroquia = (TextView) findViewById(R.id.parroquia);
        visita_escuela_parroquia.setText(visita.getSchool_parish());

        TextView visita_escuela_motivo = (TextView) findViewById(R.id.motivo);
        visita_escuela_motivo.setText(visita.getRequirement_reason());
        TextView visita_escuela_embajadorIN = (TextView) findViewById(R.id.embajadorIN);
        visita_escuela_embajadorIN.setText(visita.getSchool_ambassador_in());


        botonCHECKIN = (Button) findViewById(R.id.botonCHECKIN);

        noSeHaHechoCheckIn= visita.getCoordinates_lat_in() == 0.0 && visita.getCoordinates_lon_in() == 0.0;
        visitaRealizada= visita.getState() == 2;

        if(visitaRealizada){
            botonCHECKIN.setText("VER FORMULARIO");

        }
        else {
            if(noSeHaHechoCheckIn){
                botonCHECKIN.setText("CHECK IN");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    try {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
// check if permission is granted or not
                }

            }
            else{
                botonCHECKIN.setText("LLENAR FORMULARIO");
            }

        }




        botonCHECKIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(visitaRealizada){
                    //AQUI DEBE MOSTRAR LOS FORMULARIOS QUE SE HAN LLENADO
                    if(visita.getType() == 2 || visita.getUser_type() == 2 || visita.getUser_type() == 4){//MUESTRA FORMULARIO TECNICO

                        Intent mostrarFormularioTecnico = new Intent(getApplicationContext(), mostrarFormularioTecnicoActivity.class);
                        mostrarFormularioTecnico.putExtra("visitaActual",visita);
                        startActivity(mostrarFormularioTecnico);
                    }
                    else if(visita.getType() == 1){
                        Intent mostrarFormularioPedagogico = new Intent(getApplicationContext(), mostrarFormularioPedagogico.class);
                        mostrarFormularioPedagogico.putExtra("visitaActual",visita);
                        startActivity(mostrarFormularioPedagogico);

                    }
                    else {
                        Intent mostrarFormularioPedagogico = new Intent(getApplicationContext(), escogerFormIngresado.class);
                        mostrarFormularioPedagogico.putExtra("visitaActual",visita);
                        startActivity(mostrarFormularioPedagogico);
                    }
                }
                else{

                    if(noSeHaHechoCheckIn){
                        //Aqui va el post
                        String usuarioFK = "/serviceweb/api/v1/usuario/" + visita.getUser_id() + "/";
                        String requerimientoFK = "/serviceweb/api/v1/requirement/"+visita.getRequirement_id()+ "/";
                        Date todayDate = Calendar.getInstance().getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final String fechaHoy = formatter.format(todayDate);
                        getLocation();

                        Retrofit.Builder builder = new Retrofit.Builder()
                                .baseUrl(getString(R.string.url_backend))
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();

                        VisitasClient visitasClient = retrofit.create(VisitasClient.class);
                        Call<Void> call =  visitasClient.checkIn("system","ABC123456789",1000000,usuarioFK,requerimientoFK,visita.getDate_planned()
                                ,fechaHoy,latitud,longitud,visita.getState(),visita.getType(),visita.getId());

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                noSeHaHechoCheckIn = false;
                                botonCHECKIN.setText("LLENAR FORMULARIO");
                                visita.setCheck_in(fechaHoy);
                                visita.setCoordinates_lat_in(latitud);
                                visita.setCoordinates_lon_in(longitud);
                                if(visita.getUser_type() == 1 || visita.getUser_type() == 3){//TUTOR Y TUTO LEADER PUEDEN LLENAR AMBOS FORMULARIOS
                                    Intent escogerFormularios = new Intent(getApplicationContext(), EscogerFormularioActivity.class);
                                    escogerFormularios.putExtra("visitaActual",visita);
                                    startActivity(escogerFormularios);
                                }
                                else {//LOS TECH Y TECH LEADER SOLO PUEDEN LLENAR FORMULARIOS TECNICOS
                                    visita.setType(2);//el tipo de visita es tecnica(2)
                                    Intent formularioTecnico = new Intent(getApplicationContext(), TecnicoFormularioActivity.class);
                                    formularioTecnico.putExtra("visitaActual",visita);
                                    startActivity(formularioTecnico);
                                }

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {



                            }
                        });

                    }
                    else {

                        if (visita.getUser_type() == 1 || visita.getUser_type() == 3) {
                            Intent escogerFormularios = new Intent(getApplicationContext(), EscogerFormularioActivity.class);
                            escogerFormularios.putExtra("visitaActual", visita);
                            startActivity(escogerFormularios);
                        } else {
                            Intent formularioTecnico = new Intent(getApplicationContext(), TecnicoFormularioActivity.class);
                            formularioTecnico.putExtra("visitaActual", visita);
                            startActivity(formularioTecnico);
                        }
                    }

                }



            }
        });


        if (mMap == null) {
            SupportMapFragment mapFragment = (WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap)
                {
                    mMap = googleMap;
                    mMap.setMinZoomPreference(19);

                    LatLng escuelita = getLocationFromAddress(visita.getSchool_address());
                    System.out.println("Escuelita"+escuelita);
                    mMap.addMarker(new MarkerOptions().position(escuelita).title(visita.getSchool_name()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(escuelita));
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                    mScrollView = findViewById(R.id.scrollEscuela); //parent scrollview in xml, give your scrollview id value
                    ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                            .setListener(new WorkaroundMapFragment.OnTouchListener() {
                                @Override
                                public void onTouch()
                                {
                                    mScrollView.requestDisallowInterceptTouchEvent(true);
                                }

                            });
                }
            });
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

            return p1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(DetallesEscuela.this);
        if(gpsTracker.canGetLocation()){
            this.latitud = gpsTracker.getLatitude();
            this.longitud = gpsTracker.getLongitude();

        }else{
            gpsTracker.showSettingsAlert();
        }
    }




}

