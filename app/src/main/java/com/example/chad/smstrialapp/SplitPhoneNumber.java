package com.example.chad.smstrialapp;


public class SplitPhoneNumber {
    static public String[] split(String phoneNumber){
        String[] stringArray = phoneNumber.split(",");
        return stringArray;
    }
}
