package com.example.chad.smstrialapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class TemplateSave {

    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "templates";
    private static final String tag = "Captains Log: ";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void save(Context context, String templateName, String templateText) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(templateName, templateText);
        editor.commit();
        Toast.makeText(context, "saved!",
                Toast.LENGTH_SHORT).show();
        Log.d(tag, "Saved Template");

    }

    public String load(Context context, String templateName){

        String templateText = (getPrefs(context).getString(templateName, ""));

        return templateText;
    }

    public void delete(Context context, String templateName){
        sharedPreferences.edit().remove(templateName).commit();
        Toast.makeText(context, templateName + " removed!",
                Toast.LENGTH_SHORT).show();
    }
}
