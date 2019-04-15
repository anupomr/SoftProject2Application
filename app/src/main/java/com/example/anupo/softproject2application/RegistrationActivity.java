package com.example.anupo.softproject2application;
/*
 * Purpose: This page for Registration
 * Author:  Anupom Roy and Shila Das
 * Date: Feburary 20, 2019
 * Version: 1.7
 * */
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegistrationActivity extends AppCompatActivity {
String res="";
   EditText emailET, pwdET, phET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        emailET=(EditText)findViewById(R.id.emailET);
        pwdET=(EditText)findViewById(R.id.passwordET);
        phET=(EditText)findViewById(R.id.phET);


    }
    public void onSend(View view) {
        if(!emailET.getText().toString().isEmpty() && !phET.getText().toString().isEmpty() && !pwdET.getText().toString().isEmpty())
            new RegistrationActivity.HTTPAsyncTask().execute("http://bookapi-dev.us-east-1.elasticbeanstalk.com/api/UserWithRoles/RegisterUser");
        else
            Toast.makeText(getApplicationContext(),"Please enter all values",Toast.LENGTH_LONG).show();
    }
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
            Log.d("shila",result);
            try {
                JSONObject jo = new JSONObject(result);

                if(jo.getString("statusCode").equals("201")) {
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.putExtra("email",emailET.getText().toString());

                    intent.putExtra("ph",phET.getText().toString());
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                sb.append(inputLine);
            }
            JsonResult = sb.toString();

            // 5. return response message
            return JsonResult;//conn.getResponseMessage()+"";

        }
        private JSONObject buidJsonObject() throws JSONException {

            JSONObject jsonObject = new JSONObject();


            jsonObject.accumulate("uEmail", emailET.getText());
            jsonObject.accumulate("password", pwdET.getText());
            jsonObject.accumulate("uPhoneNumber", phET.getText());

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
