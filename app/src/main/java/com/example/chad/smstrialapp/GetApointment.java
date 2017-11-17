package com.example.chad.smstrialapp;

import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GetApointment {

    private static final String[] SCOPES = {CalendarScopes.CALENDAR_READONLY};
    private static final String tag = "Captains Log: ";
    void CalendarMethod(Context context) throws IOException {

// Initialize Calendar service with valid OAuth credentials
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // Initialize credentials and service object.

        GoogleAccountCredential credentials = GoogleAccountCredential.usingOAuth2(
                context, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credentials)
                .setApplicationName("applicationName").build();

        Log.d(tag, "got through calendar service");

// Retrieve an event

        Events events = service.events().list('primary').getTimeMin("2013-06-13T09:00:00-07:00").execute();
        List<Event> items = events.getItems();

    }
}
