package com.example.chad.smstrialapp;

/**
 * splits the phone numbers apart with a comma
 */
public class SplitPhoneNumber {
    static public String[] split(String phoneNumber){
        String[] stringArray = phoneNumber.split(",");
        return stringArray;
    }
}
