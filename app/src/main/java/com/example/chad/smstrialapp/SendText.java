package com.example.chad.smstrialapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/***********************************************************************
 * code examples from https://mobiforge.com/design-development/sms-messaging-android
 *
 ***********************************************************************/
public class SendText extends Activity {

    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        // get permissions
        PermissionsRequest pR = new PermissionsRequest();
        pR.verifySMS(this);

        // load template text
        TemplateSave ts = new TemplateSave();
        final String appointmentLoad = ts.load(this, "pianoAppointment");

        EditText editText = (EditText)findViewById(R.id.txtMessage);
        editText.setText(appointmentLoad, TextView.BufferType.EDITABLE);

        // send button
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();
//                String message = appointmentLoad;

                if (phoneNo.length() > 0 && message.length() > 0)
                    sendSMS(phoneNo, message);
//                    sendCheckSMS(phoneNo, message);
                else
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

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
                        Toast.makeText(getBaseContext(),  "Error on " + phoneNumber + ": Null PDU",
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
