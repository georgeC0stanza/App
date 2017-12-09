package com.example.chad.smstrialapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * save and edit templates
 */
public class EditTemplate extends AppCompatActivity {


    private static final String tag = "Captains Log: ";
    //initalize buttons
    Button saveTextButton;
    String currentTemplate;
    EditText templateText;

    /**
     * save and edit templates
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_template);
        final SavePopUp spp = new SavePopUp();
        final SavePopUp templateName = new SavePopUp();
        final Context context = this;
        currentTemplate = "";
        saveTextButton = (Button) findViewById(R.id.saveText);
        templateText = (EditText) findViewById(R.id.templateText);
        TemplateSave ts = new TemplateSave();

        if(ts.loadSet(this, "listOfTemplateNames") == null){
            Set<String> createNames = new HashSet<>();

            ts.saveSet(context, "listOfTemplateNames", new HashSet(createNames));
        }

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText input = new EditText(context); // This could also come from an xml resource, in which case you would use findViewById() to access the input

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        spp.setEntry(value); // mItem is a member variable in your Activity
                        dialog.dismiss();

                        templateName.setEntry(spp.getEntry());
                        TemplateSave ts = new TemplateSave();

                        Set<String> temporaryNames = ts.loadSet(context, "listOfTemplateNames");
                        temporaryNames.add(templateName.getEntry());
                        ts.save(context, templateName.getEntry(), templateText.getText().toString());
                        ts.saveSet(context, "listOfTemplateNames", new HashSet(temporaryNames));

                        reload();
                    }
                });
                builder.show();
            }
        });

      Spinner betterSpinner = (Spinner) findViewById(R.id.spinner2);
        betterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TemplateSave tempSave = new TemplateSave();
                templateText.setText(tempSave.load(context, parent.getItemAtPosition(position).toString()));
                currentTemplate = parent.getItemAtPosition(position).toString();
                setDefault();
                Toast.makeText(getBaseContext(), currentTemplate,
                        Toast.LENGTH_SHORT).show();
            }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
           }

       });

        //this calls the logic for the drop down menu, for putting templates in.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line,
                new ArrayList(ts.loadSet(context, "listOfTemplateNames")));
        betterSpinner.setAdapter(arrayAdapter);
    }

    /**
     * set the default template for the send text
     */
    public void setDefault(){
        TemplateSave ts = new TemplateSave();
        ts.save(this, "pianoAppointment", ts.load(this, currentTemplate));
    }

    /**
     * redraws the screen
     */
    public void reload(){
        this.recreate();
    }


}
