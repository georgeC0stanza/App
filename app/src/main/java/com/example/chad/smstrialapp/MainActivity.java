package com.example.chad.smstrialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

/**
 * This is the main activity. On creation we give some basic permissions. In the main activity we have 3 buttons.
 * The CreateText opens the activity that can send texts. The createTemplate opens the activity that can make,
 * then edit templates. The GoogleGoodness button allows us to use the google api, and get permissions for it,
 * and access the events for the next day.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get permissions
        PermissionsRequest pR = new PermissionsRequest();
        pR.verifyGoogle(this);
    }
    // calls the SendText activity
    public void CreateText(View view){
        Intent intent = new Intent(this, SendText.class);
        startActivity(intent);
        }

    // calls the EditTemplate activity
    public void createTemplate(View view){
        Intent intent = new Intent(this, EditTemplate.class);
        startActivity(intent);
    }

    // calls the GoogleCalApi activity
    public void GoogleGoodness(View view) throws IOException {
        Intent intent = new Intent(this, GoogleCalApi.class);
        startActivity(intent);
    }
}
