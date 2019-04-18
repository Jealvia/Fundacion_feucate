package com.example.root.educateappcontrolvisitas.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.root.educateappcontrolvisitas.R;
import com.kyanogen.signatureview.SignatureView;
//import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class FirmaActivity extends AppCompatActivity {

    Bitmap bitmap;
    Button clear,save,cancelar;
    SignatureView signatureView;
    String path;
    private static final String IMAGE_DIRECTORY = "/feducate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_firma);

        signatureView =  (SignatureView) findViewById(R.id.signature_view);
        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        cancelar=(Button) findViewById(R.id.cancelar);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = saveImage(bitmap);
                setResult(RESULT_OK, null);
                finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();

            }
        });


    }



    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        System.out.println(wallpaperDirectory.getAbsolutePath()+" "+wallpaperDirectory.exists());
        if (!wallpaperDirectory.exists()) {
            System.out.println("Ya existe");
            wallpaperDirectory.mkdirs();
            System.out.println("Creo");
        }

        try {
            File f = new File(wallpaperDirectory, "firma.jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(FirmaActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            System.out.println(f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();

            System.out.println("Excepcion: "+e1);
        }
        return "";
    }

}