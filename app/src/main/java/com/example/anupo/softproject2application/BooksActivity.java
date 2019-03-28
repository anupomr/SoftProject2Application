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
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.sebobooks.utility.BackgroundWorker;
import app.sebobooks.utility.Book;
import app.sebobooks.utility.RecyclerViewAdapter;

public class BooksActivity extends AppCompatActivity {
    public static List<Book> lstBook;
    RecyclerView myrv;

//    public static Context context;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
//        BackgroundWorker bw=new BackgroundWorker();
//        bw.execute();

        myrv=(RecyclerView) findViewById(R.id.recyclerview_id);

        lstBook=new ArrayList<>();
        btn=findViewById(R.id.btn);
//        myrv=(RecyclerView) findViewById(R.id.recyclerview_id);


        btn.setOnClickListener(new View.OnClickListener(){

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

        //
        //myrv.setOnClickListener(new RecyclerViewAdapter());
    }

    public void Book_OnClick(View view) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        //OrderDetailsActivity.textView.setText("Anupom");
        startActivity(intent);
    }
}
