package com.example.chad.smstrialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
          public void CreateText(View view){
        Intent intent = new Intent(this, Send_text.class);
        startActivity(intent);
        }
}
