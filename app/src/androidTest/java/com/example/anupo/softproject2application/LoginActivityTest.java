package com.example.anupo.softproject2application;
/*
 * Purpose: To test Login Activity
 * Author:  Anupom Roy
 * Date: April 3, 2019
 * Version: 2.1
 * */
import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity =null;

    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(LoginActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mainActivity =mActivityTestRule.getActivity();
    }
    @Test
    public void testLunchOfBooksActivity(){
        assertNotNull(mainActivity.findViewById(R.id.buttonLogin));

        onView(withId(R.id.buttonLogin)).perform(click());

        Activity loginActivity=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(loginActivity);

        loginActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity =null;
    }
}