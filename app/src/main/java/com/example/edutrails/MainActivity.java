package com.example.edutrails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private TextView toSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        toSignUp = (TextView) findViewById(R.id.tosignup);
        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });
    }
    void openSignUp() {
        Intent intent = new Intent(this, RegistrationScreen.class);
        startActivity(intent);
    }
}