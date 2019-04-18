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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.formularioPedagogicoClient;
import com.example.root.educateappcontrolvisitas.api.service.formularioTecnicoClient;

import java.util.Arrays;

public class CrearFormularioPedagogico extends AppCompatActivity {
    private Button btnCancelar;
    private Button btnSiguiente;
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
    private CheckBox chboc_extracurricular;
    private CheckBox chbox_internet;
    private EditText accionTomada;
    private EditText noProf;
    private EditText minutosAPCI;
    private EditText AI_2doEGB_A;
    private EditText AI_3roEGB_A;
    private EditText AI_4toEGB_A;
    private EditText AI_5toEGB_A;
    private EditText AI_6toEGB_A;
    private EditText AI_7moEGB_A;
    private EditText AI_8voEGB_A;
    private EditText AI_9noEGB_A;
    private EditText AI_10moEGB_A;
    private EditText AI_2doEGB_B;
    private EditText AI_3roEGB_B;
    private EditText AI_4toEGB_B;
    private EditText AI_5toEGB_B;
    private EditText AI_6toEGB_B;
    private EditText AI_7moEGB_B;
    private EditText AI_8voEGB_B;
    private EditText AI_9noEGB_B;
    private EditText AI_10moEGB_B;
    private EditText AI_2doEGB_C;
    private EditText AI_3roEGB_C;
    private EditText AI_4toEGB_C;
    private EditText AI_5toEGB_C;
    private EditText AI_6toEGB_C;
    private EditText AI_7moEGB_C;
    private EditText AI_8voEGB_C;
    private EditText AI_9noEGB_C;
    private EditText AI_10moEGB_C;
    private EditText AT_2doEGB_LEN;
    private EditText AT_3roEGB_LEN;
    private EditText AT_4toEGB_LEN;
    private EditText AT_5toEGB_LEN;
    private EditText AT_6toEGB_LEN;
    private EditText AT_7moEGB_LEN;
    private EditText AT_8voEGB_LEN;
    private EditText AT_9noEGB_LEN;
    private EditText AT_10moEGB_LEN;
    private EditText AT_2doEGB_MAT;
    private EditText AT_3roEGB_MAT;
    private EditText AT_4toEGB_MAT;
    private EditText AT_5toEGB_MAT;
    private EditText AT_6toEGB_MAT;
    private EditText AT_7moEGB_MAT;
    private EditText AT_8voEGB_MAT;
    private EditText AT_9noEGB_MAT;
    private EditText AT_10moEGB_MAT;

