package com.example.anupo.softproject2application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SellBookActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etISBN,etPublisher,etEdition,etQuantity,etPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_book);

        etTitle=findViewById(R.id.editTextTitle);
        etDescription=findViewById(R.id.editTextTitle);
        etISBN=findViewById(R.id.editTextTitle);
        etPublisher=findViewById(R.id.editTextTitle);
        etEdition=findViewById(R.id.editTextTitle);
        etQuantity=findViewById(R.id.editTextTitle);
        etPrice=findViewById(R.id.editTextTitle);
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
}
