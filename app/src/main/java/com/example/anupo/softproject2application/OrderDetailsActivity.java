package com.example.anupo.softproject2application;
/*
 * Purpose: To show the details information of the Order
 * Author:  Anupom Roy
 * Date: March 16, 2019
 * Version: 1.7
 * */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    Button checkout;
    String myString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        data = findViewById(R.id.showBookarea);
        checkout= findViewById(R.id.buttonCheckout);

        //Get search String form UI Intent
        myString=getIntent().getStringExtra("search");//"Java Standerd";
        ParseBook pb = new ParseBook();
        pb.execute();

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkoutIntent=new Intent(OrderDetailsActivity.this, PaymentActivity.class);

                startActivity(checkoutIntent);
            }
        });
    }


    public class ParseBook extends AsyncTask<Void, Void, Void> {

        String data = "";
        String dataParsed = "";
        String singleParsed = "";
//
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
                        singleParsed =
                                "Book Name  : " + jsonObject.getString("title") + "\n" +
                                "Price      : " + jsonObject.getString("price") + "\n" +
                                "Edition    : " + jsonObject.getString("edition") + "\n"+
                                "Description: " + jsonObject.getString("description") + "\n"+
                                "ISBN       : " + jsonObject.getString("isbn") + "\n"+
                                "Publisher  : "+ jsonObject.getString("publisher") + "\n";

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent homeIntent=new Intent(this,MainActivity.class);
                startActivity(homeIntent);

                break;
            case R.id.login:
                Intent loginIntent=new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
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
}