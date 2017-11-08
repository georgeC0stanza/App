package com.example.chad.smstrialapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class TemplateSave {

    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "templates";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void save(Context context, String templateName, String templateText) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(templateName, templateText);
        editor.commit();
        Toast.makeText(context, "saved!",
                Toast.LENGTH_SHORT).show();

    }

    public String load(Context context, String templateName){

        String templateText = (getPrefs(context).getString(templateName, ""));

        return templateText;
    }

    public void delete(Context context, String templateName){
        sharedPreferences.edit().remove(templateName).commit();
    }
}
