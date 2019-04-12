package com.example.anupo.softproject2application;
/*
 * Purpose: To test Login Activity
 * Author:  Anupom Roy
 * Date: April 12, 2019
 * Version: 2.1
 * */
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrationActivityTest {

    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityRegistration =new ActivityTestRule<RegistrationActivity>(RegistrationActivity.class);

    private RegistrationActivity mRegistratinActivity =null;


    @Before
    public void setUp() throws Exception {
        mRegistratinActivity=mActivityRegistration.getActivity();
    }

    @Test
    public void testLaunch(){

        View view =mRegistratinActivity.findViewById(R.id.welcomeTXT);
        view=mRegistratinActivity.findViewById(R.id.emailET);
        view=mRegistratinActivity.findViewById(R.id.passwordET);
        view=mRegistratinActivity.findViewById(R.id.phET);

        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mRegistratinActivity=null;
    }
}