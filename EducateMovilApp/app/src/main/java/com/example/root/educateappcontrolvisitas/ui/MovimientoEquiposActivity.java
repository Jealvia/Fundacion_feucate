package com.example.root.educateappcontrolvisitas.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Equipo;
import com.example.root.educateappcontrolvisitas.api.model.EquipoAdapter;
import com.example.root.educateappcontrolvisitas.api.model.Evidencias;
import com.example.root.educateappcontrolvisitas.ui.adapter.EvidenciasAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.view.LayoutInflater;

public class MovimientoEquiposActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables para subactividad de firma
    private static final int CODIGO_ACTIVIDAD = 1;
    private String nombre_firma;

    //Variables para agregar evidencias(fotos)
    private final String CARPETA_RAIZ="Feducate/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Evidencias";
    private int numEvidencias=0;
    private String path;
    final int COD_MOSTRAR=10;
    final int COD_FOTO=20;
    final ArrayList<Evidencias> listaEvidencias= new ArrayList<Evidencias>(0);
    final ArrayList<Equipo> listaEquipos= new ArrayList<Equipo>(0);
    final EvidenciasAdapter evidenciasAdapter=new EvidenciasAdapter(this,listaEvidencias);

    //widgets
    private EditText textFecha,textHoraInicio,textHoraFin, editObs,editEmail;
    private CheckBox checkEntregar,checkRetiro;
    private ImageButton btnAgregarEquipo,btnAgregarEvidencias, btnAgregarFirma,
            ib_obtener_fecha,ib_obtener_hora_fin,ib_obtener_hora_inicio;
    private ListView equipos_dynamic,imagenes_dynamic;
    private Button btnAceptar,btnCancelar;
    private ImageView resultadoFirma;
    private ScrollView miScrollView;

    //Atributos para manejo de fechas y horas
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    public MovimientoEquiposActivity() {

    }


    //Inicializacion de la vista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_equipos);

        //Inicializan los widgets
        textHoraInicio = (EditText) findViewById(R.id.textHoraInicio);
        textHoraFin = (EditText) findViewById(R.id.textHoraFin);
        textFecha = (EditText) findViewById(R.id.textFecha);
        editObs = (EditText) findViewById(R.id.editObs);
        editEmail = (EditText) findViewById(R.id.editEmail);
        checkEntregar=(CheckBox)  findViewById(R.id.checkEntregar);
        checkRetiro=(CheckBox) findViewById(R.id.checkRetiro);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnAgregarEquipo=(ImageButton) findViewById(R.id.btnAgregarEquipo);
        btnAgregarEvidencias=(ImageButton) findViewById(R.id.btnAgregarEvidencias);
        btnAgregarFirma=(ImageButton) findViewById(R.id.btnAgregarFirma);
        ib_obtener_fecha=(ImageButton) findViewById(R.id.ib_obtener_fecha);
        ib_obtener_hora_fin=(ImageButton) findViewById(R.id.ib_obtener_hora_fin);
        ib_obtener_hora_inicio=(ImageButton) findViewById(R.id.ib_obtener_hora_inicio);
        equipos_dynamic=(ListView) findViewById(R.id.equipos_dynamic);
        imagenes_dynamic=(ListView) findViewById(R.id.evidencias_dynamic);
        resultadoFirma=(ImageView) findViewById(R.id.resultadoFirma);
        miScrollView=(ScrollView) findViewById(R.id.miScrollView);

        //Manejador de fechas y horas
        ib_obtener_fecha.setOnClickListener(this);
        ib_obtener_hora_fin.setOnClickListener(this);
        ib_obtener_hora_inicio.setOnClickListener(this);

        //Inicializacion de las lista de equipos

        EquipoAdapter equipoAdapter=new EquipoAdapter(this,listaEquipos);
        equipos_dynamic.setAdapter(equipoAdapter);

        //Inicializacion de la lista de imagenes


        imagenes_dynamic.setAdapter(evidenciasAdapter);


        //Manejador evento agregar equipo
        btnAgregarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = MovimientoEquiposActivity.this.getLayoutInflater();
                final View dialogView= inflater.inflate(R.layout.dialog_crear_equipo_layout,null);
                Toast.makeText(MovimientoEquiposActivity.this, "Ingrese los datos", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(MovimientoEquiposActivity.this);
                alert.setTitle("Ingresar nuevo equipo");
                alert.setView(dialogView);
                alert.setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText et_descripcion= dialogView.findViewById(R.id.et_descripcion);
                        EditText et_cantidad= dialogView.findViewById(R.id.et_cantidad);
                        EditText et_serie= dialogView.findViewById(R.id.et_serie);
                        EditText et_codigo= dialogView.findViewById(R.id.et_codigo);

                        String descripcion= et_descripcion.getText().toString();
                        String serie=et_serie.getText().toString();
                        String codigo=et_codigo.getText().toString();
                        if(isNumeric(et_cantidad.getText().toString())){
                            int cantidad= Integer.parseInt(et_cantidad.getText().toString());
                            Equipo prod = new Equipo(descripcion,cantidad,serie,codigo);
                            listaEquipos.add(prod);
                            Toast.makeText(MovimientoEquiposActivity.this, "Producto Agregado!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MovimientoEquiposActivity.this,
                                    "La cantidad fue mal ingresada", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });

        //Manejador evento agregar firma del colegia
        btnAgregarFirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovimientoEquiposActivity.this, FirmaActivity.class);
                intent.putExtra("EXTRA_MESSAGE", nombre_firma);
                startActivityForResult(intent, CODIGO_ACTIVIDAD);
            }
        });

        //Manejador evento agregar evidencias
        btnAgregarEvidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFotografia(listaEvidencias);
                evidenciasAdapter.notifyDataSetChanged();
            }
        });

        //Manejador evento boton aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("TOMO FOTO"+listaEvidencias.size());
                String fecha = textFecha.getText().toString();
                String horaFin = textHoraFin.getText().toString();
                String horaInicio= textHoraInicio.getText().toString();
                String observacion=editObs.getText().toString();
                String email=editEmail.getText().toString();
                if (fecha.equals("") || horaFin.equals("") || horaInicio.equals("")||
                        email.equals("")) {
                    Toast.makeText(MovimientoEquiposActivity.this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
                } else {
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


        miScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.equipos_dynamic).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                findViewById(R.id.evidencias_dynamic).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        equipos_dynamic.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        imagenes_dynamic.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
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
            case R.id.ib_obtener_hora_inicio:
                obtenerHora();
                break;
            case R.id.ib_obtener_hora_fin:
                obtenerHoraFin();
                break;
        }
    }

    //Manejador de fecha
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;

                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                textFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
        },anio, mes, dia);

        recogerFecha.show();

    }

    //Manejador de hora inicio
    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                textHoraInicio.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }

        }, hora, minuto, false);

        recogerHora.show();
    }

    //Manejador de hora fin
    private void obtenerHoraFin(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                textHoraFin.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }

        }, hora, minuto, false);

        recogerHora.show();
    }

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case CODIGO_ACTIVIDAD:
                if (resultCode == Activity.RESULT_OK) {
                    //LLenar la imagen con el valor
                    resultadoFirma.setImageResource(android.R.drawable.checkbox_on_background);
                }else {
                    resultadoFirma.setImageResource(android.R.drawable.checkbox_off_background);
                }
                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(this, new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                                File imagen=new File(path);
                                boolean prueba=imagen.exists();
                                //System.out.println(imagen.getUsableSpace());
                                if(prueba==false){
                                    System.out.println("NO TOMO FOTO");
                                    numEvidencias--;
                                }
                                if(prueba==true){
                                    System.out.println("PATH"+path);
                                    ///storage/emulated/0/Feducate/Evidencias/Evidencia 1.jpg
                                    String tmp[]=path.split("/");
                                    Evidencias evidencias=new Evidencias(tmp[tmp.length-1],path);
                                    listaEvidencias.add(evidencias);
                                    evidenciasAdapter.notifyDataSetChanged();
                                }
                                System.out.println("PRUEBITA"+path);


                            }
                        });

                //Actualizar el valor de imagenes
                break;
        }
    }


    private void tomarFotografia(ArrayList<Evidencias> listaEvidencias) {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }
        if(isCreada==true){
            numEvidencias++;
            nombreImagen="Evidencia_"+numEvidencias+".jpg";//AGREGA NOmbre imagen
        }
        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);
        System.out.println("SALIDA");



    }


}


//final AlertDialog.Builder alert = new AlertDialog.Builder(MovimientoEquiposActivity.this);
//alert.setTitle("Descripcion del Equipo");
//alert.setMessage(prod.getDescripcion());
//alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//    @Override
//      public void onClick(DialogInterface dialogInterface, int i) {
//        dialogInterface.dismiss();
//     }
// });
//      alert.show();