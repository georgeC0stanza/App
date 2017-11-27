package com.example.chad.smstrialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class obtainDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtain_date);
    }

    public void GoogleGoodness(View view) throws IOException {
        Intent intent = new Intent(this, GoogleCalApi.class);
        startActivity(intent);

    }
}