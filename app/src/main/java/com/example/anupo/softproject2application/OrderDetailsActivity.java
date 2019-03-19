package com.example.anupo.softproject2application;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;

public class OrderDetailsActivity extends AppCompatActivity {
    public  static String data;
    public  static TextView textView;
    String myString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        textView=findViewById(R.id.showBookarea);
        //retrieving from shared preferences
        SharedPreferences myPref = getSharedPreferences("MyUser", MODE_PRIVATE);
         myString = myPref.getString("UserName","");
    }
}
