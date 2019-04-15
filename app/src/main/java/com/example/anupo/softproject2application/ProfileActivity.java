package com.example.anupo.softproject2application;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class ProfileActivity extends AppCompatActivity {

    EditText fnET,lnET, ccET;
    String email,ph;
   // TextView phTV, emailTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fnET=(EditText)findViewById(R.id.fnET);
        lnET=(EditText)findViewById(R.id.lnET);
        ccET=(EditText)findViewById(R.id.ccET);

        email=getIntent().getStringExtra("email");
        Log.d("email",email);
        ph=getIntent().getStringExtra("ph");
        Log.d("shila2",email);
    }
    public void onSend(View view) {

        if(!fnET.getText().toString().isEmpty() && !lnET.getText().toString().isEmpty() && !ccET.getText().toString().isEmpty())
            new ProfileActivity.HTTPAsyncTask().execute("http://bookapi-dev.us-east-1.elasticbeanstalk.com/api/Users/UpdateProfile");
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
            Log.d("shila1",result);
            try {
                JSONObject jo = new JSONObject(result);
                // Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_LONG).show();
                if(jo.getString("statusCode").equals("201")) {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

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
            Log.d("email",email);
            //jsonObject.accumulate("userId", "0");
            jsonObject.accumulate("userName",email);
            jsonObject.accumulate("firstName", fnET.getText());
            //jsonObject.accumulate("middleName", "");
            jsonObject.accumulate("lastName", lnET.getText());
            jsonObject.accumulate("userType", "1");
            //jsonObject.accumulate("Address", "");
            jsonObject.accumulate("age", "35");
            jsonObject.accumulate("email", email); //this email should be login user email, any user can update only self profile
            jsonObject.accumulate("phone", ph); //this email should be login user email, any user can update only self profile
            jsonObject.accumulate("creditcard", ccET.getText());
            jsonObject.accumulate("creditcardName", fnET.getText()+" "+lnET.getText());
            jsonObject.accumulate("localizationId", "1"); //this is from dropdown
            jsonObject.accumulate("institutionBranchId", "1");//this is from dropdown . this dropdown list is depended on anoother dropdownlist Institution
            jsonObject.accumulate("isBlocked", "false"); //always put false
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
