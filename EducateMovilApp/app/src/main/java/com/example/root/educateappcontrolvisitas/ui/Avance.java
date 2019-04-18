package com.example.root.educateappcontrolvisitas.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.educateappcontrolvisitas.R;

import androidx.fragment.app.Fragment;

public class Avance extends Fragment {
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_movimiento_equipos, container, false);

    }
}
