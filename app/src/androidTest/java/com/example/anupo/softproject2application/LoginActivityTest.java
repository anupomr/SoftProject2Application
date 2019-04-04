package com.example.anupo.softproject2application;
/*
 * Purpose: To test Books Activity
 * Author:  Anupom Roy
 * Date: April 3, 2019
 * Version: 2.1
 * */
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule =new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity loginActivity=null;



    @Before
    public void setUp() throws Exception {
        loginActivity=mActivityTestRule.getActivity();
    }
    @Test
    public void testLunchOfBooksActivity(){
        assertNotNull(loginActivity.findViewById(R.id.buttonLoginCustomer));
        onView(withId(R.id.buttonLoginCustomer)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        loginActivity=null;
    }
}