package com.example.chad.smstrialapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

 public class TemplateSave {
    // defines variables we will use
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "templates";
    private static final String tag = "Captains Log: ";

    private  SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * This method will save the template to shaved preferences.
     * @param context
     * @param templateName
     * @param templateText
     */
    public void save(Context context, String templateName, String templateText) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(templateName, templateText);
        editor.apply();
        Toast.makeText(context, "saved!",
                Toast.LENGTH_SHORT).show();
        Log.d(tag, "Saved Template");
    }

    /**
     * This instead saves the template as a set of strings for the template text instead.
     * @param context
     * @param templateName
     * @param templateText
     */
    public void saveSet(Context context, String templateName, Set<String> templateText){
        SharedPreferences.Editor editor = getPrefs(context).edit();

        editor.putStringSet(templateName, templateText);
        editor.apply();

        Toast.makeText(context, "saved!",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * This method allows us to load the template saved to shared preferences.
     * @param context
     * @param templateName
     * @return
     */
     public String load(Context context, String templateName){

        String templateText = (getPrefs(context).getString(templateName, ""));

        return templateText;
    }

    /**
     * If a set was used for the template text instead we load it with this method.
     * @param context
     * @param templateName
     * @return
     */
     public Set<String> loadSet(Context context, String templateName){

        Set<String> templateText = (getPrefs(context).getStringSet(templateName, null));

        return templateText;
    }

    /**
     * If we want to delete the information in shared preferences we call this method.
     * @param context
     * @param templateName
     */
    public void delete(Context context, String templateName){
        sharedPreferences.edit().remove(templateName).commit();
        Toast.makeText(context, templateName + " removed!",
                Toast.LENGTH_SHORT).show();
    }
}
