package com.example.chad.smstrialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get permissions
        PermissionsRequest pR = new PermissionsRequest();
        pR.verifyGoogle(this);
    }
    public void CreateText(View view){
        Intent intent = new Intent(this, SendText.class);
        startActivity(intent);
        }

    public void createTemplate(View view){
        Intent intent = new Intent(this, EditTemplate.class);
        startActivity(intent);
    }

    public void GoogleGoodness(View view) throws IOException {
        Intent intent = new Intent(this, GoogleCalApi.class);
        startActivity(intent);
        //GetApointment ga = new GetApointment();
        //ga.CalendarMethod(this);
    }
}
