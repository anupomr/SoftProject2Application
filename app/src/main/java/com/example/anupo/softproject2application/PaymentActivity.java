package com.example.anupo.softproject2application;
/*
 * Purpose: the user able to pay the payment
 * Author:  Anupom Roy
 * Date: March 16, 2019
 * Version: 1.7
 * */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.sebobooks.utility.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PaymentActivity extends AppCompatActivity {
Button confitmButton;
EditText clientcode,fname,lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        confitmButton=findViewById(R.id.buttonConfirm);
        clientcode=findViewById(R.id.txtCardNo);
        fname=findViewById(R.id.txtAddress);
        lname=findViewById(R.id.txtName);

        confitmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtCardNo = clientcode.getText().toString().trim();
                String txtAddress = fname.getText().toString().trim();
                String txtName = lname.getText().toString().trim();

                // Validating text field
                if (txtCardNo.isEmpty()) {
                    clientcode.setError("Name required");
                    clientcode.requestFocus();
                    return;
                }

                if (txtAddress.isEmpty()) {
                    fname.setError("Name required");
                    fname.requestFocus();
                    return;
                }
                if (txtName.isEmpty()) {
                    lname.setError("Name required");
                    lname.requestFocus();
                    return;
                }

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createUser(txtCardNo, txtAddress, txtName);


                Intent confitmIntent=new Intent(PaymentActivity.this,ConfirmationActivity.class);
                startActivity(confitmIntent);
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
                //Toast.makeText(this, "You selected start!", Toast.LENGTH_LONG).show();
                break;
            case R.id.login:
                Intent loginIntent=new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
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
