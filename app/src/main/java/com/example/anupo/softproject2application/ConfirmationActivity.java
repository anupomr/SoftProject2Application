package com.example.anupo.softproject2application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }
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
