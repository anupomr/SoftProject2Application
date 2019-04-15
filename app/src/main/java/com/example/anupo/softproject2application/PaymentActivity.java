package com.example.anupo.softproject2application;
/*
 * Purpose: the user able to pay the payment
 * Author:  Anupom Roy
 * Date: March 16, 2019
 * Version: 1.7
 * */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import app.sebobooks.utility.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PaymentActivity extends AppCompatActivity {
Button confitmButton;
EditText etcc,etname,etaddress,etph;
String selectedBookId, selectedBookPrice;
    private static final String CUSTOMER_USERNAME_PREFS = "customer_username_prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        confitmButton=findViewById(R.id.buttonConfirm);

        etcc=findViewById(R.id.txtCardNo);
        etaddress=findViewById(R.id.txtAddress);
        etname=findViewById(R.id.txtName);
        etph=findViewById(R.id.txtPhone);
        SharedPreferences settings = getSharedPreferences(CUSTOMER_USERNAME_PREFS,MODE_PRIVATE);
        String uid = settings.getString("UserId", "");
        String cc = settings.getString("cc", "");
        String ccname = settings.getString("ccname", "");
        String ph = settings.getString("ph", "");
        String address = settings.getString("address", "");
        etcc.setText(cc);
        etph.setText(ph);
        etname.setText(ccname);
        etaddress.setText(address);
        Intent intent=getIntent();
        selectedBookId=intent.getExtras().getString("bookId");
        selectedBookPrice=intent.getExtras().getString("price");
        Log.d("in pay" ,selectedBookId);
        Log.d("in pay" ,selectedBookPrice);
        confitmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtCardNo = etcc.getText().toString().trim();
                String txtAddress = etaddress.getText().toString().trim();
                String txtName = etname.getText().toString().trim();

                // Validating text field
                if (txtCardNo.isEmpty()) {
                    etcc.setError("Name required");
                    etcc.requestFocus();
                    return;
                }

                if (txtAddress.isEmpty()) {
                    etaddress.setError("Name required");
                    etaddress.requestFocus();
                    return;
                }
                if (txtName.isEmpty()) {
                    etname.setError("Name required");
                    etname.requestFocus();
                    return;
                }
                new HTTPAsyncTask().execute("http://bookapi-dev.us-east-1.elasticbeanstalk.com/api/Orders");

            }
        });
    }








    //Create Menu if the user change his/her mind not to procceed
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

                if(!jo.getString("orderId").isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(),ConfirmationActivity.class);

                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

            Log.d("in pay build",selectedBookId);
            Log.d("in pay build",selectedBookPrice);
            jsonObject.accumulate("bookId", selectedBookId);
            jsonObject.accumulate("quantity",1);
            jsonObject.accumulate("price",selectedBookPrice);
            SharedPreferences settings = getSharedPreferences(CUSTOMER_USERNAME_PREFS,MODE_PRIVATE);
            String uid = settings.getString("UserId", "");
            jsonObject.accumulate("userId", uid);


            jsonObject.accumulate("sellerId", "15");
            jsonObject.accumulate("creationDate", "04/14/2019");
            jsonObject.accumulate("paymentDate", "04/14/2019");
            jsonObject.accumulate("releaseDate", "04/14/2019");
            jsonObject.accumulate("cancelationDate", "04/14/2019");
            jsonObject.accumulate("canfirmationDate", "04/14/2019");
            jsonObject.accumulate("isConfirmedByBuyer", true);
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
