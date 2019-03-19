package com.example.anupo.softproject2application;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class OrderDetailsActivity extends AppCompatActivity {
    public static TextView data;
    public static String data1;

    String myString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        data = findViewById(R.id.showBookarea);
        //retrieving from shared preferences
//        SharedPreferences myPref = getSharedPreferences("MyUser", MODE_PRIVATE);
//         myString = myPref.getString("UserName","");
        myString="Java Standerd";
        ParseBook pb = new ParseBook();
        pb.execute();
    }


    public class ParseBook extends AsyncTask<Void, Void, Void> {

        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http://bookapi-dev.us-east-1.elasticbeanstalk.com/api/Books.json");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    if (jsonObject.getString("title").equals(myString)) {
                        singleParsed = "Book Name: " + jsonObject.getString("title") + "\n" +
                                "Price    : " + jsonObject.getString("price") + "\n" +
                                "Edition  : " + jsonObject.getString("edition") + "\n";

                        dataParsed = dataParsed + singleParsed + "\n\n";
                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            OrderDetailsActivity.data.setText(this.dataParsed);
            super.onPostExecute(aVoid);
        }
    }

}