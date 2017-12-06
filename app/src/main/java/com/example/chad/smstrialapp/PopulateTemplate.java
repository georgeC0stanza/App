package com.example.chad.smstrialapp;

/**
 * Created by Taft on 11/20/2017.
 */

/**
 * This method is made to populate the message in the template base on the replacement character that is
 * used as the input. For instance anywhere <N> is used, the template will put the name of the person in.
 */
public class PopulateTemplate {
    public String pTemplate(String message, String name, String start, String date){
        // make a calendar event
        GoogleCalApi gc = new GoogleCalApi();

        //tell what the replacement character would be
        message = message.replace("<N>", name);
        message = message.replace("<T>", start);
        message = message.replace("<D>", date);

        return message;
    }
}

