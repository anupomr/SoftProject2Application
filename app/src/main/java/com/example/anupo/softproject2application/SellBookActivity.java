package com.example.anupo.softproject2application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class SellBookActivity extends AppCompatActivity {

    private static final String CUSTOMER_USERNAME_PREFS = "customer_username_prefs";
    EditText etTitle, etDescription, etISBN,etPublisher,etEdition,etQuantity,etPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_book);

        etTitle=findViewById(R.id.editTextTitle);
        etDescription=findViewById(R.id.editTextDesc);
        etISBN=findViewById(R.id.editTextIsbn);
        etPublisher=findViewById(R.id.editTextPublisher);
        etEdition=findViewById(R.id.editTextEdition);
        etQuantity=findViewById(R.id.editTextQuantity);
        etPrice=findViewById(R.id.editTextPrice);
    }


    //Menu start if user change his mind
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

                Intent books=new Intent(this,SellBookActivity.class);
                startActivity(books);
                break;

            case R.id.bookList:
                Intent booksListIntent=new Intent(this,BooksActivity.class);
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
    public void onSend(View view) {
        if(!etTitle.getText().toString().isEmpty() && !etPrice.getText().toString().isEmpty() && !etISBN.getText().toString().isEmpty())
            new SellBookActivity.HTTPAsyncTask().execute("http://bookapi-dev.us-east-1.elasticbeanstalk.com/api/Books");
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
                if(!jo.getString("bookId").isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(),BooksActivity.class);
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
                //sb=inputLine;
                sb.append(inputLine);
            }
            JsonResult = sb.toString();

            // 5. return response message
            return JsonResult;//conn.getResponseMessage()+"";

        }
        private JSONObject buidJsonObject() throws JSONException {

            JSONObject jsonObject = new JSONObject();


            jsonObject.accumulate("title", etTitle.getText());
            jsonObject.accumulate("description", etDescription.getText());// not mandatory
            jsonObject.accumulate("isbn", etISBN.getText());
            jsonObject.accumulate("publisher", etPublisher.getText());
            jsonObject.accumulate("edition",etEdition.getText());
            jsonObject.accumulate("quantity",etQuantity.getText());
            jsonObject.accumulate("price",etPrice.getText());
            jsonObject.accumulate("visualizations","0");
            jsonObject.accumulate("quantitySold","0");
            jsonObject.accumulate("blocked", false);
            jsonObject.accumulate("isWaitList", false);
            jsonObject.accumulate("creationDate", "04/14/2019");
            jsonObject.accumulate("BookConditionId","1");
            jsonObject.accumulate("StudyAreaId","1");
            //read shared pref
            SharedPreferences settings = getSharedPreferences(CUSTOMER_USERNAME_PREFS,MODE_PRIVATE);
            String uid = settings.getString("UserId", "");
            Log.d("shilauid",uid);

            jsonObject.accumulate("UserId",uid); //session id
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
