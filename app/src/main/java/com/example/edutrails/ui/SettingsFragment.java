package com.example.edutrails.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.edutrails.R;

public class SettingsFragment extends Fragment {

    RadioButton button1;
    RadioButton button2;
    RadioGroup group;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        group = (RadioGroup) rootView.findViewById(R.id.group1);
        button1 = (RadioButton) rootView.findViewById(R.id.button1);
        button2 = (RadioButton) rootView.findViewById(R.id.button2);

        onRadioButtonClicked(rootView);


        return rootView;
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.button1:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.button2:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}