package com.example.chad.smstrialapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * The EditTemplate class is meant to set button layouts for the xml, as well as use the TemplateSave
 * class to save the edited templates
 */
public class EditTemplate extends AppCompatActivity {
//intialize variables
    Button saveTextButton;
    final Context context = this;
    EditText templateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_template);
// set variables
        saveTextButton = (Button) findViewById(R.id.saveText);
        templateText = (EditText) findViewById(R.id.templateText);
// have the template save when the click the button.
        saveTextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TemplateSave ts = new TemplateSave();
                ts.save(context, "pianoAppointment", templateText.getText().toString());

            }
        });
    }
}
