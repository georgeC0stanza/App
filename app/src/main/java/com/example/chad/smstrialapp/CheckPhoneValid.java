package com.example.chad.smstrialapp;

/**
 * Created by Chad on 12/8/2017.
 */

/**
 * The CheckPhoneValid class is to change a phone number into digits, after which it checks if it is 10 digits.
 * The function returns if the phone number is formatted correctly or not.
 */
public class CheckPhoneValid {

    //implements the changes listed above
    public Boolean PhoneValid(String phoneNumber) throws Exception {
        String PhoneAlNu = phoneNumber.replaceAll("[^\\d.]", "");
        Boolean Phone = true;

        if (PhoneAlNu.length() < 10 || PhoneAlNu.length() > 10 ) {
            Phone = false;
        }
        return Phone;
    }

}
