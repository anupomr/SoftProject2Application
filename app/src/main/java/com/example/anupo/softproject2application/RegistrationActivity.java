package com.example.anupo.softproject2application;
/*
 * Purpose: This page for Registration
 * Author:  Anupom Roy
 * Date: Feburary 20, 2019
 * Version: 1.7
 * */
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            res=result;
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
                //sb.append(inputLine);
            }
            JsonResult = sb.toString();

            // 5. return response message
            return JsonResult;//conn.getResponseMessage()+"";

        }
        private JSONObject buidJsonObject() throws JSONException {

            JSONObject jsonObject = new JSONObject();

            //for register--begin
               jsonObject.accumulate("email", "a@anu.com");
             jsonObject.accumulate("password", "Cen@123");



             jsonObject.accumulate("firstName", "shila");

              jsonObject.accumulate("lastName", "das");

            jsonObject.accumulate("Address", "");

               jsonObject.accumulate("phone", "12345");
             jsonObject.accumulate("creditcard", "12345");
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
