package com.example.edutrails.ui.startTour;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.edutrails.R;
import com.google.android.material.button.MaterialButton;

public class StartTourChose extends Fragment {

    MaterialButton mehr;
    MaterialButton starten;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_start_tour_chose, container, false);

        mehr = (MaterialButton) rootView.findViewById(R.id.mehr_button);
        starten = (MaterialButton) rootView.findViewById(R.id.starten_button);
        starten.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTour();
            }
        });

        return rootView;
    }

    private void startTour() {
        StartTourFragment nextFrag= new StartTourFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.card, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


}