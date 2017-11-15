package com.example.chad.smstrialapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;

import java.util.Arrays;

public class parseCalendar extends Activity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_calendar);
    }

    MakeRequestTask(GoogleAccountCredential credential) {

        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();
    }


    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };
    // Initialize Calendar service with valid OAuth credentials
    GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
            getApplicationContext(), Arrays.asList(SCOPES))
            .setBackOff(new ExponentialBackOff());
    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();


    Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("applicationName").build();

    // Retrieve an event
    Event event = service.events().get('primary', "eventId").execute();*/
}
