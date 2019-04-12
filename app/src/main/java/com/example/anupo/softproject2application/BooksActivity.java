package com.example.anupo.softproject2application;
/*
 *Purpose: Show all Books
 * Author:  Shila Das
 * Date: February 18, 2019
 * Version: 1.7
 * */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.sebobooks.utility.BackgroundWorker;
import app.sebobooks.utility.Book;
import app.sebobooks.utility.RecyclerViewAdapter;

public class BooksActivity extends AppCompatActivity {
    public static List<Book> lstBook;
    RecyclerView myrv;
    EditText searchTxt;
    Button btnShowBooks, btnSearchBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        myrv=(RecyclerView) findViewById(R.id.recyclerview_id);

        lstBook=new ArrayList<>();
        searchTxt=findViewById(R.id.searchTxt);
        btnSearchBook=findViewById(R.id.searchBtn);
        btnShowBooks=findViewById(R.id.btn);

        //Search button handeller
        btnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent searchIntent=new Intent(BooksActivity.this, OrderDetailsActivity.class);
                searchIntent.putExtra("search",searchTxt.getText().toString().trim());
                startActivity(searchIntent);
            }
        });

        btnShowBooks.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                BackgroundWorker bw=new BackgroundWorker();
                Log.d("shila","test before execute");
                bw.execute();
                Log.d("shila","test after execute");
                RecyclerViewAdapter myAdapter=new RecyclerViewAdapter(getBaseContext(),lstBook);
                myrv.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
                myrv.setAdapter(myAdapter);
            }
        });

    }

    public void Book_OnClick(View view) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        startActivity(intent);
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

}
