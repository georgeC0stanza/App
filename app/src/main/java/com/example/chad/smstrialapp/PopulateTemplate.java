package com.example.chad.smstrialapp;

/**
 * Created by Taft on 11/20/2017.
 */

public class PopulateTemplate {
    public String pTemplate(String message, String name, String start, String date){

        message = message.replace("<N>", name);
        message = message.replace("<T>", start);
        message = message.replace("<D>", date);

        return message;
    }
}

