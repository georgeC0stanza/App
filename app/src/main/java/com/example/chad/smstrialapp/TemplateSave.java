package com.example.chad.smstrialapp;

import android.content.Context;
import android.content.SharedPreferences;

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
    }

    public String load(Context context, String templateName){

        SharedPreferences shared = getPrefs(context);
        String templateText = (shared.getString(templateName, ""));

        return templateText;
    }
}
