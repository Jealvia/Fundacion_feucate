package com.example.root.educateappcontrolvisitas.ui;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.example.root.educateappcontrolvisitas.R;
import com.example.root.educateappcontrolvisitas.api.model.PostApiKey;
import com.example.root.educateappcontrolvisitas.api.model.Visita;
import com.example.root.educateappcontrolvisitas.api.service.UsuariosClient;
import com.example.root.educateappcontrolvisitas.api.service.VisitasClient;
import com.example.root.educateappcontrolvisitas.ui.adapter.VisitaAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.security.DigestException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private SharedPreferences sp,sp1;
    private String nombreUsuario;
    private String usuarioId;
    private String tokenCrsf;
    private String session;


    @Override
    protected void onPause() {

        // hide the keyboard in order to avoid getTextBeforeCursor on inactive InputConnection
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);

        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        sp=getSharedPreferences("login",MODE_PRIVATE);

        //if SharedPreferences contains username and password then directly redirect to Home activity
        if(sp.contains("usuario_nombre") && sp.contains("usuario_id")){



            startActivity(new Intent(LoginActivity.this,MainActivity.class));

            finish();   //finish current activity

        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private String usuarioId;
        private boolean loginExitoso;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            usuarioId = "";

        }

        @Override
        protected Boolean doInBackground(Void... params)  {
            // TODO: attempt authentication against a network service.

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url_backend))
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            UsuariosClient usuariosClient = retrofit.create(UsuariosClient.class);

            Call<JsonObject> call =  usuariosClient.login(mEmail,mPassword);
            System.out.println("Que vaina: "+call.request().body()+"; "+call.request().toString()+";");
            System.out.println("Email: "+mEmail);
            System.out.println("Contrasena: "+mPassword);

            Response<JsonObject> response = null;
            try {
                response = call.execute();
                //String token[]=response.headers().values("Set-Cookie").get(0).split(";");
                //String id[]=response.headers().values("Set-Cookie").get(1).split("=");
                //String tmpIds[]=id[1].split(";");
                //String tmptoken[]=token[0].split("=");

                //String sesionId=tmpIds[0];
                //String crsfToken=tmptoken[1];
                //tokenCrsf=crsfToken;
                //session=sesionId;
                tokenCrsf="";
                session="";

                System.out.println(response.headers());
                if(response.isSuccessful()){
                    if(response.body() != null ){
                        System.out.println("Entro ");
                        loginExitoso = response.body().getAsJsonObject().get("success").getAsBoolean();
                        System.out.println("Exito: "+loginExitoso);
                        usuarioId = response.body().getAsJsonObject().get("uid").getAsString();
                        System.out.println("ID: "+usuarioId);

                        return loginExitoso;
                    }
                    else{
                        System.out.println("Error ");
                        return false;
                    }

                }
            } catch (IOException e) {
                System.out.println("Catch: "+e);
                e.printStackTrace();
            }

            return loginExitoso;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {



                System.out.println("Login exitoso");
                System.out.println("El id es: " + usuarioId);



                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);

                mainIntent.putExtra("usuario_id",usuarioId);
                mainIntent.putExtra("usuario_nombre",mEmail);
                mainIntent.putExtra("token",tokenCrsf);
                mainIntent.putExtra("sesion",session);

                SharedPreferences prefs =
                        getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

                String token = prefs.getString("token_firebase", "token");
                System.out.println("TOKEN LOGIN"+token);
                //Manejar las SharedPreferences para mantener al usuario logged in
                SharedPreferences.Editor e=sp.edit();
                e.putString("usuario_nombre",mEmail);
                e.putString("usuario_id",usuarioId);
                e.commit();

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
                UsuariosClient crearVisitaClient = retrofit.create(UsuariosClient.class);

                PostApiKey postVisita=new PostApiKey(token);

                Call<PostApiKey> call=crearVisitaClient.actualizarToken(usuarioId,postVisita);//"system","ABC123456789",

                call.enqueue(new Callback<PostApiKey>() {
                    @Override
                    public void onResponse(Call<PostApiKey> call, Response<PostApiKey> response) {
                        System.out.println("Que tiro estas pepas");
                        System.out.println("MESSAGE"+response.message()+"---------"+response.raw());
                        System.out.println("QUE PASO"+call.request()+"--------"+call.isExecuted()+"---------");
                    }

                    @Override
                    public void onFailure(Call<PostApiKey> call, Throwable t) {
                        System.out.println("Que tiro estas pepas");


                    }
                });

                // Start the new activity
                startActivity(mainIntent);
                finish();


            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}