package com.example.chad.smstrialapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.gson.Gson;

import org.apache.commons.collections4.CollectionUtils;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

/**
 * This is the activity that sends a text message from the premade templates using the google
 * calendar events.
 * our code examples from https://mobiforge.com/design-development/sms-messaging-android
 */
public class SendText extends Activity {
    //initializes buttons and EditTexts names
    Button btnLoadSMS;
    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
    GoogleAccountCredential mCredential;


    // Creates a log file for us to use for detection, this one was just to test functionality,
    // we are sure we are not the first nerds to put this.
    private static final String tag = "Captains Log: ";

    // intializes the event loader
    String appointmentLoad = null;

    /**
     * This activity creates the buttons, calls for permissions, fills in the text from the saved template,
     * fills in the template markers with actual information, and then sends the texts to those in the event list.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);
        // create the buttons and editTexts that we are going to use
        btnLoadSMS = (Button) findViewById(R.id.loadTemp);
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        // get permissions to send texts
        PermissionsRequest pR = new PermissionsRequest();
        pR.verifySMS(this);


        // send button
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

 //             String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();
                appointmentLoad = message;
                try {
                    fillSMS(appointmentLoad);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // this button loads the template that we have saved
        btnLoadSMS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // load template text
                TemplateSave ts = new TemplateSave();
                String appointmentLoad = ts.load(SendText.this, "pianoAppointment");

                EditText editText = (EditText) findViewById(R.id.txtMessage);
                editText.setText(appointmentLoad, TextView.BufferType.EDITABLE);
            }
        });
    }

    /**
     * this gets event information, formats and initializes the data. It then calls the send function for each
     * appoinment in the stack
     * @param appointmentLoad
     */
    private void fillSMS(String appointmentLoad) throws Exception {
        // initialize event options
        String name = "";
        String start = "";
        String date = "";
        String phoneNo = "";

        // load calendar events into stack
        TemplateSave ts = new TemplateSave();
        Set<String> appointmentLoadSet = ts.loadSet(SendText.this, "events");
        Stack<String> events = new Stack<>();

        //gets the events from the appointment load
        if (CollectionUtils.isNotEmpty(appointmentLoadSet))
            events.addAll(appointmentLoadSet);

        //makes a while loop that will get will get an appointment from the stack, tries to send a
        // reminder message if the phone number is correct, until the stack is empty
            while(!events.empty()) {
                String message = txtMessage.getText().toString();
                appointmentLoad = message;

                String event = events.peek();
            events.pop();

            //creats a template object
            PopulateTemplate pt = new PopulateTemplate();

            //get our events so we can access them
            date = event.substring(1, 10);
            start = event.substring(11, 17);
            name = event.substring(event.indexOf("(") + 1, event.indexOf(")"));
            phoneNo = event.substring(event.indexOf("[") + 1, event.indexOf("]")).replaceAll("[^\\d.]", "");
            appointmentLoad = pt.pTemplate(appointmentLoad, name, start, date);

          //   EditText editText = (EditText) findViewById(R.id.txtMessage);
          //  editText.setText(appointmentLoad, TextView.BufferType.EDITABLE);

            Log.d(tag, "Load Message");

                //String phoneNo = txtPhoneNo.getText().toString();
          //     String message = txtMessage.getText().toString();
                //if (phoneNo.length() > 0 && message.length() > 0) {

                //send a toast to inform the user if an appointment is not going to send
                CheckPhoneValid NumberGood = new CheckPhoneValid();
                if (NumberGood.PhoneValid(phoneNo) == false) {
                    Toast.makeText(getBaseContext(),
                            "The phone number for "+ name + "was entered incorrectly",
                            Toast.LENGTH_SHORT).show();
                }

                //if the number is not formatted correctly it does not send
                if (NumberGood.PhoneValid(phoneNo) == true) {
                    sendSMS(phoneNo, appointmentLoad);
                }
//                    sendCheckSMS(phoneNo, message);
                //}
                //else {
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
                    Log.d(tag, "Send Message sms");
                //}
        }
    }

    /**
     * takes each phone number and sends it a message with the text in the text view
     * @param phoneNumber
     * @param message
     */
    private void sendSMS(String  phoneNumber, String  message) {
//        PendingIntent pi = PendingIntent.getActivity(this, 0,
//                new Intent(this, Send_text.class), 0);

        // split up phone numbers
        String[] listNumbers = SplitPhoneNumber.split(phoneNumber);

        // send text messages
        SmsManager sms = SmsManager.getDefault();

        for (int i = 0; i < listNumbers.length; i++) {
//            sms.sendTextMessage(stringArray[i], null, message, null, null);
//            sms.sendMultipartTextMessage(listNumbers[i], null, parts, null, null);
            sendCheckSMS(listNumbers[i], message);

            Toast.makeText(getApplicationContext(), "Message sent to " + listNumbers[i] + "!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * sends sms messages and listens to the status
     * @param phoneNumber
     * @param message
     */
    //---sends an SMS message to another device---
    private void sendCheckSMS(final String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent " + phoneNumber,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Error on " + phoneNumber + "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "Error on " + phoneNumber + ": No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Error on " + phoneNumber + ": Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Error on " + phoneNumber + ": Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered to " + phoneNumber,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered to " + phoneNumber,
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        // begin sending the message
        SmsManager sms = SmsManager.getDefault();

        // break up long messages
        ArrayList<String> parts = sms.divideMessage(message);

//        sms.sendMultipartTextMessage(phoneNumber, null, parts, sentPI, deliveredPI);
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
}