    private EditText LA_2doEGB_LEN;
    private EditText LA_3roEGB_LEN;
    private EditText LA_4toEGB_LEN;
    private EditText LA_5toEGB_LEN;
    private EditText LA_6toEGB_LEN;
    private EditText LA_7moEGB_LEN;
    private EditText LA_8voEGB_LEN;
    private EditText LA_9noEGB_LEN;
    private EditText LA_10moEGB_LEN;
    private EditText LA_2doEGB_MAT;
    private EditText LA_3roEGB_MAT;
    private EditText LA_4toEGB_MAT;
    private EditText LA_5toEGB_MAT;
    private EditText LA_6toEGB_MAT;
    private EditText LA_7moEGB_MAT;
    private EditText LA_8voEGB_MAT;
    private EditText LA_9noEGB_MAT;
    private EditText LA_10moEGB_MAT;
    private EditText PA_2doEGB_MAT;
    private EditText PA_2doEGB_LEN;
    private EditText PA_3roEGB_MAT;
    private EditText PA_3roEGB_LEN;
    private EditText PA_4toEGB_MAT;
    private EditText PA_4toEGB_LEN;
    private EditText PA_5toEGB_MAT;
    private EditText PA_5toEGB_LEN;
    private EditText PA_6toEGB_MAT;
    private EditText PA_6toEGB_LEN;
    private EditText PA_7moEGB_MAT;
    private EditText PA_7moEGB_LEN;
    private EditText PA_8voEGB_MAT;
    private EditText PA_8voEGB_LEN;
    private EditText PA_9noEGB_MAT;
    private EditText PA_9noEGB_LEN;
    private EditText PA_10moEGB_MAT;
    private EditText PA_10moEGB_LEN;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_formulario_pedagogico);
        final Visita visita = getIntent().getParcelableExtra("visitaActual");


        scrollEscuela = (ScrollView) findViewById(R.id.scrollFormularioPedagogico);
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

        //Aqui empieza lo importante

        chboc_extracurricular = (CheckBox) findViewById(R.id.chboc_extracurricular);
        chbox_internet = (CheckBox) findViewById(R.id.chbox_internet);

        accionTomada = (EditText) findViewById(R.id.accionTomadaPedagogica);
        noProf = (EditText) findViewById(R.id.noProfesoresCapacitados);
        minutosAPCI = (EditText) findViewById(R.id.noMinutosAPCI);

        //Celdas de las tablas

        AI_2doEGB_A = (EditText) findViewById(R.id.ai2DOEGB_A);
        AI_3roEGB_A = (EditText) findViewById(R.id.ai3ROEGB_A);
        AI_4toEGB_A = (EditText) findViewById(R.id.ai4TOEGB_A);
        AI_5toEGB_A = (EditText) findViewById(R.id.ai5TOEGB_A);
        AI_6toEGB_A = (EditText) findViewById(R.id.ai6TOEGB_A);
        AI_7moEGB_A = (EditText) findViewById(R.id.ai7MOEGB_A);
        AI_8voEGB_A = (EditText) findViewById(R.id.ai8VOEGB_A);
        AI_9noEGB_A = (EditText) findViewById(R.id.ai9NOEGB_A);
        AI_10moEGB_A = (EditText) findViewById(R.id.ai10MOEGB_A);

        AI_2doEGB_B = (EditText) findViewById(R.id.ai2DOEGB_B);
        AI_3roEGB_B = (EditText) findViewById(R.id.ai3ROEGB_B);
        AI_4toEGB_B = (EditText) findViewById(R.id.ai4TOEGB_B);
        AI_5toEGB_B = (EditText) findViewById(R.id.ai5TOEGB_B);
        AI_6toEGB_B = (EditText) findViewById(R.id.ai6TOEGB_B);
        AI_7moEGB_B = (EditText) findViewById(R.id.ai7MOEGB_B);
        AI_8voEGB_B = (EditText) findViewById(R.id.ai8VOEGB_B);
        AI_9noEGB_B = (EditText) findViewById(R.id.ai9NOEGB_B);
        AI_10moEGB_B = (EditText) findViewById(R.id.ai10MOEGB_B);

        AI_2doEGB_C = (EditText) findViewById(R.id.ai2DOEGB_C);
        AI_3roEGB_C = (EditText) findViewById(R.id.ai3ROEGB_C);
        AI_4toEGB_C = (EditText) findViewById(R.id.ai4TOEGB_C);
        AI_5toEGB_C = (EditText) findViewById(R.id.ai5TOEGB_C);
        AI_6toEGB_C = (EditText) findViewById(R.id.ai6TOEGB_C);
        AI_7moEGB_C = (EditText) findViewById(R.id.ai7MOEGB_C);
        AI_8voEGB_C = (EditText) findViewById(R.id.ai8VOEGB_C);
        AI_9noEGB_C = (EditText) findViewById(R.id.ai9NOEGB_C);
        AI_10moEGB_C = (EditText) findViewById(R.id.ai10MOEGB_C);

        AT_2doEGB_LEN = (EditText) findViewById(R.id.AT_2DOEGB_LEN);
        AT_3roEGB_LEN = (EditText) findViewById(R.id.AT_3ROEGB_LEN);
        AT_4toEGB_LEN = (EditText) findViewById(R.id.AT_4TOEGB_LEN);
        AT_5toEGB_LEN = (EditText) findViewById(R.id.AT_5TOEGB_LEN);
        AT_6toEGB_LEN = (EditText) findViewById(R.id.AT_6TOEGB_LEN);
        AT_7moEGB_LEN = (EditText) findViewById(R.id.AT_7MOEGB_LEN);
        AT_8voEGB_LEN = (EditText) findViewById(R.id.AT_8VOEGB_LEN);
        AT_9noEGB_LEN = (EditText) findViewById(R.id.AT_9NOEGB_LEN);
        AT_10moEGB_LEN = (EditText) findViewById(R.id.AT_10MOEGB_LEN);

        AT_2doEGB_MAT = (EditText) findViewById(R.id.AT_2DOEGB_MAT);
        AT_3roEGB_MAT = (EditText) findViewById(R.id.AT_3ROEGB_MAT);
        AT_4toEGB_MAT = (EditText) findViewById(R.id.AT_4TOEGB_MAT);
        AT_5toEGB_MAT = (EditText) findViewById(R.id.AT_5TOEGB_MAT);
        AT_6toEGB_MAT = (EditText) findViewById(R.id.AT_6TOEGB_MAT);
        AT_7moEGB_MAT = (EditText) findViewById(R.id.AT_7MOEGB_MAT);
        AT_8voEGB_MAT = (EditText) findViewById(R.id.AT_8VOEGB_MAT);
        AT_9noEGB_MAT = (EditText) findViewById(R.id.AT_9NOEGB_MAT);
        AT_10moEGB_MAT = (EditText) findViewById(R.id.AT_10MOEGB_MAT);

        LA_2doEGB_LEN = (EditText) findViewById(R.id.LA_2DOEGB_LEN);
        LA_3roEGB_LEN = (EditText) findViewById(R.id.LA_3ROEGB_LEN);
        LA_4toEGB_LEN = (EditText) findViewById(R.id.LA_4TOEGB_LEN);
        LA_5toEGB_LEN = (EditText) findViewById(R.id.LA_5TOEGB_LEN);
        LA_6toEGB_LEN = (EditText) findViewById(R.id.LA_6TOEGB_LEN);
        LA_7moEGB_LEN = (EditText) findViewById(R.id.LA_7MOEGB_LEN);
        LA_8voEGB_LEN = (EditText) findViewById(R.id.LA_8VOEGB_LEN);
        LA_9noEGB_LEN = (EditText) findViewById(R.id.LA_9NOEGB_LEN);
        LA_10moEGB_LEN = (EditText) findViewById(R.id.LA_10MOEGB_LEN);

        LA_2doEGB_MAT = (EditText) findViewById(R.id.LA_2DOEGB_MAT);
        LA_3roEGB_MAT = (EditText) findViewById(R.id.LA_3ROEGB_MAT);
        LA_4toEGB_MAT = (EditText) findViewById(R.id.LA_4TOEGB_MAT);
        LA_5toEGB_MAT = (EditText) findViewById(R.id.LA_5TOEGB_MAT);
        LA_6toEGB_MAT = (EditText) findViewById(R.id.LA_6TOEGB_MAT);
        LA_7moEGB_MAT = (EditText) findViewById(R.id.LA_7MOEGB_MAT);
        LA_8voEGB_MAT = (EditText) findViewById(R.id.LA_8VOEGB_MAT);
        LA_9noEGB_MAT = (EditText) findViewById(R.id.LA_9NOEGB_MAT);
        LA_10moEGB_MAT = (EditText) findViewById(R.id.LA_10MOEGB_MAT);

        PA_2doEGB_LEN = (EditText) findViewById(R.id.PA_2DOEGB_LEN);
        PA_3roEGB_LEN = (EditText) findViewById(R.id.PA_3ROEGB_LEN);
        PA_4toEGB_LEN = (EditText) findViewById(R.id.PA_4TOEGB_LEN);
        PA_5toEGB_LEN = (EditText) findViewById(R.id.PA_5TOEGB_LEN);
        PA_6toEGB_LEN = (EditText) findViewById(R.id.PA_6TOEGB_LEN);
        PA_7moEGB_LEN = (EditText) findViewById(R.id.PA_7MOEGB_LEN);
        PA_8voEGB_LEN = (EditText) findViewById(R.id.PA_8VOEGB_LEN);
        PA_9noEGB_LEN = (EditText) findViewById(R.id.PA_9NOEGB_LEN);
        PA_10moEGB_LEN = (EditText) findViewById(R.id.PA_10MOEGB_LEN);

        PA_2doEGB_MAT = (EditText) findViewById(R.id.PA_2DOEGB_MAT);
        PA_3roEGB_MAT = (EditText) findViewById(R.id.PA_3ROEGB_MAT);
        PA_4toEGB_MAT = (EditText) findViewById(R.id.PA_4TOEGB_MAT);
        PA_5toEGB_MAT = (EditText) findViewById(R.id.PA_5TOEGB_MAT);
        PA_6toEGB_MAT = (EditText) findViewById(R.id.PA_6TOEGB_MAT);
        PA_7moEGB_MAT = (EditText) findViewById(R.id.PA_7MOEGB_MAT);
        PA_8voEGB_MAT = (EditText) findViewById(R.id.PA_8VOEGB_MAT);
        PA_9noEGB_MAT = (EditText) findViewById(R.id.PA_9NOEGB_MAT);
        PA_10moEGB_MAT = (EditText) findViewById(R.id.PA_10MOEGB_MAT);


        //Celdas de las tablas





        btnCancelar = (Button)findViewById(R.id.btn_cancelarPedagogico);
        btnSiguiente = (Button)findViewById(R.id.btn_siguientePedagogico);



        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean primeraParteVacia,tablaAIVacia,tablaATVacia,tablaLAVacia,tablaPAVacia, camposVacios;
                /*
                String AI_2A = AI_2doEGB_A.getText().toString();
                String AI_3A = AI_3roEGB_A.getText().toString();
                String AI_4A = AI_4toEGB_A.getText().toString();
                String AI_5A = AI_5toEGB_A.getText().toString();
                String AI_6A = AI_6toEGB_A.getText().toString();
                String AI_7A = AI_7moEGB_A.getText().toString();
                String AI_8A = AI_8voEGB_A.getText().toString();
                String AI_9A = AI_9noEGB_A.getText().toString();
                String AI_10A = AI_10moEGB_A.getText().toString();

                String AI_2B = AI_2doEGB_B.getText().toString();
                String AI_3B = AI_3roEGB_B.getText().toString();
                String AI_4B = AI_4toEGB_B.getText().toString();
                String AI_5B = AI_5toEGB_B.getText().toString();
                String AI_6B = AI_6toEGB_B.getText().toString();
                String AI_7B = AI_7moEGB_B.getText().toString();
                String AI_8B = AI_8voEGB_B.getText().toString();
                String AI_9B = AI_9noEGB_B.getText().toString();
                String AI_10B = AI_10moEGB_B.getText().toString();

                String AI_2C = AI_2doEGB_C.getText().toString();
                String AI_3C = AI_3roEGB_C.getText().toString();
                String AI_4C = AI_4toEGB_C.getText().toString();
                String AI_5C = AI_5toEGB_C.getText().toString();
                String AI_6C = AI_6toEGB_C.getText().toString();
                String AI_7C = AI_7moEGB_C.getText().toString();
                String AI_8C = AI_8voEGB_C.getText().toString();
                String AI_9C = AI_9noEGB_C.getText().toString();
                String AI_10C = AI_10moEGB_C.getText().toString();

                String AT_2_LEN = AT_2doEGB_LEN.getText().toString();
                String AT_3_LEN = AT_3roEGB_LEN.getText().toString();
                String AT_4_LEN = AT_4toEGB_LEN.getText().toString();
                String AT_5_LEN = AT_5toEGB_LEN.getText().toString();
                String AT_6_LEN = AT_6toEGB_LEN.getText().toString();
                String AT_7_LEN = AT_7moEGB_LEN.getText().toString();
                String AT_8_LEN = AT_8voEGB_LEN.getText().toString();
                String AT_9_LEN = AT_9noEGB_LEN.getText().toString();
                String AT_10_LEN = AT_10moEGB_LEN.getText().toString();

                String AT_2_MAT = AT_2doEGB_MAT.getText().toString();
                String AT_3_MAT = AT_3roEGB_MAT.getText().toString();
                String AT_4_MAT = AT_4toEGB_MAT.getText().toString();
                String AT_5_MAT = AT_5toEGB_MAT.getText().toString();
                String AT_6_MAT = AT_6toEGB_MAT.getText().toString();
                String AT_7_MAT = AT_7moEGB_MAT.getText().toString();
                String AT_8_MAT = AT_8voEGB_MAT.getText().toString();
                String AT_9_MAT = AT_9noEGB_MAT.getText().toString();
                String AT_10_MAT = AT_10moEGB_MAT.getText().toString();

                String LA_2_LEN = LA_2doEGB_LEN.getText().toString();
                String LA_3_LEN = LA_3roEGB_LEN.getText().toString();
                String LA_4_LEN = LA_4toEGB_LEN.getText().toString();
                String LA_5_LEN = LA_5toEGB_LEN.getText().toString();
                String LA_6_LEN = LA_6toEGB_LEN.getText().toString();
                String LA_7_LEN = LA_7moEGB_LEN.getText().toString();
                String LA_8_LEN = LA_8voEGB_LEN.getText().toString();
                String LA_9_LEN = LA_9noEGB_LEN.getText().toString();
                String LA_10_LEN = LA_10moEGB_LEN.getText().toString();

                String LA_2_MAT = LA_2doEGB_MAT.getText().toString();
                String LA_3_MAT = LA_3roEGB_MAT.getText().toString();
                String LA_4_MAT = LA_4toEGB_MAT.getText().toString();
                String LA_5_MAT = LA_5toEGB_MAT.getText().toString();
                String LA_6_MAT = LA_6toEGB_MAT.getText().toString();
                String LA_7_MAT = LA_7moEGB_MAT.getText().toString();
                String LA_8_MAT = LA_8voEGB_MAT.getText().toString();
                String LA_9_MAT = LA_9noEGB_MAT.getText().toString();
                String LA_10_MAT = LA_10moEGB_MAT.getText().toString();

                String PA_2_LEN = PA_2doEGB_LEN.getText().toString();
                String PA_3_LEN = PA_3roEGB_LEN.getText().toString();
                String PA_4_LEN = PA_4toEGB_LEN.getText().toString();
                String PA_5_LEN = PA_5toEGB_LEN.getText().toString();
                String PA_6_LEN = PA_6toEGB_LEN.getText().toString();
                String PA_7_LEN = PA_7moEGB_LEN.getText().toString();
                String PA_8_LEN = PA_8voEGB_LEN.getText().toString();
                String PA_9_LEN = PA_9noEGB_LEN.getText().toString();
                String PA_10_LEN = PA_10moEGB_LEN.getText().toString();

                String PA_2_MAT = PA_2doEGB_MAT.getText().toString();
                String PA_3_MAT = PA_3roEGB_MAT.getText().toString();
                String PA_4_MAT = PA_4toEGB_MAT.getText().toString();
                String PA_5_MAT = PA_5toEGB_MAT.getText().toString();
                String PA_6_MAT = PA_6toEGB_MAT.getText().toString();
                String PA_7_MAT = PA_7moEGB_MAT.getText().toString();
                String PA_8_MAT = PA_8voEGB_MAT.getText().toString();
                String PA_9_MAT = PA_9noEGB_MAT.getText().toString();
                String PA_10_MAT = PA_10moEGB_MAT.getText().toString();




                tablaAIVacia = TextUtils.isEmpty(AI_2A) || TextUtils.isEmpty(AI_3A) || TextUtils.isEmpty(AI_4A) || TextUtils.isEmpty(AI_5A) || TextUtils.isEmpty(AI_6A)
                                || TextUtils.isEmpty(AI_7A) || TextUtils.isEmpty(AI_8A) || TextUtils.isEmpty(AI_9A) || TextUtils.isEmpty(AI_10A) ||
                        TextUtils.isEmpty(AI_2B) || TextUtils.isEmpty(AI_3B) || TextUtils.isEmpty(AI_4B) || TextUtils.isEmpty(AI_5B) || TextUtils.isEmpty(AI_6B)
                        || TextUtils.isEmpty(AI_7B) || TextUtils.isEmpty(AI_8B) || TextUtils.isEmpty(AI_9B) || TextUtils.isEmpty(AI_10B) ||
                        TextUtils.isEmpty(AI_2C) || TextUtils.isEmpty(AI_3C) || TextUtils.isEmpty(AI_4C) || TextUtils.isEmpty(AI_5C) || TextUtils.isEmpty(AI_6C)
                        || TextUtils.isEmpty(AI_7C) || TextUtils.isEmpty(AI_8C) || TextUtils.isEmpty(AI_9C) || TextUtils.isEmpty(AI_10C);

                tablaATVacia = TextUtils.isEmpty(AT_2_LEN) || TextUtils.isEmpty(AT_3_LEN) || TextUtils.isEmpty(AT_4_LEN) || TextUtils.isEmpty(AT_5_LEN) || TextUtils.isEmpty(AT_6_LEN)
                        || TextUtils.isEmpty(AT_7_LEN) || TextUtils.isEmpty(AT_8_LEN) || TextUtils.isEmpty(AT_9_LEN) || TextUtils.isEmpty(AT_10_LEN) ||
                        TextUtils.isEmpty(AT_2_MAT) || TextUtils.isEmpty(AT_3_MAT) || TextUtils.isEmpty(AT_4_MAT) || TextUtils.isEmpty(AT_5_MAT) || TextUtils.isEmpty(AT_6_MAT)
                        || TextUtils.isEmpty(AT_7_MAT) || TextUtils.isEmpty(AT_8_MAT) || TextUtils.isEmpty(AT_9_MAT) || TextUtils.isEmpty(AT_10_MAT);

                tablaLAVacia = TextUtils.isEmpty(LA_2_LEN) || TextUtils.isEmpty(LA_3_LEN) || TextUtils.isEmpty(LA_4_LEN) || TextUtils.isEmpty(LA_5_LEN) || TextUtils.isEmpty(LA_6_LEN)
                        || TextUtils.isEmpty(LA_7_LEN) || TextUtils.isEmpty(LA_8_LEN) || TextUtils.isEmpty(LA_9_LEN) || TextUtils.isEmpty(LA_10_LEN) ||
                        TextUtils.isEmpty(LA_2_MAT) || TextUtils.isEmpty(LA_3_MAT) || TextUtils.isEmpty(LA_4_MAT) || TextUtils.isEmpty(LA_5_MAT) || TextUtils.isEmpty(LA_6_MAT)
                        || TextUtils.isEmpty(LA_7_MAT) || TextUtils.isEmpty(LA_8_MAT) || TextUtils.isEmpty(LA_9_MAT) || TextUtils.isEmpty(LA_10_MAT);

                tablaPAVacia = TextUtils.isEmpty(PA_2_LEN) || TextUtils.isEmpty(PA_3_LEN) || TextUtils.isEmpty(PA_4_LEN) || TextUtils.isEmpty(PA_5_LEN) || TextUtils.isEmpty(PA_6_LEN)
                        || TextUtils.isEmpty(PA_7_LEN) || TextUtils.isEmpty(PA_8_LEN) || TextUtils.isEmpty(PA_9_LEN) || TextUtils.isEmpty(PA_10_LEN) ||
                        TextUtils.isEmpty(PA_2_MAT) || TextUtils.isEmpty(PA_3_MAT) || TextUtils.isEmpty(PA_4_MAT) || TextUtils.isEmpty(PA_5_MAT) || TextUtils.isEmpty(PA_6_MAT)
                        || TextUtils.isEmpty(PA_7_MAT) || TextUtils.isEmpty(PA_8_MAT) || TextUtils.isEmpty(PA_9_MAT) || TextUtils.isEmpty(PA_10_MAT);

*/

                primeraParteVacia = TextUtils.isEmpty(noProf.getText().toString()) || TextUtils.isEmpty(minutosAPCI.getText().toString()) || TextUtils.isEmpty(accionTomada.getText().toString());
                camposVacios = primeraParteVacia;// || tablaAIVacia || tablaATVacia || tablaLAVacia || tablaPAVacia;

                if(camposVacios){
                    Context context = getApplicationContext();
                    CharSequence text = "¡LLENAR LOS CAMPOS OBLIGATORIOS(*)!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
                else{
                    //Obtengo los valores

                    String nprof = noProf.getText().toString();
                    int noProf = Integer.parseInt(nprof);
                    String minApci = minutosAPCI.getText().toString();
                    double minAPCI = Double.parseDouble(minApci);
                    String accion = accionTomada.getText().toString();
/*
                    double AI_2_A = Double.parseDouble(AI_2A);
                    double AI_3_A = Double.parseDouble(AI_3A);
                    double AI_4_A = Double.parseDouble(AI_4A);
                    double AI_5_A = Double.parseDouble(AI_5A);
                    double AI_6_A = Double.parseDouble(AI_6A);
                    double AI_7_A = Double.parseDouble(AI_7A);
                    double AI_8_A = Double.parseDouble(AI_8A);
                    double AI_9_A = Double.parseDouble(AI_9A);
                    double AI_10_A = Double.parseDouble(AI_10A);
                    double AI_total_A = AI_2_A + AI_3_A + AI_4_A + AI_5_A + AI_6_A + AI_7_A + AI_8_A + AI_9_A + AI_10_A;

                    double AI_2_B = Double.parseDouble(AI_2B);
                    double AI_3_B = Double.parseDouble(AI_3B);
                    double AI_4_B = Double.parseDouble(AI_4B);
                    double AI_5_B = Double.parseDouble(AI_5B);
                    double AI_6_B = Double.parseDouble(AI_6B);
                    double AI_7_B = Double.parseDouble(AI_7B);
                    double AI_8_B = Double.parseDouble(AI_8B);
                    double AI_9_B = Double.parseDouble(AI_9B);
                    double AI_10_B = Double.parseDouble(AI_10B);
                    double AI_total_B = AI_2_B + AI_3_B + AI_4_B + AI_5_B + AI_6_B + AI_7_B + AI_8_B + AI_9_B + AI_10_B;


                    double AI_2_C = Double.parseDouble(AI_2C);
                    double AI_3_C = Double.parseDouble(AI_3C);
                    double AI_4_C = Double.parseDouble(AI_4C);
                    double AI_5_C = Double.parseDouble(AI_5C);
                    double AI_6_C = Double.parseDouble(AI_6C);
                    double AI_7_C = Double.parseDouble(AI_7C);
                    double AI_8_C = Double.parseDouble(AI_8C);
                    double AI_9_C = Double.parseDouble(AI_9C);
                    double AI_10_C = Double.parseDouble(AI_10C);
                    double AI_total_C = AI_2_C + AI_3_C + AI_4_C + AI_5_C + AI_6_C + AI_7_C + AI_8_C + AI_9_C + AI_10_C;

                    double ai_total_2 = AI_2_A + AI_2_B + AI_2_C;
                    double ai_total_3 = AI_3_A + AI_3_B + AI_3_C;
                    double ai_total_4 = AI_4_A + AI_4_B + AI_4_C;
                    double ai_total_5 = AI_5_A + AI_5_B + AI_5_C;
                    double ai_total_6 = AI_6_A + AI_6_B + AI_6_C;
                    double ai_total_7 = AI_7_A + AI_7_B + AI_7_C;
                    double ai_total_8 = AI_8_A + AI_8_B + AI_8_C;
                    double ai_total_9 = AI_9_A + AI_9_B + AI_9_C;
                    double ai_total_10 = AI_10_A + AI_10_B + AI_10_C;
                    double ai_total_promedio = (ai_total_2 + ai_total_3 + ai_total_4+ ai_total_5 + ai_total_6 + ai_total_7 + ai_total_8 + ai_total_9 + ai_total_10)/9;

                    double at_2_LEN = Double.parseDouble(AT_2_LEN);
                    double at_3_LEN = Double.parseDouble(AT_3_LEN);
                    double at_4_LEN = Double.parseDouble(AT_4_LEN);
                    double at_5_LEN = Double.parseDouble(AT_5_LEN);
                    double at_6_LEN = Double.parseDouble(AT_6_LEN);
                    double at_7_LEN = Double.parseDouble(AT_7_LEN);
                    double at_8_LEN = Double.parseDouble(AT_8_LEN);
                    double at_9_LEN = Double.parseDouble(AT_9_LEN);
                    double at_10_LEN = Double.parseDouble(AT_10_LEN);
                    double at_total_LEN = at_2_LEN + at_3_LEN + at_4_LEN + at_5_LEN + at_6_LEN + at_7_LEN + at_8_LEN + at_9_LEN + at_10_LEN;
                    double promedio_at_LEN = at_total_LEN / 9;

                    double at_2_MAT = Double.parseDouble(AT_2_MAT);
                    double at_3_MAT = Double.parseDouble(AT_3_MAT);
                    double at_4_MAT = Double.parseDouble(AT_4_MAT);
                    double at_5_MAT = Double.parseDouble(AT_5_MAT);
                    double at_6_MAT = Double.parseDouble(AT_6_MAT);
                    double at_7_MAT = Double.parseDouble(AT_7_MAT);
                    double at_8_MAT = Double.parseDouble(AT_8_MAT);
                    double at_9_MAT = Double.parseDouble(AT_9_MAT);
                    double at_10_MAT = Double.parseDouble(AT_10_MAT);
                    double at_total_MAT = at_2_MAT + at_3_MAT + at_4_MAT + at_5_MAT + at_6_MAT + at_7_MAT + at_8_MAT + at_9_MAT + at_10_MAT;
                    double promedio_at_MAT = at_total_MAT / 9;

                    double la_2_LEN = Double.parseDouble(LA_2_LEN);
                    double la_3_LEN = Double.parseDouble(LA_3_LEN);
                    double la_4_LEN = Double.parseDouble(LA_4_LEN);
                    double la_5_LEN = Double.parseDouble(LA_5_LEN);
                    double la_6_LEN = Double.parseDouble(LA_6_LEN);
                    double la_7_LEN = Double.parseDouble(LA_7_LEN);
                    double la_8_LEN = Double.parseDouble(LA_8_LEN);
                    double la_9_LEN = Double.parseDouble(LA_9_LEN);
                    double la_10_LEN = Double.parseDouble(LA_10_LEN);
                    double la_total_LEN = la_2_LEN + la_3_LEN + la_4_LEN + la_5_LEN + la_6_LEN + la_7_LEN + la_8_LEN + la_9_LEN + la_10_LEN;
                    double promedio_la_LEN = la_total_LEN / 9;

                    double la_2_MAT = Double.parseDouble(LA_2_MAT);
                    double la_3_MAT = Double.parseDouble(LA_3_MAT);
                    double la_4_MAT = Double.parseDouble(LA_4_MAT);
                    double la_5_MAT = Double.parseDouble(LA_5_MAT);
                    double la_6_MAT = Double.parseDouble(LA_6_MAT);
                    double la_7_MAT = Double.parseDouble(LA_7_MAT);
                    double la_8_MAT = Double.parseDouble(LA_8_MAT);
                    double la_9_MAT = Double.parseDouble(LA_9_MAT);
                    double la_10_MAT = Double.parseDouble(LA_10_MAT);
                    double la_total_MAT = la_2_MAT + la_3_MAT + la_4_MAT + la_5_MAT + la_6_MAT + la_7_MAT + la_8_MAT + la_9_MAT + la_10_MAT;
                    double promedio_la_MAT = la_total_MAT / 9;

                    double pa_2_MAT = Double.parseDouble(PA_2_MAT);
                    double pa_3_MAT = Double.parseDouble(PA_3_MAT);
                    double pa_4_MAT = Double.parseDouble(PA_4_MAT);
                    double pa_5_MAT = Double.parseDouble(PA_5_MAT);
                    double pa_6_MAT = Double.parseDouble(PA_6_MAT);
                    double pa_7_MAT = Double.parseDouble(PA_7_MAT);
                    double pa_8_MAT = Double.parseDouble(PA_8_MAT);
                    double pa_9_MAT = Double.parseDouble(PA_9_MAT);
                    double pa_10_MAT = Double.parseDouble(PA_10_MAT);
                    double pa_total_MAT = pa_2_MAT + pa_3_MAT + pa_4_MAT + pa_5_MAT + pa_6_MAT + pa_7_MAT + pa_8_MAT + pa_9_MAT + pa_10_MAT;
                    double promedio_pa_MAT = pa_total_MAT / 9;

                    double pa_2_LEN = Double.parseDouble(PA_2_LEN);
                    double pa_3_LEN = Double.parseDouble(PA_3_LEN);
                    double pa_4_LEN = Double.parseDouble(PA_4_LEN);
                    double pa_5_LEN = Double.parseDouble(PA_5_LEN);
                    double pa_6_LEN = Double.parseDouble(PA_6_LEN);
                    double pa_7_LEN = Double.parseDouble(PA_7_LEN);
                    double pa_8_LEN = Double.parseDouble(PA_8_LEN);
                    double pa_9_LEN = Double.parseDouble(PA_9_LEN);
                    double pa_10_LEN = Double.parseDouble(PA_10_LEN);
                    double pa_total_LEN = pa_2_LEN + pa_3_LEN + pa_4_LEN + pa_5_LEN + pa_6_LEN + pa_7_LEN + pa_8_LEN + pa_9_LEN + pa_10_LEN;
                    double promedio_pa_LEN = pa_total_LEN / 9;

*/
                    String id = "/serviceweb/api/v1/visit/" + visita.getId() + "/";

                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(getString(R.string.url_backend))
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();

                    formularioPedagogicoClient formularioPedagogicoClient = retrofit.create(formularioPedagogicoClient.class);

                    int esExtracurricular, tieneInternet;
                    if(chboc_extracurricular.isChecked()){
                        esExtracurricular = 1;

                    }
                    else{
                        esExtracurricular = 0;                    }
                    if(chbox_internet.isChecked()){
                        tieneInternet = 1;

                    }
                    else {
                        tieneInternet = 0;

                    }

                    Call<Void> call = formularioPedagogicoClient.guardarFormulario("system","ABC123456789",
                            visita.getId(),"/serviceweb/api/v1/visit/" + visita.getId() + "/",esExtracurricular,tieneInternet,noProf,minAPCI,accion,visita.getUsername(),visita.getUsername());
                    /*
                    AI_2_A,AI_3_A,AI_4_A,AI_5_A,AI_6_A,AI_7_A,AI_8_A,AI_9_A,AI_10_A,
                            AI_2_B,AI_3_B,AI_4_B,AI_5_B,AI_6_B,AI_7_B,AI_8_B,AI_9_B,AI_10_B,AI_2_C,AI_3_C,AI_4_C,AI_5_C,AI_6_C,AI_7_C,AI_8_C,AI_9_C,AI_10_C,
                            ai_total_2,ai_total_3,ai_total_4,ai_total_5,ai_total_6,ai_total_7,ai_total_8,ai_total_9,ai_total_10,AI_total_A,AI_total_B,
                            AI_total_C,ai_total_promedio,at_2_LEN,at_3_LEN,at_4_LEN,at_5_LEN,at_6_LEN,at_7_LEN,at_8_LEN,at_9_LEN,at_10_LEN,at_2_MAT,
                            at_3_MAT,at_4_MAT,at_5_MAT,at_6_MAT,at_7_MAT,at_8_MAT,at_9_MAT,at_10_MAT,promedio_at_LEN,promedio_at_MAT,la_2_LEN,la_3_LEN,la_4_LEN,la_5_LEN,
                            la_6_LEN,la_7_LEN,la_8_LEN,la_9_LEN,la_10_LEN,la_2_MAT,la_3_MAT,la_4_MAT,la_5_MAT,la_6_MAT,la_7_MAT,la_8_MAT,la_9_MAT,la_10_MAT,promedio_la_LEN,
                            promedio_la_MAT,pa_2_MAT,pa_2_LEN,pa_3_MAT,pa_3_LEN,pa_4_MAT,pa_4_LEN,pa_5_MAT,pa_5_LEN,pa_6_MAT,pa_6_LEN,pa_7_MAT,pa_7_LEN,pa_8_MAT,pa_8_LEN,
                            pa_9_MAT,pa_9_LEN,pa_10_MAT,pa_10_LEN,promedio_pa_LEN,promedio_pa_MAT );*/

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(visita.getType() == 1 && (visita.getUser_type() == 1 || visita.getUser_type() == 3)){
                                Intent checkOutIntent = new Intent(getApplicationContext(), Incidencias.class);
                                checkOutIntent.putExtra("visitaActual",visita);
                                startActivity(checkOutIntent);

                            }
                            else{
                                Intent formularioTecnico = new Intent(getApplicationContext(),TecnicoFormularioActivity.class);
                                formularioTecnico.putExtra("visitaActual",visita);
                                startActivity(formularioTecnico);

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {



                        }
                    });





                    //se hace el post






                }


            }
        });



        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CrearFormularioPedagogico.this,MainActivity.class));
                finish();
            }
        });

    }
}
