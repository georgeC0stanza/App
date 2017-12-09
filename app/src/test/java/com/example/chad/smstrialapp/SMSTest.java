package com.example.chad.smstrialapp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Chad on 11/1/2017.
 */


@RunWith(MockitoJUnitRunner.class)
public class SMSTest {

    @Mock
    Context mMockContext;



    @Test
    public void Phone_number_correct() throws Exception {

        CheckPhoneValid NumberGood = new CheckPhoneValid();
        assertEquals(true, NumberGood.PhoneValid("801-572absd-8777"));
        assertEquals(false, NumberGood.PhoneValid("2"));
        assertEquals(false, NumberGood.PhoneValid("278054054055050450"));
        assertEquals(true, NumberGood.PhoneValid("80//1-572absd-877@$!7"));


        /*
        String phoneNumber = "253-22/85j66l?5";
        String PhoneAlNu = phoneNumber.replaceAll("[^\\d.]", "");

        System.out.println(PhoneAlNu);
        System.out.println(PhoneAlNu.length());
        Boolean Phone = true;

            if (PhoneAlNu.length() < 10 || PhoneAlNu.length() > 10 ) {
                Phone = false;
            }
        System.out.println(Phone);
        */
    }
}
