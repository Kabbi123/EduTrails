package com.example.edutrails;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

public class RegistrationScreen extends AppCompatActivity {

    private TextView toSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        toSignIn = (TextView) findViewById(R.id.tosignin);
        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignIn();
            }
        });
    }
    void openSignIn() {
        Intent intent = new Intent(this, ContentScreen.class);
        startActivity(intent);
    }
}