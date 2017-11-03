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

    private static final String phoneNumber = "253-22856650,2083601160";

    @Test
    public void Phone_number_correct() throws Exception {
        Boolean Phone = true;
        String[] stringArray = phoneNumber.split(",");
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].length() < 9 || stringArray[i].length() > 9 ) {
                Phone = false;
                System.out.println(Phone);
            }
        if(Phone == false){
            break;
        }
        }
        System.out.println(Phone);
    }
}
