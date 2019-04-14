package com.example.anupo.softproject2application;
/*
 * Purpose: This page for Login
 * Author:  Anupom Roy and Shila das
 * Date: Feburary 20, 2019
 * Version: 2.2
 * */
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    String res="";
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
        if(!username.isEmpty() && !password.isEmpty())
        {
            //return username.equals(password);
            return true;
        }
        return false;
    }


    public void loginBtn_OnClick(View view) {
        String _username = usernameEditText.getText().toString();
        String _password = passwordEditText.getText().toString();
        //for inserting book condition


        if(validate(_username,_password))
        {
            new HTTPAsyncTask().execute("http://bookapi-dev.us-east-1.elasticbeanstalk.com/api/UserWithRoles/login");
           // Toast.makeText(this
            //, res, Toast.LENGTH_LONG).show();
          //  JSONArray jsonArray = null;

           // try {
               // jsonArray = new JSONArray(res);
             //   Log.d("sebo","test");
          //  for (int i = 0; i < jsonArray.length(); i++) {
              //  JSONObject jsonObject = new JSONObject(res);

            //    Log.d("shila1",String.valueOf(jsonObject.length()));
              //  Log.d("shila3",String.valueOf(jsonObject));
            //    Log.d("shila2",String.valueOf(jsonObject.names()));
             //   Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.names()), Toast.LENGTH_LONG).show();
              //  Toast.makeText(getApplicationContext(),jsonObject.getString("uEmail"), Toast.LENGTH_LONG).show();
//                if (jsonObject.getString("title").equals(myString)) {
//                    singleParsed =
//                            "Book Name  : " + jsonObject.getString("title") + "\n" +
//                                    "Price      : " + jsonObject.getString("price") + "\n" +
//                                    "Edition    : " + jsonObject.getString("edition") + "\n"+
//                                    "Description: " + jsonObject.getString("description") + "\n"+
//                                    "ISBN       : " + jsonObject.getString("isbn") + "\n"+
//                                    "Publisher  : "+ jsonObject.getString("publisher") + "\n";
//
//                    dataParsed = dataParsed + singleParsed + "\n\n";
//                }
            //}
            //} catch (JSONException e) {
              //  e.printStackTrace();
            //}

        }
        else
        {
            //throw error
            Toast.makeText(this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
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


    //To generate the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent homeIntent=new Intent(this,MainActivity.class);
                startActivity(homeIntent);

                break;
            case R.id.login:

                Toast.makeText(this, "You selected login!", Toast.LENGTH_LONG).show();
                break;
            case R.id.book:
                Intent books=new Intent(this,BooksActivity.class);
                startActivity(books);

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
    //Menu End

    // This Async Task connect database though API for Login
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return HttpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
           // Log.d("resStr",result);
            //usernameEditText.setText(result);
           try {
                JSONObject jo = new JSONObject(result);
                Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_LONG).show();

                if(jo.getString("message").equals("profile found"))
                {
                    JSONObject jprofile=new JSONObject(jo.getString("profile"));
                    Log.d("readData",jprofile.getString("uFirstName"));//way of reading any tag inside profile from response
                  //  Intent intent = new Intent(getApplicationContext(),BooksActivity.class);
                    //intent.putExtra("username",usernameEditText.getText().toString());
                    //startActivity(intent);
                    SharedPreferences.Editor editor =
                            getSharedPreferences(CUSTOMER_USERNAME_PREFS, MODE_PRIVATE).edit();
                    editor.putString("username_key",usernameEditText.getText().toString());
                    editor.putString("UserId",jprofile.getString("uId"));
                    editor.putString("cc",jprofile.getString("uCreditcard"));
                    editor.putString("ccname",jprofile.getString("uCreditcardName"));
                    editor.putString("ph",jprofile.getString("uPhoneNumber"));
                    editor.putString("address",jprofile.getString("uP"));
                    editor.putString("email",usernameEditText.getText().toString());
                    editor.apply();

                    //view customer activity
                    // Toast.makeText(this, "valid", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),BooksActivity.class);
                    intent.putExtra("username",usernameEditText.getText().toString());
                    startActivity(intent);

                    // for SharedPreferences response
                    SharedPreferences myPreference =
                            getSharedPreferences("MyUser", 0);
                    //prepare it for edit by creating and Edit object
                    SharedPreferences.Editor prefEditor = myPreference.edit();
                    //store a string in memory
                    prefEditor.putString("UserName", usernameEditText.getText().toString());
                    //commit the transaction
                    prefEditor.commit();
                }
                else
                    Toast.makeText(getApplicationContext(),jo.getString("message"),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.d("shila",result);
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
           // res=result;
            //res.setText(result);
           // return result;
        }
        private String HttpPost(String myUrl) throws IOException, JSONException {
            String result = "";
            String JsonResult = null;
            StringBuffer sb = new StringBuffer();
            InputStream is = null;
            URL url = new URL(myUrl);

            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            // 2. build JSON object
            JSONObject jsonObject = buidJsonObject();

            // 3. add JSON content to POST request body
            setPostRequestContent(conn, jsonObject);

            // 4. make POST request to the given URL
            //conn.connect();
            is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                //sb=inputLine;
                sb.append(inputLine);
            }
            JsonResult = sb.toString();

            // 5. return response message
            return JsonResult;//conn.getResponseMessage()+"";

        }
        private JSONObject buidJsonObject() throws JSONException {

            JSONObject jsonObject = new JSONObject();
            //for inserting book condition--begin
            //jsonObject.accumulate("condition", txt.getText());
            //for inserting book condition--end
            //for login--begin
                jsonObject.accumulate("email", usernameEditText.getText());
              jsonObject.accumulate("password", passwordEditText.getText());
            //for login--end
            //for register--begin
            //   jsonObject.accumulate("email", "a@anu.com");
            // jsonObject.accumulate("password", "Cen@123");



          // jsonObject.accumulate("firstName", "shila");

          //  jsonObject.accumulate("lastName", "das");

            //jsonObject.accumulate("Address", "");

         //   jsonObject.accumulate("phone", "12345");
          //  jsonObject.accumulate("creditcard", "12345");
            //for inserting user profile--end

            return jsonObject;
        }
        private void setPostRequestContent(HttpURLConnection conn,
                                           JSONObject jsonObject) throws IOException {

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            Log.i(MainActivity.class.toString(), jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();
        }
    }
}
