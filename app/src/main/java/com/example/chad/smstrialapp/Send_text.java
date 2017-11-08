package com.example.chad.smstrialapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Send_text extends Activity {

    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;

    private void sendSMS(String  phoneNumber, String  message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, Send_text.class), 0);

        // split up phone numbers
        String[] stringArray = SplitPhoneNumber.split(phoneNumber);

        // send text messages
        SmsManager sms = SmsManager.getDefault();

        for (int i = 0; i < stringArray.length; i++) {
            sms.sendTextMessage(stringArray[i], null, message, null, null);
        }
        Toast.makeText(getApplicationContext(), "Message sent!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        // get permissions
        PermissionsRequest pR = new PermissionsRequest();
        pR.verify(this);

        TemplateSave ts = new TemplateSave();
        String appointmentLoad = ts.load(this, "pianoAppointment");
        Toast.makeText(this, appointmentLoad, Toast.LENGTH_LONG).show();



        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();
                if (phoneNo.length() > 0 && message.length() > 0)
                    sendSMS(phoneNo, message);
                else
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }
}
