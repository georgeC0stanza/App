package com.example.chad.smstrialapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
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
import org.apache.commons.collections4.CollectionUtils;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

/* code examples from https://mobiforge.com/design-development/sms-messaging-android
 */
public class SendText extends Activity {

    Button btnLoadSMS;
    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
    private static final String tag = "Captains Log: ";
    String appointmentLoad = null;
    /**
     * creates the buttons, calls for permissions, fills in the text from the saved template,
     * fills in the template markers with actual information.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        btnLoadSMS = (Button) findViewById(R.id.loadTemp);
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);


        // get permissions
        PermissionsRequest pR = new PermissionsRequest();
        pR.verifySMS(this);


        // send button
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();
                appointmentLoad = message;
                fillSMS(appointmentLoad);
            /*
                if (phoneNo.length() > 0 && message.length() > 0) {

                    sendSMS(phoneNo, message);
//                    sendCheckSMS(phoneNo, message);
                }
                else {
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
                    Log.d(tag, "Send Message sms");
                }
                */
            }
        });

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

    private void fillSMS(String appointmentLoad) {
        String name = "";
        String start = "";
        String date = "";

        // load calendar events into stack
        TemplateSave ts = new TemplateSave();
        Set<String> appointmentLoadSet = ts.loadSet(SendText.this, "events");
        Stack<String> events = new Stack<>();


        if (CollectionUtils.isNotEmpty(appointmentLoadSet))
            events.addAll(appointmentLoadSet);

            while(!events.empty()) {
            String event = events.peek();
            events.pop();

            PopulateTemplate pt = new PopulateTemplate();

            date = event.substring(1, 10);
            start = event.substring(11, 17);
            name = event.substring(event.indexOf("(") + 1, event.indexOf(")"));

            appointmentLoad = pt.pTemplate(appointmentLoad, name, start, date);

            EditText editText = (EditText) findViewById(R.id.txtMessage);
            editText.setText(appointmentLoad, TextView.BufferType.EDITABLE);

            Log.d(tag, "Load Message");

                String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();
                if (phoneNo.length() > 0 && message.length() > 0) {

                    sendSMS(phoneNo, message);
//                    sendCheckSMS(phoneNo, message);
                }
                else {
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
                    Log.d(tag, "Send Message sms");
                }
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
