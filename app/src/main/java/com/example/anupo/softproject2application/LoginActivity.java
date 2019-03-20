package com.example.anupo.softproject2application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    //validate credentials
    public boolean validate(String username, String password) {
        if(!username.isEmpty())
        {
            return username.equals(password);
        }
        return false;
    }


    public void loginBtn_OnClick(View view) {
        String _username = usernameEditText.getText().toString();
        String _password = passwordEditText.getText().toString();

        if(validate(_username,_password))
        {
            SharedPreferences.Editor editor =
                    getSharedPreferences(CUSTOMER_USERNAME_PREFS, MODE_PRIVATE).edit();
            editor.putString("username_key",_username);
            editor.apply();

            //view customer activity
            Toast.makeText(this, "valid", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,BooksListActivity.class);
            intent.putExtra("username",_username);
            startActivity(intent);

            // for SharedPreferences response
            SharedPreferences myPreference =
                    getSharedPreferences("MyUser", 0);
            //prepare it for edit by creating and Edit object
            SharedPreferences.Editor prefEditor = myPreference.edit();
            //store a string in memory
            prefEditor.putString("UserName", _username);
            //commit the transaction
            prefEditor.commit();
        }
        else
        {
            //throw error
            Toast.makeText(this, "invalid", Toast.LENGTH_SHORT).show();
            passwordEditText.setFocusable(true);
            passwordEditText.setError("Password and/or username is wrong...");
        }

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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent homeIntent=new Intent(this,MainActivity.class);
                startActivity(homeIntent);
                //Toast.makeText(this, "You selected start!", Toast.LENGTH_LONG).show();
                break;
            case R.id.login:

                Toast.makeText(this, "You selected login!", Toast.LENGTH_LONG).show();
                break;
            case R.id.book:
                Intent books=new Intent(this,BooksActivity.class);
                startActivity(books);
                //Toast.makeText(this, "You selected book!", Toast.LENGTH_LONG).show();
                break;
            case R.id.bookList:
                Intent booksListIntent=new Intent(this,BooksListActivity.class);
                startActivity(booksListIntent);

                break;
            case R.id.exit:
                Toast.makeText(this, "You selected exit!", Toast.LENGTH_LONG).show();
                System.exit(1);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
