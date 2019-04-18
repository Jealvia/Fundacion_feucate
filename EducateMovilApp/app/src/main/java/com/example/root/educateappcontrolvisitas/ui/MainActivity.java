package com.example.root.educateappcontrolvisitas.ui;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.UsuariosClient;
import com.example.root.educateappcontrolvisitas.api.service.VisitasClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.SharedPreferences;



public class MainActivity extends AppCompatActivity {

    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 123;
    private String usuario_id;

    public String getUsuario_id() {
        return usuario_id;
    }

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    public String getmUsername() {
        return mUsername;
    }

    private String mUsername;

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    //Es lo mismo que usuario_id en la BD
    private String identificadorUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        identificadorUsuario = sharedPreferences.getString("usuario_id",null);//intent.getStringExtra("usuario_id");

        mUsername = sharedPreferences.getString("usuario_nombre",null);//intent.getStringExtra("usuario_nombre");



        System.out.println("------------------Usuario ingresado-------------------------- ");
        System.out.println("Usuario nombre: " + mUsername);
        System.out.println("Key usuario(lo uso para obtener el Usuario_id): " + identificadorUsuario);



        mSectionsPagerAdapter.usuarioID = identificadorUsuario;

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem nombreUser = menu.findItem(R.id.username);
        nombreUser.setTitle(mUsername);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                //AuthUI.getInstance().signOut(this);
                //Manejar SharedPreferences para Sign out
                SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor e=sp.edit();
                e.clear();
                e.commit();

                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();   //

                return true;
            case R.id.create_requirement:
                if(identificadorUsuario.equals("3")||identificadorUsuario.equals("4"))
                {

                }else {
                    Intent intent = new Intent(MainActivity.this, CrearRequerimiento.class);
                    intent.putExtra("sesion", getIntent().getStringExtra("sesion"));
                    intent.putExtra("token", getIntent().getStringExtra("token"));
                    intent.putExtra("sector", mUsername);

                    startActivity(intent);
                    finish();
                    return true;
                }
            //case R.id.prueba:
                //startActivity(new Intent(MainActivity.this,MovimientoEquiposActivity.class));
                //finish();
                //return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     *
     *
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        //Lo uso para buscar visitas
        private String usuarioID;

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);

        }



        @Override
        public Fragment getItem(int position) {


            if(position == 0){
                return new VisitasHoyFragment().newInstance(usuarioID);
            }
            else{
                return new OtrasVisitasFragment().newInstance(usuarioID);



            }
        }

        @Override
        public int getCount() {
            // Son 2 tabs en total
            return 2;
        }
    }

}
