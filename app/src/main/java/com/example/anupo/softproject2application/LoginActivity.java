package com.example.anupo.softproject2application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private static final String CUSTOMER_USERNAME_PREFS = "customer_username_prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText =(EditText) findViewById(R.id.usernameTxt);
        passwordEditText = (EditText)findViewById(R.id.passwordTxt);
    }
    public void cancelBtn_OnClick(View view) {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }
    // If not rregister, go to registration activity to register
    public void NotRegistered_OnClick(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
